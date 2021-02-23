package com.dxzmpk.hongpeng.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.LayoutListViewBinding;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbsListFragment<T, M extends AbsViewModel<T>> extends Fragment {

    protected LayoutListViewBinding binding;

    protected RecyclerView mRecyclerView;

    protected PagedListAdapter<T, RecyclerView.ViewHolder> adapter;

    protected M mViewModel;

    protected DividerItemDecoration decoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutListViewBinding.inflate(inflater, container, false);
        binding.getRoot().setFitsSystemWindows(true);
        mRecyclerView = binding.recyclerView;

        adapter = getAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(null);

        //默认给列表中的Item 一个 10dp的ItemDecoration
        decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_divider));
        mRecyclerView.addItemDecoration(decoration);

        genericViewModel();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void genericViewModel() {
        //利用 子类传递的 泛型参数实例化出absViewModel 对象。
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] arguments = type.getActualTypeArguments();
        if (arguments.length > 1) {
            Type argument = arguments[1];
            Class modelClaz = ((Class) argument).asSubclass(AbsViewModel.class);
            mViewModel = (M) ViewModelProviders.of(this).get(modelClaz);

            //触发页面初始化数据加载的逻辑
            mViewModel.getPageData().observe(getViewLifecycleOwner(), pagedList -> submitList(pagedList));

            //监听分页时有无更多数据,以决定是否关闭上拉加载的动画
//            mViewModel.getBoundaryPageData().observe(getViewLifecycleOwner(), hasData -> finishRefresh(hasData));
        }
    }

    public void submitList(PagedList<T> result) {
        //只有当新数据集合大于0 的时候，才调用adapter.submitList
        //否则可能会出现 页面----有数据----->被清空-----空布局
        if (result.size() > 0) {
            adapter.submitList(result);
        }
//        finishRefresh(result.size() > 0);
    }


    public abstract PagedListAdapter getAdapter();
}
