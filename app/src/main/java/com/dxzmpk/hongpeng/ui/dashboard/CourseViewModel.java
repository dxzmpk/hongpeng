package com.dxzmpk.hongpeng.ui.dashboard;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

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

public class CourseViewModel extends AbsViewModel<Course> {

    private MutableLiveData<String> mText;

    public CourseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
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
        data.forEach((x)->x.setShowPic("http://192.168.0.105:8083/files/" + x.getShowPic()));
        Log.e(TAG, "loadData: data Loaded pageIndex = " + pageIndex);
        return data;
    }

    private static final String TAG = "DashboardViewModel";
}