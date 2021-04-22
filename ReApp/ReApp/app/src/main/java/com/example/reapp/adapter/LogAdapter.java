package com.example.reapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.myinterface.INotify;
import com.example.reapp.pojo.LogInfo;
import com.example.reapp.utils.DateUtil;
import com.lock.myapplication.R;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> implements INotify {

    private List<LogInfo> logInfoList;
    private Context context;

    public LogAdapter(Context context, List<LogInfo> logInfoList) {
        this.logInfoList = logInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        Long time = logInfoList.get(position).getDoTime();
        holder.tv_task.setText("任务代码: " + logInfoList.get(position).getTaskCode());
        holder.tv_imei.setText("设备IMEI: " + logInfoList.get(position).getDeviceIMEI());
        holder.tv_do_time.setText("执行时间: " + DateUtil.toFormatTime(time));
        holder.tv_apply_time.setText("申请时间: ");
    }

    @Override
    public int getItemCount() {
        return logInfoList.size();
    }

    @Override
    public void remove(int position) {

    }

    @Override
    public void removeAll() {
        logInfoList.clear();
        notifyDataSetChanged();
    }

    class LogViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_task,tv_imei,tv_do_time,tv_apply_time;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_task = itemView.findViewById(R.id.tv_task);
            tv_imei = itemView.findViewById(R.id.tv_imei);
            tv_do_time = itemView.findViewById(R.id.tv_do_time);
            tv_apply_time = itemView.findViewById(R.id.tv_apply_time);
        }
    }

}
