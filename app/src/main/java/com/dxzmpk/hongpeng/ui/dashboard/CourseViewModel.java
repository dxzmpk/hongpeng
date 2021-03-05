package com.dxzmpk.hongpeng.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.alibaba.fastjson.TypeReference;
import com.dxzmpk.hongpeng.model.Activity;
import com.dxzmpk.hongpeng.model.Course;
import com.dxzmpk.hongpeng.ui.AbsViewModel;
import com.dxzmpk.libnetwork.ApiResponse;
import com.dxzmpk.libnetwork.ApiService;
import com.dxzmpk.libnetwork.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CourseViewModel extends AbsViewModel<Course> {

    private volatile boolean witchCache = true;
    private MutableLiveData<PagedList<Activity>> cacheLiveData = new MutableLiveData<>();
    private AtomicBoolean loadAfter = new AtomicBoolean(false);


    public CourseViewModel() {
    }

    public boolean isWitchCache() {
        return witchCache;
    }

    public MutableLiveData<PagedList<Activity>> getCacheLiveData() {
        return cacheLiveData;
    }

    public AtomicBoolean getLoadAfter() {
        return loadAfter;
    }

    @Override
    public DataSource createDataSource() {
        return new CoursePagedDataSource();
    }


    class CoursePagedDataSource extends PageKeyedDataSource<Integer, Course> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Course> callback) {
            List<Course> data = loadData(1, params.requestedLoadSize, null, null);
            callback.onResult(data, null, 2);
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Course> callback) {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Course> callback) {
            List<Course> data = loadData(params.key, params.requestedLoadSize, null, null);
            if (data.size() < params.requestedLoadSize) {
                callback.onResult(data, null);
                return;
            }
            callback.onResult(data, params.key + 1);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Course> loadData(int pageIndex, int pageSize, String pubState, String title) {

        Request request = ApiService.get("/course/lists")
                .addParam("pageIndex", pageIndex)
                .addParam("pageSize", pageSize)
                .addParam("pubState", pubState)
                .addParam("title", title)
                .responseType(new TypeReference<ArrayList<Course>>(){}.getType());
        request.cacheStrategy(Request.NET_ONLY);
        ApiResponse<List<Course>> response = request.execute();
        List<Course> data = response.body == null? Collections.emptyList() : response.body;
        data.forEach((x)->x.setShowPic(ApiService.getsBaseUrl() + "/files/" + x.getShowPic()));
        Log.e(TAG, "loadData: data Loaded pageIndex = " + pageIndex);
        return data;
    }

    @SuppressLint("RestrictedApi")
    public void loadAfter(int id, ItemKeyedDataSource.LoadCallback<Course> callback) {
        if (loadAfter.get()) {
            callback.onResult(Collections.emptyList());
            return;
        }
        ArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                List<Course> activities = loadData(id, config.pageSize, null, null);
                callback.onResult(activities);
            }
        });
    }

    private static final String TAG = "DashboardViewModel";
}