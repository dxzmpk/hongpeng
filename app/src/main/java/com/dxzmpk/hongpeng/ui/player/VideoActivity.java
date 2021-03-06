package com.dxzmpk.hongpeng.ui.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
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
    public String key = "playState";
    public static String COURSE_ID = "courseId";
    public String courseId;

    public static void startActivity(Context context, String courseId) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(COURSE_ID, courseId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = (String) getIntent().getSerializableExtra(COURSE_ID);
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

    @Override
    protected void onStop() {
        super.onStop();
    }
}