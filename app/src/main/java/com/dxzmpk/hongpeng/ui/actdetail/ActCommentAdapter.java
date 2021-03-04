package com.dxzmpk.hongpeng.ui.actdetail;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dxzmpk.hongpeng.databinding.ActCommentBinding;
import com.dxzmpk.hongpeng.model.Comments;
import com.dxzmpk.libcommon.extention.AbsPagedListAdapter;

public class ActCommentAdapter extends AbsPagedListAdapter<Comments, ActCommentAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;

    protected ActCommentAdapter(Context context) {
        super(new DiffUtil.ItemCallback<Comments>() {
            @Override
            public boolean areItemsTheSame(@NonNull Comments oldItem, @NonNull Comments newItem) {
                return oldItem.getComments().equals(newItem.getComments());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Comments oldItem, @NonNull Comments newItem) {
                return oldItem.getComments().equals(newItem.getComments());
            }
        });
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ActCommentBinding mBinding;
        public ViewHolder(View root, ActCommentBinding binding) {
            super(root);
            mBinding = binding;
        }
        public void bindData(Comments item) {
            mBinding.setComment(item);
//            mBinding.authorAvatar.bindData(item.getAvatarUrl());
//            mBinding.authorName.setText(item.getNickName());
        }
    }

    @Override
    protected ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        ActCommentBinding binding = ActCommentBinding.inflate(mInflater, parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    protected void onBindViewHolder2(ViewHolder holder, int position) {
        Comments item = getItem(position);
        holder.bindData(item);
    }
}
