package com.xiaoliu.pagingdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */
public class PageAdapter extends PagedListAdapter<Student, PageAdapter.MyHolder> {

    public PageAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            //比较两个item是否相等
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            //比较两个内容是否相等
            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getStudentNumber() == newItem.getStudentNumber();
            }
        });
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Student item = getItem(position);
        if (item == null) {
            holder.mTextView.setText("loading");
        }else{
            holder.mTextView.setText(String.valueOf(item.getStudentNumber()));
        }
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        private final TextView mTextView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);
        }
    }
}
