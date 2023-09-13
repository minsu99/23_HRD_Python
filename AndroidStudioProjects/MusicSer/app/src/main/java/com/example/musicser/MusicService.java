package com.example.musicser;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    private MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(this,R.raw.lildurk);
        mp.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mp != null){
            mp.stop();
            mp.release();
        }
        super.onDestroy();
    }
}



