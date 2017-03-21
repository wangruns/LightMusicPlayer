package com.wangruns.lightmusicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView btnPause, btnPlay,btnStop;
    private ImageView translateImage;
    private SeekBar skbProgress;
    private static Helper player;
    private static Helper firstPlayer,secondPlayer;
    private Intent intent;
    private String selectedMusicName;
    private boolean isFirstTimePlay=true;
    private boolean isStop=false;
    private static int countPlayer=0;
    private RotateAnimation  animation;

    //Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //increase the countPlayer when a MainActivity is created
        countPlayer++;


        intent =getIntent();
        //the intent can not be null
        if(intent!=null){
            selectedMusicName=intent.getStringExtra("musicName");
        }

        //add listeners
        btnPlay = (ImageView)findViewById(R.id.play_btn);
        btnPlay.setOnClickListener(new ClickEvent());

        btnPause = (ImageView)findViewById(R.id.pause_btn);
        btnPause.setOnClickListener(new ClickEvent());

        btnStop=(ImageView)findViewById(R.id.stop_btn);
        btnStop.setOnClickListener(new ClickEvent());


        translateImage=(ImageView)findViewById(R.id.translateImage);
        animation = new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        //set the time to translate a circle
        animation.setDuration(5000);
        //set it to translate all the time
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);




        skbProgress = (SeekBar)findViewById(R.id.seekbar_progress);
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        player = new Helper(skbProgress);

        if(countPlayer==1){
            firstPlayer=player;
        }
        if(countPlayer>1){
            secondPlayer=player;
        }


    }

    class ClickEvent implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            if (arg0 == btnPause) {
                //set the circle
                animation.setRepeatCount(0);
                firstPlayer.pauseTheMusic();
            } else if(arg0 == btnPlay){
                //make the image translate first
//                translateImage=(ImageView)findViewById(R.id.translateImage);
//                animation = new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//
//                //set the time to translate a circle
//                animation.setDuration(5000);
//                //set it to translate all the time
//                animation.setRepeatCount(Animation.INFINITE);
//                animation.setRepeatMode(Animation.RESTART);
                translateImage.startAnimation(animation);



                //if it's the second time to play the music,stop the first one firstly.
                if(countPlayer>1){
                    firstPlayer.stopTheMusic();
                    firstPlayer=player;
                    countPlayer=1;
                }
                /**
                 * if it's the first to play the music,the music source should be loaded
                 * or
                 * if the stop button is clicked and play again,the music source should be loaded
                 */
                if(isFirstTimePlay || isStop){
                    //get the url of the selected music
                    String url=selectedMusicName+".mp3";
                    player.prepareMusic(url);
                    isFirstTimePlay=false;
                    isStop=false;
                }else{
                        //the music in the cache already
                    firstPlayer.playTheMusic();
                    animation.start();
                }

            }else{
                //set the circle
                animation.setRepeatCount(0);
                firstPlayer.stopTheMusic();
                isStop=true;
            }
        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
            // (progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.mediaPlayer.seekTo(progress);
        }
    }

}