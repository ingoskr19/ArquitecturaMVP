package co.com.etn.arquitecturamvpbase.views.activities.device;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
    }

    private void init() {
        videoView = (VideoView) findViewById(R.id.video_videoview);
    }

    public void intenteToVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null ){
            startActivityForResult(intent, Constants.REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK){
                Uri videoUri = data.getData();
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoUri);
            videoView.setVisibility(View.VISIBLE);
            videoView.seekTo(1);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
