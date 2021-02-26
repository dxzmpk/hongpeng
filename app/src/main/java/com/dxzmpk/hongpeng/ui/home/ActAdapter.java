package com.dxzmpk.hongpeng.ui.home;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.TypeReference;
import com.dxzmpk.hongpeng.BR;
import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.LayoutActBinding;
import com.dxzmpk.hongpeng.model.Activity;
import com.dxzmpk.hongpeng.model.ActivityReturn;
import com.dxzmpk.hongpeng.ui.actdetail.ActDetailActivity;
import com.dxzmpk.libnetwork.ApiResponse;
import com.dxzmpk.libnetwork.ApiService;
import com.dxzmpk.libnetwork.JsonCallback;
import com.dxzmpk.libnetwork.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActAdapter extends PagedListAdapter<Activity, ActAdapter.ViewHolder> {

    private LayoutInflater inflater;

    protected Context mContext;


    public ActAdapter(Context mContext) {
        super(new DiffUtil.ItemCallback<Activity>() {
            @Override
            public boolean areItemsTheSame(@NonNull Activity oldItem, @NonNull Activity newItem) {
                return oldItem.getActId() == newItem.getActId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Activity oldItem, @NonNull Activity newItem) {
                return oldItem.equals(newItem);
            }
        });
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    public ActAdapter(@NonNull AsyncDifferConfig<Activity> config, Context mContext, String mCategory) {
        super(config);
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LayoutActBinding mBinding;
        public ImageView feedImage;

        public ViewHolder(@NonNull View itemView, ViewDataBinding binding) {
            super(itemView);
            mBinding = (LayoutActBinding) binding;
        }

        public void bindData(Activity item) {

            mBinding.setVariable(BR.act, item);
            mBinding.myImage.bindData(item.getShowPic());

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_act, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Activity activity = getItem(position);
        holder.bindData(activity);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Request request = ApiService.get("/activity/getById")
                        .addParam("actId", activity.getActId())
                        .responseType(new TypeReference<ActivityReturn>(){}.getType());
                request.cacheStrategy(Request.NET_ONLY);
                request.execute(new JsonCallback<ActivityReturn>() {
                    @Override
                    public void onSuccess(ApiResponse response) {
                        ActivityReturn data = (ActivityReturn) response.body;
                        ActDetailActivity.startFeedDetailActivity(mContext, data);
                    }
                });

            }
        });
    }
}
