package com.android.cmcc.recoder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mActivityMain;
    private EditText     mEdtOutput;
    private Button       mBtnStart;
    private Button       mBtnStop;


    private int frequence     = 44100;
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat   = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferSize    = 0;

    private void assignViews() {
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mEdtOutput = (EditText) findViewById(R.id.edt_output);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        init();
    }

    private void init() {
        mEdtOutput.setText(
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".mp3");
    }

    boolean checkOutFile() {
        return !TextUtils.isEmpty(mEdtOutput.getText().toString()) &&
               mEdtOutput.getText().toString().endsWith(".mp3");
    }

    public void startRecord(View view) {
        if (!checkOutFile()) {
            Toast.makeText(this, "输出录音文件目录不准确", Toast.LENGTH_LONG).show();
            return;
        }
        mBtnStart.setText("录音中...");
        mBtnStart.setEnabled(false);
        mBtnStop.setEnabled(true);
        new RecordTask().execute(new File(mEdtOutput.getText().toString().trim()));
    }

    boolean isRecording = false;

    class RecordTask extends AsyncTask<File, Integer, Void> {
        @Override
        protected Void doInBackground(File... arg0) {
            AudioRecord record = null;
            isRecording = true;
            try {
                DataOutputStream dos = new DataOutputStream(
                        new BufferedOutputStream(new FileOutputStream(arg0[0])));
                bufferSize = AudioRecord.getMinBufferSize(frequence, channelConfig,
                                                          audioFormat);

                record = new AudioRecord(MediaRecorder.AudioSource.MIC, frequence,
                                         channelConfig, audioFormat, bufferSize);
                //琢磨了一晚上的巨大的坑
                //需要注意:
                // 1、brate是bit率,这个值不能乱写,是根据录音文件PCM计算的,计算方式就这样ok啦
                //2、有与录音是单身道,我们在MP3编码的时候只能使用单身道,否则会出现语速慢半拍的节奏,困惑了我一晚上。
                //3、所以channelConfig = AudioFormat.CHANNEL_IN_MONO;必须这么设置,因为录音出来的数据只能是但声道。
                //4、 bufferSize不能你想怎么设置就怎么设置,所以这个地方必须是计算出来的。  bufferSize = AudioRecord.getMinBufferSize(frequence, channelConfig,
                // audioFormat);

                int brate= frequence * 2 * 2 /1024;;
                final RecordStream encoder = new RecordStream(dos, channelConfig, frequence,

                                                              brate);

                short[] buffer = new short[bufferSize*2];
                //开始录制
                record.startRecording();
                while (isRecording) {
                    int readsize = record.read(buffer, 0, buffer.length);
                    if (AudioRecord.ERROR_INVALID_OPERATION != readsize) {
                        encoder.writeEncoderStream(buffer);
                    }
                }
                //录制结束
                record.stop();

                try {
                    encoder.flush();
                    encoder.close();
                }
                catch (IOException e) {

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (record != null)
                    record.release();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mBtnStart.setText("开始");
            mBtnStart.setEnabled(true);
            mBtnStop.setEnabled(false);
            isRecording = false;
        }
    }

    public void stopRecord(View view) {

        mBtnStart.setText("开始");
        mBtnStart.setEnabled(true);
        mBtnStop.setEnabled(false);
        isRecording = false;
    }

    boolean isRecording() {
        return isRecording;
    }

}
