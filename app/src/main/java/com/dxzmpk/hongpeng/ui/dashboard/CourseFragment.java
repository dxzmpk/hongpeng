package com.dxzmpk.hongpeng.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;

import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.model.Activity;
import com.dxzmpk.hongpeng.model.Course;
import com.dxzmpk.hongpeng.ui.AbsListFragment;
import com.dxzmpk.hongpeng.ui.home.HomeFragment;
import com.dxzmpk.libnavannotation.FragmentDestination;

@FragmentDestination(pageUrl = "main/dash")
public class CourseFragment extends AbsListFragment<Course, CourseViewModel> {

    private CourseViewModel courseViewModel;

    @Override
    public PagedListAdapter getAdapter() {
        return new CourseAdapter(getContext());
    }

    public static CourseFragment newInstance() {
        CourseFragment fragment = new CourseFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel.getPageData().observe(getViewLifecycleOwner(), new Observer<PagedList<Course>>() {
            @Override
            public void onChanged(PagedList<Course> courses) {
                submitList(courses);
            }

        });
    }
}