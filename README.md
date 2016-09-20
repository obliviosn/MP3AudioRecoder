# MP3AudioRecoder


 Android端MP3录音程序: <br>
    由于大部分android手机未自带MP3编码器，所以无法直接录制MP3格式的音频文件，所以我使用lame的源码编译了android版本的静态库,此程序实现边录边转码。lame源码查看这里 <br> http://lame.sourceforge.net/ <br>  
 
 说明: 1、录制音频的几个参数，码率，声道，bit值，buffersize等有一定的取值关系，不能随便设置。<br>
 2、录音出来的数据是PCM数据都是单声道数据，所以无论是录音器设置为单声道还是双声道，在lame的初始化参数里面必须设置单声道，即lame_set_num_channels为1，如果你做音频转码可以为双声道，否则会出现录音语速慢一倍的问题。<br>
 3、BufferSize需要根据码率，帧速等进行计算，代码中有计算式，不要胡乱写，否则会出现MP3编码失败的问题。<br>
 <br>
   如有问题可以提交:wiki或者电邮:y.w.f@126.com
