package co.com.etn.arquitecturamvpbase.views.activities.device;

import android.Manifest;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.Permissions;

public class AudioActivity extends AppCompatActivity {

    private ToggleButton play;
    private ToggleButton record_stop;
    private ToggleButton delete;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private String fileName;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private boolean isReproduction = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        init();
        setListeners();

        fileName = createPathAudio();
    }

    private String createPathAudio() {
        String timeStamp = new SimpleDateFormat(Constants.FORMAT_DATE_FILE).format(new Date());
        String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        return storageDir+ "/"+ Constants.PREFIX_FILE_AUDIO+timeStamp+Constants.SUFFIX_FILE_AUDIO;
    }

    private void setListeners() {
        record_stop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isRecord) {
                if(Permissions.isGrantedPermissions(AudioActivity.this, Manifest.permission.RECORD_AUDIO)
                        && Permissions.isGrantedPermissions(AudioActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ){
                    managerRecord(isRecord);
                } else {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
                    Permissions.verifyPermissions(AudioActivity.this,permissions);
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(isReproduction);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });
    }

    private void onDelete() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        record_stop.setBackgroundResource(R.drawable.ic_microphone);
    }

    private void onPlay(boolean isReproduction) {
        if(isReproduction){
            play.setBackgroundResource(R.drawable.ic_pause);
            delete.setVisibility(View.GONE);
            if(mediaPlayer == null){
                startPlaying();
            } else {
                continueReproduction();
            }
        }else {
            pauseReproduction();
        }
    }

    private void pauseReproduction() {
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            countDownTimer.cancel();
        }
        play.setBackgroundResource(R.drawable.ic_play);
        delete.setVisibility(View.VISIBLE);
    }

    private void continueReproduction() {
        mediaPlayer.start();
        startProgress();
    }

    private void startPlaying() {
        progressBar.setProgress(0);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
            startProgress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void managerRecord(boolean isRecord) {
        if (isRecord){
            record_stop.setBackgroundResource(R.drawable.ic_stop);
            play.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        } else {
            play.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
        onRecord(isRecord);
    }

    private void onRecord(boolean start) {
        if(start){
            startProgress();
            startRecording();
        } else {
            stopRecorder();
        }
    }

    private void stopRecorder() {
        record_stop.setBackgroundResource(R.drawable.ic_microphone);
        countDownTimer.cancel();
        if (mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setMaxDuration(Constants.MAX_DURATION);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(fileName);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startProgress() {
        countDownTimer = new CountDownTimer(Constants.MAX_DURATION, Constants.INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressBar.getProgress()+Constants.INTERVAL);
            }

            @Override
            public void onFinish() {
                if(progressBar.getProgress()>0){
                    progressBar.setProgress(progressBar.getProgress()+Constants.INTERVAL);
                    record_stop.setBackgroundResource(R.drawable.ic_microphone);
                }
            }
        };
    }

    private void init() {
        record_stop = (ToggleButton) findViewById(R.id.audio_record);
        play = (ToggleButton) findViewById(R.id.audio_play);
        delete = (ToggleButton) findViewById(R.id.audio_delete);
        progressBar = (ProgressBar) findViewById(R.id.audio_progressbar);
        play.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
    }
}
