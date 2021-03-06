package com.dxzmpk.hongpeng.ui.dashboard;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dxzmpk.hongpeng.BR;
import com.dxzmpk.hongpeng.R;
import com.dxzmpk.hongpeng.databinding.LayoutCourseBinding;
import com.dxzmpk.hongpeng.model.Course;
import com.dxzmpk.hongpeng.ui.actdetail.ActDetailActivity;
import com.dxzmpk.hongpeng.ui.player.VideoActivity;

public class CourseAdapter extends PagedListAdapter<Course, CourseAdapter.ViewHolder> {

    private LayoutInflater inflater;

    protected Context mContext;

    public CourseAdapter(Context mContext) {
        super(new DiffUtil.ItemCallback<Course>() {
            @Override
            public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
                // 防止空异常
                return TextUtils.equals(oldItem.getCourseId(), newItem.getCourseId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
                return oldItem.equals(newItem);
            }
        });
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_course, parent, false);
        return new CourseAdapter.ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        final Course course = getItem(position);
        holder.bindData(course);

        holder.itemView.setOnClickListener(v -> VideoActivity.startActivity(mContext, course.getCourseId()));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public LayoutCourseBinding mBinding;
        public ImageView feedImage;

        public ViewHolder(@NonNull View itemView, ViewDataBinding binding) {
            super(itemView);
            mBinding = (LayoutCourseBinding) binding;
        }

        public void bindData(Course item) {

            mBinding.setVariable(BR.course, item);
//            mBinding.imageView.bindData(item.getShowPic());

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
