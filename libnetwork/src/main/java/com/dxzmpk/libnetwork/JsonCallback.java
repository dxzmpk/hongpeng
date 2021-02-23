package com.dxzmpk.libnetwork;

public abstract class JsonCallback<T> {
    public void onSuccess(com.dxzmpk.libnetwork.ApiResponse<T> response) {

    }

    public void onError(com.dxzmpk.libnetwork.ApiResponse<T> response) {

    }

    public void onCacheSuccess(com.dxzmpk.libnetwork.ApiResponse<T> response) {

    }
}
