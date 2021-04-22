package com.example.reapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.myinterface.INotify;
import com.example.reapp.myinterface.ItemClickListener;
import com.example.reapp.pojo.TaskInfo;
import com.lock.myapplication.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements INotify {

    private List<TaskInfo> taskInfoList;

    private ItemClickListener clickListener;

    public TaskAdapter(List<TaskInfo> taskInfoList, ItemClickListener clickListener) {
        this.taskInfoList = taskInfoList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.tv_deviceName.setText("设备名称:" + taskInfoList.get(position).getDeviceName());
        holder.tv_username.setText("用户名称:" + taskInfoList.get(position).getUserName());
        holder.tv_status.setText("任务状态:" + getStatus(taskInfoList.get(position).getTaskStatus()));
        holder.itemView.setOnClickListener(v -> {
            clickListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return taskInfoList.size();
    }

    @Override
    public void remove(int position) {
        taskInfoList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void removeAll() {
        taskInfoList.clear();
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_deviceName;
        public TextView tv_username;
        public TextView tv_status;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_deviceName = itemView.findViewById(R.id.tv_device_name);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_status = itemView.findViewById(R.id.tv_task_Status);
        }
    }

    private String getStatus(int code) {
        switch (code) {
            case 0:
                return "未审核";
            case 1:
                return "已通过";
            case 2:
                return "已拒绝";
            case 3:
                return "已过时";
            case 4:
                return "已执行";
        }
        return "";
    }

}
