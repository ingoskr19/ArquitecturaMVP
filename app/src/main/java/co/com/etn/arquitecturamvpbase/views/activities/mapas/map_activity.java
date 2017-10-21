package co.com.etn.arquitecturamvpbase.views.activities.mapas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import co.com.etn.arquitecturamvpbase.R;

public class map_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_mvp);
        Intent intent = new Intent(map_activity.this,MapsActivity.class);
        startActivity(intent);
    }
}
