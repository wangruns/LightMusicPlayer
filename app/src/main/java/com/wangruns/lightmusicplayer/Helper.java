package com.wangruns.lightmusicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Helper implements OnBufferingUpdateListener,OnCompletionListener, MediaPlayer.OnPreparedListener{
    public MediaPlayer mediaPlayer;
    private SeekBar skbProgress;
    private Timer mTimer=new Timer();

    //initialize Helper
    public Helper(SeekBar seekBar){
        this.skbProgress=seekBar;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            Log.e("mediaPlayer", "error", e);
        }

        mTimer.schedule(mTimerTask, 0, 1000);
    }

    //update the seekBar by the timer
    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if(mediaPlayer==null)
                return;
            if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
                handleProgress.sendEmptyMessage(0);
            }
        }
    };

    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {

            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();

            if (duration > 0) {
                long pos = skbProgress.getMax() * position / duration;
                skbProgress.setProgress((int) pos);
            }
        };
    };



    //play the music
    public void playTheMusic()
    {
        mediaPlayer.start();
    }

    public void prepareMusic(String musicUrl)
    {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicUrl);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void pauseTheMusic()
    {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stopTheMusic(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();

        }
    }


    @Override
    public void onPrepared(MediaPlayer arg0) {
        arg0.start();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {}

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
        skbProgress.setSecondaryProgress(bufferingProgress);
        int currentProgress=skbProgress.getMax()*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
    }

}
