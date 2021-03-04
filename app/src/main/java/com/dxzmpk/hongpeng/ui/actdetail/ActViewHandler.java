package com.dxzmpk.hongpeng.ui.actdetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.ActDetailActivityBinding;
import com.dxzmpk.hongpeng.databinding.ActDetailAllBinding;
import com.dxzmpk.hongpeng.databinding.ActionbarTitleBinding;
import com.dxzmpk.hongpeng.databinding.LayoutInterationBinding;
import com.dxzmpk.hongpeng.model.ActivityReturn;
import com.dxzmpk.hongpeng.model.Comments;
import com.dxzmpk.libcommon.utils.PixUtils;
import com.dxzmpk.libcommon.view.EmptyView;
import com.dxzmpk.libnetwork.ApiService;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class ActViewHandler {

    private final ActDetailViewModel viewModel;
    protected AppCompatActivity mActivity;
    protected RecyclerView mRecyclerView;
    protected LayoutInterationBinding mInateractionBinding;
    protected ActCommentAdapter listAdapter;
    protected ActDetailActivityBinding mActDetailActivityBinding;
    protected ActDetailAllBinding mActAllBinding;

    public ActViewHandler(AppCompatActivity activity) {

        mActivity = activity;
        viewModel = ViewModelProviders.of(activity).get(ActDetailViewModel.class);
        mActAllBinding = DataBindingUtil.setContentView(mActivity, R.layout.act_detail_all);
        mRecyclerView = mActAllBinding.recyclerView;
        mActAllBinding.actionBar.actDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

    }


    @CallSuper
    public void bindInitData(String actId) {

        mInateractionBinding = mActAllBinding.layoutInteration;
        mInateractionBinding.setOwner(mActivity);

        // network requests here
        viewModel.getActivityReturn(actId);

        // config recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(null);
        listAdapter = new ActCommentAdapter(mActivity) {
            @Override
            public void onCurrentListChanged(@Nullable PagedList<Comments> previousList, @Nullable PagedList<Comments> currentList) {
                boolean empty = currentList.size() <= 0;
                handleEmpty(!empty);
            }
        };

        mRecyclerView.setAdapter(listAdapter);
        mActDetailActivityBinding = ActDetailActivityBinding.inflate(LayoutInflater.from(mActivity), mRecyclerView, false);
        listAdapter.addHeaderView(mActDetailActivityBinding.getRoot());

        // observe comments data
        viewModel.setActId(actId);
        viewModel.getPageData().observe(mActivity, new Observer<PagedList<Comments>>() {
            @Override
            public void onChanged(PagedList<Comments> comments) {
                listAdapter.submitList(comments);
                handleEmpty(comments.size() > 0);
            }
        });

        // observe actReturn data
        viewModel.getActivityReturnLiveData().observe(mActivity, new Observer<ActivityReturn>() {
            @Override
            public void onChanged(ActivityReturn activityReturn) {
                changeDetailView(activityReturn);
                mInateractionBinding.setActReturn(activityReturn);
                mActAllBinding.setActReturn(activityReturn);
            }
        });
    }

    private void changeDetailView(ActivityReturn activityReturn){
//        inflateActionBar("活动");
        useBanner(activityReturn.getPicList());
        mActDetailActivityBinding.setActReturn(activityReturn);
        mActDetailActivityBinding.actTitleFather.actTitleTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        mActDetailActivityBinding.actTitleFather.actTitleDate.setVisibility(View.GONE);
    }

    public void useBanner(List<String> pics) {

        Banner banner = mActDetailActivityBinding.banner;

        if (pics.size() == 0) {
            pics.add("1614321400934.jpg");
        }


        //--------------------------简单使用-------------------------------
        banner.addBannerLifecycleObserver(mActivity)//添加生命周期观察者
                .setIndicator(new CircleIndicator(mActivity));

        //—————————————————————————如果你想偷懒，而又只是图片轮播————————————————————————
        banner.setAdapter(new BannerImageAdapter<String>(pics) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                //图片加载自己实现
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(holder.itemView)
                        .load(ApiService.getsBaseUrl() + "/files/" + data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }

        }).addBannerLifecycleObserver(mActivity)
                .setIndicator(new CircleIndicator(mActivity));
    }

    private EmptyView mEmptyView;

    public void handleEmpty(boolean hasData) {
        if (hasData) {
            if (mEmptyView != null) {
                listAdapter.removeHeaderView(mEmptyView);
            }
        } else {
            if (mEmptyView == null) {
                mEmptyView = new EmptyView(mActivity);
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = PixUtils.dp2px(40);
                mEmptyView.setLayoutParams(layoutParams);
                mEmptyView.setTitle("本课程暂无评论");
            }
            listAdapter.addHeaderView(mEmptyView);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (commentDialog != null && commentDialog.isAdded()) {
//            commentDialog.onActivityResult(requestCode, resultCode, data);
//        }
    }

    private void showCommentDialog() {
//        if (commentDialog == null) {
//            commentDialog = CommentDialog.newInstance(mFeed.itemId);
//        }
//        commentDialog.setCommentAddListener(comment -> {
//            handleEmpty(true);
//            listAdapter.addAndRefreshList(comment);
//        });
//        commentDialog.show(mActivity.getSupportFragmentManager(), "comment_dialog");
    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onBackPressed() {

    }
}
