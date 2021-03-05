package com.dxzmpk.hongpeng.ui.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.ActivityVideoBinding;
import com.dxzmpk.libnetwork.ApiService;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoActivity extends AppCompatActivity {

    protected VideoHandler handler;
    String key = "playState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new VideoHandler(this);

        handler.createOrResumePlayer();

        if (savedInstanceState != null) {
            Boolean playing = savedInstanceState.getBoolean(key);
            handler.restorePlayer(playing);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(key, handler.isPlaying());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.pausePlayer();
    }

}