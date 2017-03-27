package com.fudd.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;

import java.io.IOException;

public class AlarmAlert extends Activity
{
  MediaPlayer mMediaPlayer;
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);

    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM); //创建media player
    mMediaPlayer = new MediaPlayer();
    try {
      mMediaPlayer.setDataSource(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
    } catch (IOException e) {
      e.printStackTrace();
    }
    final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
      mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
      mMediaPlayer.setLooping(true);
      try {
        mMediaPlayer.prepare();
      } catch (IOException e) {
        e.printStackTrace();
      }
      mMediaPlayer.start();
    }
    new AlertDialog.Builder(AlarmAlert.this)
        .setIcon(R.drawable.clock)
        .setTitle("闹钟响了!!")
        .setMessage("快完成你制定的计划吧!!!")
        .setPositiveButton("关掉它",
         new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int whichButton)
          {

            mMediaPlayer.stop();
            mMediaPlayer.release();
            finish();
          }
        })
        .show();
  } 
}
