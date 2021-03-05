package com.dxzmpk.hongpeng.ui.home;

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
import com.dxzmpk.hongpeng.ui.AbsViewModel;
import com.dxzmpk.hongpeng.ui.MutablePageKeyedDataSource;
import com.dxzmpk.libnetwork.ApiResponse;
import com.dxzmpk.libnetwork.ApiService;
import com.dxzmpk.libnetwork.JsonCallback;
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
            witchCache = false;
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Activity> callback) {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Activity> callback) {
            List<Activity> data = loadData(params.key, params.requestedLoadSize, null, null);
//            这里是因为分页插件在index很大时，依然会显示数据的异常
            if (data.size() <= params.requestedLoadSize) {
                callback.onResult(data, null);
                return;
            }
            callback.onResult(data, params.key + 1);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Activity> loadData(int pageIndex, int pageSize, String pubState, String title) {
        if (pageIndex > 1) {
            loadAfter.set(true);
        }
        Request request = ApiService.get("/activity/lists")
                .addParam("pageIndex", pageIndex)
                .addParam("pageSize", pageSize)
                .addParam("pubState", pubState)
                .addParam("title", title)
                .responseType(new TypeReference<ArrayList<Activity>>(){}.getType());

        if (witchCache) {
            request.cacheStrategy(Request.CACHE_ONLY);
            request.execute(new JsonCallback<List<Activity>>() {
                @Override
                public void onCacheSuccess(ApiResponse<List<Activity>> response) {
                    Log.e("loadData", "onCacheSuccess: ");
                    MutablePageKeyedDataSource dataSource = new MutablePageKeyedDataSource<Activity>();
                    dataSource.data.addAll(response.body);

                    PagedList pagedList = dataSource.buildNewPagedList(config);
                    cacheLiveData.postValue(pagedList);

                    //下面的不可取，否则会报
                    // java.lang.IllegalStateException: callback.onResult already called, cannot call again.
                    //if (response.body != null) {
                    //  callback.onResult(response.body);
                    // }
                }
            });
        }

        List<Activity> data = null;
        try {
            Request netRequest = witchCache ? request.clone() : request;
            netRequest.cacheStrategy(pageIndex == 1 ? Request.NET_CACHE : Request.NET_ONLY);
            ApiResponse<List<Activity>> response = netRequest.execute();
            data = response.body == null ? Collections.emptyList() : response.body;
            if (pageIndex > 1) {
                //通过BoundaryPageData发送数据 告诉UI层 是否应该主动关闭上拉加载分页的动画
                ((MutableLiveData) getBoundaryPageData()).postValue(data.size() > 0);
                loadAfter.set(false);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "loadData: pageIndex = " + pageIndex);
        
        return data;
    }

    @SuppressLint("RestrictedApi")
    public void loadAfter(int pageIndex, PageKeyedDataSource.LoadCallback<Integer, Activity> callback) {
        if (loadAfter.get()) {
            callback.onResult(Collections.emptyList(), pageIndex + 1);
            return;
        }
        ArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                List<Activity> activities = loadData(pageIndex, config.pageSize, null, null);
                callback.onResult(activities, pageIndex + 1);
            }
        });
    }

    private static final String TAG = "HomeViewModel";

    public MutableLiveData<PagedList<Activity>> getCacheLiveData() {
        return cacheLiveData;
    }
}


