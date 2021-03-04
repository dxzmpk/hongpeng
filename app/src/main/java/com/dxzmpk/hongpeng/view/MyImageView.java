package com.dxzmpk.hongpeng.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dxzmpk.libcommon.utils.PixUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MyImageView extends AppCompatImageView {


    private Context context;

    public MyImageView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public MyImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void bindData(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            setVisibility(GONE);
            return;
        } else {
            setVisibility(VISIBLE);
        }
        Glide.with(this).load(imageUrl).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                setScaleType(ScaleType.CENTER_CROP);
//                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                int width = windowManager.getDefaultDisplay().getWidth();
//                int height = windowManager.getDefaultDisplay().getHeight();

                int width = PixUtils.getScreenWidth();

                setAdjustViewBounds(true);
                setMaxWidth(width);
                setMaxHeight(width*2);

                setImageDrawable(resource);
            }
        });
    }


    @BindingAdapter(value = {"image_url", "isCircle"})
    public static void setImageUrl(MyImageView view, String imageUrl, boolean isCircle) {
        setImageUrl(view, imageUrl, isCircle, 0);
    }

    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"image_url", "isCircle", "radius"}, requireAll = false)
    public static void setImageUrl(MyImageView view, String imageUrl, boolean isCircle, int radius) {
        RequestBuilder<Drawable> builder = Glide.with(view).load(imageUrl);
        if (isCircle) {
            builder.transform(new CircleCrop());
        } else if (radius > 0) {
            builder.transform(new RoundedCornersTransformation(PixUtils.dp2px(radius), 0));
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
            builder.override(layoutParams.width, layoutParams.height);
        }
        builder.into(view);
    }


}
