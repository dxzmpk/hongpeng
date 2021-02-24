package com.dxzmpk.hongpeng.ui.home;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.alibaba.fastjson.TypeReference;
import com.dxzmpk.hongpeng.model.Activity;
import com.dxzmpk.hongpeng.ui.AbsViewModel;
import com.dxzmpk.libnetwork.ApiResponse;
import com.dxzmpk.libnetwork.ApiService;
import com.dxzmpk.libnetwork.Request;

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
        return new ActPagedDataSource();
    }



    class ActPagedDataSource extends PageKeyedDataSource<Integer, Activity> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Activity> callback) {
            List<Activity> data = loadData(1, params.requestedLoadSize, null, null);
            callback.onResult(data, null, 2);
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Activity> callback) {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Activity> callback) {
            List<Activity> data = loadData(params.key, params.requestedLoadSize, null, null);
            callback.onResult(data, params.key + 1);
        }
    }

    boolean lastItem = false;


    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Activity> loadData(int pageIndex, int pageSize, String pubState, String title) {

        Request request = ApiService.get("/activity/lists")
                .addParam("pageIndex", pageIndex)
                .addParam("pageSize", pageSize)
                .addParam("pubState", pubState)
                .addParam("title", title)
                .responseType(new TypeReference<ArrayList<Activity>>(){}.getType());
        request.cacheStrategy(Request.NET_ONLY);
        ApiResponse<List<Activity>> response = request.execute();
        if (response.body.size() <= 1) {
            lastItem = true;
        }
        if (lastItem) {
            return Collections.emptyList();
        }
        List<Activity> data = response.body == null? Collections.emptyList() : response.body;
        data.forEach((x)->x.setShowPic("http://42.192.206.123:8083/files/" + x.getShowPic()));
        Log.e(TAG, "loadData: data Loaded pageIndex = " + pageIndex);
        return data;
    }

    private static final String TAG = "HomeViewModel";
}



//class ActDataSource extends ItemKeyedDataSource<Integer, Activity> {
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Activity> callback) {
//        Log.e(TAG, "loadInitial DATASOURCE");
//        loadData(-1, params.requestedLoadSize, null, null, callback);
//        witchCache = false;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Activity> callback) {
//        Log.e(TAG, "loadAfter DATASOURCE");
//        loadData(-1, params.requestedLoadSize, null, null, callback);
//    }
//
//    @Override
//    // 进入页面的时候加载第三页，向前加载第一页使用
//    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Activity> callback) {
//        Log.e(TAG, "loadBefore DATASOURCE");
//        callback.onResult(Collections.emptyList());
//    }
//
//    @NonNull
//    @Override
//    public Integer getKey(@NonNull Activity item) {
//        return Integer.valueOf(item.getActId());
//    }
//}