package com.dxzmpk.hongpeng.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;

import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.model.Activity;
import com.dxzmpk.hongpeng.ui.AbsListFragment;
import com.dxzmpk.hongpeng.ui.AbsViewModel;

import java.util.List;

public class HomeFragment extends AbsListFragment<Activity, HomeViewModel> {

    private HomeViewModel homeViewModel;


    @Override
    public PagedListAdapter getAdapter() {
        return new ActAdapter(getContext());
    }

    public static HomeFragment newInstance(String feedType) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel.getPageData().observe(getViewLifecycleOwner(), new Observer<PagedList<Activity>>() {
            @Override
            public void onChanged(PagedList<Activity> activities) {
                submitList(activities);
            }

        });
    }


}