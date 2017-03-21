package com.wangruns.lightmusicplayer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //intent with funny effect
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(StartActivity.this, ListActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
                overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
            }
        },2000);
    }
}
