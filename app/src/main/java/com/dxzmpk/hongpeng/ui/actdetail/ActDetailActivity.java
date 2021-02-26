package com.dxzmpk.hongpeng.ui.actdetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.ActionbarTitleBinding;
import com.dxzmpk.hongpeng.model.ActivityReturn;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class ActDetailActivity extends AppCompatActivity {

    private static final String KEY_FEED = "key_feed";

    private ActDetailViewModel mViewModel;

    // 这里是为了方便别的用户使用startActivity进行传参，可以学习这种方式，提高代码的可复用性。
    public static void startFeedDetailActivity(Context context, ActivityReturn activityReturn) {
        Intent intent = new Intent(context, ActDetailActivity.class);
        intent.putExtra(KEY_FEED, activityReturn);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ActDetailViewModel.class);
        setTheme(R.style.Theme_WithActionBar);
        setContentView(R.layout.act_detail_activity);

        ActivityReturn activityReturn = (ActivityReturn) getIntent().getSerializableExtra(KEY_FEED);
        inflateActionBar(activityReturn.getActivity().getTitle());
        useBanner(activityReturn.getPicList());
    }

    private void inflateActionBar(String subTitle) {
        ActionBar actionBar=getSupportActionBar();
        @NonNull ActionbarTitleBinding binding = ActionbarTitleBinding.inflate(getLayoutInflater());
        binding.setTitle(subTitle);
        actionBar.setCustomView(binding.getRoot());
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    public void useBanner(List<String> pics) {

        Banner banner = findViewById(R.id.banner);

        //--------------------------简单使用-------------------------------
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this));

        //—————————————————————————如果你想偷懒，而又只是图片轮播————————————————————————
        banner.setAdapter(new BannerImageAdapter<String>(pics) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                //图片加载自己实现
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(holder.itemView)
                        .load("http://192.168.0.105:8083/files/" + data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }

        }).addBannerLifecycleObserver(this)
        .setIndicator(new CircleIndicator(this));
    }
}