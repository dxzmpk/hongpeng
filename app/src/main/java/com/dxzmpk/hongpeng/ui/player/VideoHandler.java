package com.dxzmpk.hongpeng.ui.player;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.FragmentNavigator;

import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.ActivityVideoBinding;
import com.dxzmpk.hongpeng.model.Activity;
import com.dxzmpk.hongpeng.ui.actdetail.ActDetailViewModel;
import com.dxzmpk.libnetwork.ApiService;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;

public class VideoHandler {

    protected AppCompatActivity mActivity;
    protected ActivityVideoBinding mVideoBinding;
    protected VideoViewModel mViewModel;
    protected StyledPlayerView playerView;
    protected StyledPlayerControlView controlView;

    public VideoHandler(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
        mVideoBinding = DataBindingUtil.setContentView(mActivity, R.layout.activity_video);
        mViewModel = ViewModelProviders.of(mActivity).get(VideoViewModel.class);
        playerView = mVideoBinding.playerView;
        validateFullScreenButton();
    }

    protected void validateFullScreenButton(){
        try {
            Field field = null;
            field = StyledPlayerView.class.getDeclaredField("controller");
            field.setAccessible(true);
            controlView = (StyledPlayerControlView) field.get(playerView);
            controlView.setOnFullScreenModeChangedListener(new StyledPlayerControlView.OnFullScreenModeChangedListener() {
                @Override
                public void onFullScreenModeChanged(boolean isFullScreen) {
                    if(isFullScreen) {
                        mActivity.setTheme(R.style.Theme_WithActionBar);
                        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        mActivity.setTheme(R.style.Theme_Nav_raw);
                        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
            });
            Field field1 = StyledPlayerControlView.class.getDeclaredField("fullScreenButton");
            field1.setAccessible(true);
            ImageView fullScreenButton = (ImageView) field1.get(controlView);
            fullScreenButton.setVisibility(View.VISIBLE);
            Field field2 = StyledPlayerControlView.class.getDeclaredField("isFullScreen");
            field2.setAccessible(true);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void createOrResumePlayer() {
        if (mViewModel.player == null) {
            mViewModel.player = new SimpleExoPlayer.Builder(mActivity).build();

            playerView.setPlayer(mViewModel.player);

            MediaItem mediaItem = MediaItem.fromUri(ApiService.getsBaseUrl() + "/files/" + "1614939670570.mp4");
            MediaItem mediaItem1 = MediaItem.fromUri(ApiService.getsBaseUrl() + "/files/" + "1614942028549.mp4");
            // Set the media item to be played.
            mViewModel.player.addMediaItem(mediaItem);
            mViewModel.player.addMediaItem(mediaItem1);
            // Prepare the player.
            mViewModel.player.prepare();
            // Start the playback.
            mViewModel.player.play();
        } else {
            if (mActivity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                restoreLandScapeFullScreen();
            }
            StyledPlayerView playerView = mVideoBinding.playerView;
            playerView.setPlayer(mViewModel.player);
        }
    }

    private void restoreLandScapeFullScreen() {
        try {
            Method method = StyledPlayerControlView.class.getDeclaredMethod("onFullScreenButtonClicked", View.class);
            method.setAccessible(true);
            method.invoke(controlView, new View(mActivity));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void pausePlayer() {
        mViewModel.player.pause();
    }

}
