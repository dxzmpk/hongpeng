package com.dxzmpk.hongpeng.ui.actdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.TypeReference;
import com.dxzmpk.hongpeng.model.ActivityReturn;
import com.dxzmpk.libnetwork.ApiResponse;
import com.dxzmpk.libnetwork.ApiService;
import com.dxzmpk.libnetwork.JsonCallback;
import com.dxzmpk.libnetwork.Request;

import java.util.List;

public class ActDetailViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ActivityReturn> activityReturnLiveData = new MutableLiveData<>();

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
}