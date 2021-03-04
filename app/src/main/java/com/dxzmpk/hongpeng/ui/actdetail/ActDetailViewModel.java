package com.dxzmpk.hongpeng.ui.actdetail;

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
import com.dxzmpk.hongpeng.model.ActivityReturn;
import com.dxzmpk.hongpeng.model.Comments;
import com.dxzmpk.hongpeng.ui.AbsViewModel;
import com.dxzmpk.libnetwork.ApiResponse;
import com.dxzmpk.libnetwork.ApiService;
import com.dxzmpk.libnetwork.JsonCallback;
import com.dxzmpk.libnetwork.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActDetailViewModel extends AbsViewModel<Comments> {
    // TODO: Implement the ViewModel
    private MutableLiveData<ActivityReturn> activityReturnLiveData = new MutableLiveData<>();
    private String actId;

    public void setActId(String actId) {
        this.actId = actId;
    }

    public void getActivityReturn(String actId){

        Request request = ApiService.get("/activity/getById")
                .addParam("actId", actId)
                .responseType(new TypeReference<ActivityReturn>(){}.getType());
        request.cacheStrategy(Request.NET_ONLY);
        request.execute(new JsonCallback<ActivityReturn>() {
            @Override
            public void onSuccess(ApiResponse response) {
                ActivityReturn data = (ActivityReturn) response.body;
                activityReturnLiveData.postValue(data);
            }
        });

    }

    public LiveData<ActivityReturn> getActivityReturnLiveData() {
        return activityReturnLiveData;
    }

    class DataSource extends PageKeyedDataSource<Integer, Comments> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Comments> callback) {
            List<Comments> comments= loadData(params.requestedLoadSize, 1, actId);
            callback.onResult(comments, null, 2);
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Comments> callback) {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Comments> callback) {
            List<Comments> data = loadData(params.requestedLoadSize, params.key, actId);
            if (data.size() <= params.requestedLoadSize) {
                callback.onResult(data, null);
                return;
            }
            callback.onResult(data, params.key + 1);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private List<Comments> loadData(int pageSize, int pageIndex, String actId) {
            Request request = ApiService.get("/activity/listComment")
                    .addParam("pageIndex", pageIndex)
                    .addParam("pageSize", pageSize)
                    .addParam("actId", actId)
                    .responseType(new TypeReference<ArrayList<Comments>>(){}.getType());
            request.cacheStrategy(Request.NET_ONLY);
            ApiResponse<List<Comments>> response = request.execute();
            List<Comments> data = response.body == null? Collections.emptyList() : response.body;
            Log.e(TAG, "loadData: data Loaded pageIndex = " + pageIndex);
            return data;
        }

    }

    @Override
    public DataSource createDataSource() {
        return new DataSource();
    }

    private static final String TAG = "ActDetailViewModel";
}