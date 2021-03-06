package com.dxzmpk.hongpeng.ui.player;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dxzmpk.hongpeng.model.CourseReturn;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoViewModel extends ViewModel {


    protected SimpleExoPlayer player;

    MutableLiveData<CourseReturn> courseReturn;

}
