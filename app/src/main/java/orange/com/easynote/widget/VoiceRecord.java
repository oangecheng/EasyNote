package orange.com.easynote.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Orange on 2016/12/29.
 */

public class VoiceRecord extends TextView {

    //语音保存的路径
    private String fileName = null;
    //语音操作对象，MediaPlayer为播放器，MediaRecorder为录音
    private MediaPlayer mediaPlayer = null;
    private MediaRecorder mediaRecorder = null;

    //判断是否为长按的标记
    private int isLongClick = 0;

    //记录开始的时间
    private long startTime = 0;

    public VoiceRecord(Context context) {
        super(context);
    }

    public VoiceRecord(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    private void startRecording(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    private void stopRecording(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void onRecording(boolean start){
        if (start){
            startRecording();
        }else {
            startRecording();
        }
    }
}
