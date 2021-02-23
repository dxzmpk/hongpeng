package com.dxzmpk.hongpeng.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dxzmpk.hongpeng.BR;
import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.LayoutActBinding;
import com.dxzmpk.hongpeng.model.Activity;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Activity activity = getItem(position);
        holder.bindData(activity);
    }
}
