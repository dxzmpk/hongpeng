package com.dxzmpk.hongpeng.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PagedList;

import com.dxzmpk.hongpeng.model.Activity;
import com.dxzmpk.hongpeng.ui.AbsViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomeViewModel extends AbsViewModel<Activity> {

    private volatile boolean witchCache = true;
    private MutableLiveData<PagedList<Activity>> cacheLiveData = new MutableLiveData<>();
    private AtomicBoolean loadAfter = new AtomicBoolean(false);

    public HomeViewModel() {
    }

    @Override
    public DataSource createDataSource() {
        return new ActDataSource();
    }

    class ActDataSource extends ItemKeyedDataSource<Integer, Activity> {

        @Override
        public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Activity> callback) {
            Log.e(TAG, "loadInitial DATASOURCE");
            loadData(0, params.requestedLoadSize, callback);
            witchCache = false;
        }

        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Activity> callback) {
            Log.e(TAG, "loadAfter DATASOURCE");
            loadData(params.key, params.requestedLoadSize, callback);
        }

        @Override
        // 进入页面的时候加载第三页，向前加载第一页使用
        public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Activity> callback) {
            Log.e(TAG, "loadBefore DATASOURCE");
            callback.onResult(Collections.emptyList());
        }

        @NonNull
        @Override
        public Integer getKey(@NonNull Activity item) {
            return Integer.valueOf(item.getActId());
        }
    }


    private void loadData(int key, int count, ItemKeyedDataSource.LoadCallback<Activity> callback) {
        if (key > 0) {
            loadAfter.set(true);
        }

        //
        String picUrl = "https://pics2.baidu.com/feed/5882b2b7d0a20cf4443d59671f004a3eadaf99ee.png?token=a9bdd904d4fdabe17da747f73140df7e&s=FB3B30C66A3A10904685289D0300508E";
        List<Activity> activityList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            activityList.add(new Activity(String.valueOf(i), i+"title", 1, 10f, 200, picUrl));
        }
        Log.e(TAG, "loadData: data Loaded");
        callback.onResult(activityList);
    }

    private static final String TAG = "HomeViewModel";
}