package com.dxzmpk.hongpeng.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public abstract class AbsViewModel<T> extends ViewModel {

    protected PagedList.Config config;

    private DataSource dataSource;

    private LiveData<PagedList<T>> pageData;

    private MutableLiveData<Boolean> boundaryPageData = new MutableLiveData<>();

    public AbsViewModel() {

        config = new PagedList.Config.Builder()
                .setPageSize(5)
                .setInitialLoadSizeHint(5)
                .build();

        pageData = new LivePagedListBuilder(factory, config)
                .setInitialLoadKey(0)
                .setBoundaryCallback(callback)
                .build();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public LiveData<PagedList<T>> getPageData() {
        return pageData;
    }

    public MutableLiveData<Boolean> getBoundaryPageData() {
        return boundaryPageData;
    }


    private static final String TAG = "AbsViewModel";

    PagedList.BoundaryCallback<T> callback = new PagedList.BoundaryCallback<T>() {
        @Override
        // 当没有数据从DataSource加载到PagedList中时
        public void onZeroItemsLoaded() {
            Log.e(TAG, "onZeroItemsLoaded");
            boundaryPageData.postValue(false);
        }

        @Override
        // 当第一条数据被加载时
        public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
            Log.e(TAG, "onItemAtFrontLoaded");
            boundaryPageData.postValue(true);
        }

        @Override
        // 当最后一条数据被加载时
        public void onItemAtEndLoaded(@NonNull T itemAtEnd) {
            Log.e(TAG, "onItemAtEndLoaded");
        }
    };

    DataSource.Factory factory = new DataSource.Factory() {
        @NonNull
        @Override
        public DataSource create() {
            if (dataSource == null || dataSource.isInvalid()) {
                dataSource = createDataSource();
            }
            return dataSource;
        }
    };

    public abstract DataSource createDataSource();

}
