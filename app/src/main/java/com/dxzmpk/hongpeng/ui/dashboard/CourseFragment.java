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
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;

import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.model.Activity;
import com.dxzmpk.hongpeng.model.Course;
import com.dxzmpk.hongpeng.ui.AbsListFragment;
import com.dxzmpk.hongpeng.ui.MutablePageKeyedDataSource;
import com.dxzmpk.hongpeng.ui.home.HomeFragment;
import com.dxzmpk.libnavannotation.FragmentDestination;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

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

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        final PagedList<Course> currentList = adapter.getCurrentList();
        if (currentList == null || currentList.size() <= 0) {
            finishRefresh(false);
            return;
        }
        mViewModel.loadAfter(adapter.getItemCount()/currentList.getConfig().pageSize + 1, new ItemKeyedDataSource.LoadCallback<Course>() {
            @Override
            public void onResult(@NonNull List<Course> data) {
                PagedList.Config config = currentList.getConfig();
                if (data != null && data.size() > 0) {
                    //这里 咱们手动接管 分页数据加载的时候 使用MutableItemKeyedDataSource也是可以的。
                    //由于当且仅当 paging不再帮我们分页的时候，我们才会接管。所以 就不需要ViewModel中创建的DataSource继续工作了，所以使用
                    //MutablePageKeyedDataSource也是可以的
                    MutablePageKeyedDataSource dataSource = new MutablePageKeyedDataSource();

                    //这里要把列表上已经显示的先添加到dataSource.data中
                    //而后把本次分页回来的数据再添加到dataSource.data中
                    dataSource.data.addAll(currentList);
//                    dataSource.data.addAll(data);
                    PagedList pagedList = dataSource.buildNewPagedList(config);
                    submitList(pagedList);
                }
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mViewModel.getDataSource().invalidate();
    }
}