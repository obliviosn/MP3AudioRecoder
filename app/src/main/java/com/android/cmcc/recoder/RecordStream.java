package com.android.cmcc.recoder;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 项目名称： AudioRecoder
 * 创建人：   Vber.T
 * 创建时间： 16/9/19 下午8:28
 * 修改人：   Vber.T
 * 修改时间： 16/9/19 下午8:28
 * 修改备注：
 * 文件名称： RecordStream
 * 类描述：
 */
public class RecordStream extends FilterOutputStream {
    /**
     * Constructs a new {@code FilterOutputStream} with {@code out} as its
     * target stream.
     *
     * @param out the target stream that this stream writes to.
     */

    public RecordStream(OutputStream out) {
        super(out);
    }

    int channel, sampleRate, brate;

    public RecordStream(OutputStream out, int channel, int sampleRate, int brate) throws  IOException {
        super(out);
        this.channel = channel;
        this.sampleRate = sampleRate;
        this.brate = brate;
        if(AudioHelper.nativeInit(channel,sampleRate,brate)<0)
            throw  new IOException("nativeInit MP3 Error");
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        throw new UnsupportedOperationException(
                "Method not implemented for byte[]. Please use short[]");
    }

    public void writeEncoderStream(short[] buffer) throws IOException {
        byte[] encodedBuffer   = new byte[buffer.length];
        int    length = AudioHelper.nativeEncodeAudioMP3(buffer,encodedBuffer,encodedBuffer.length);
        if (length > 0) {
            this.out.write(encodedBuffer, 0, length);
        } else {
            throw new IOException("Error during Encoding. Error Code: " + length);
        }
    }


    @Override
    public void close() throws IOException {
        this.out.close();
        AudioHelper.nativeDestroy();
    }
}
