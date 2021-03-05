package com.dxzmpk.hongpeng.ui.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.ActivityVideoBinding;
import com.dxzmpk.libnetwork.ApiService;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoActivity extends AppCompatActivity {

    protected ActivityVideoBinding mVideoBinding;
    protected VideoHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new VideoHandler(this);
        handler.createOrResumePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.pausePlayer();
    }
}