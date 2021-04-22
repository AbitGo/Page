package com.example.reapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.myinterface.IMEICallback;
import com.example.reapp.myinterface.INotify;
import com.example.reapp.myinterface.ItemClickListener;
import com.example.reapp.myinterface.ItemTouchAdapterHelper;
import com.example.reapp.pojo.DeviceInfo;
import com.lock.myapplication.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> implements ItemTouchAdapterHelper, INotify {

    private List<DeviceInfo> deviceInfoList;
    private Context context;
    private ItemClickListener itemClickListener;
    private IMEICallback imeiCallback;

    public DeviceAdapter(Context context, IMEICallback imeiCallback, List<DeviceInfo> deviceInfoList, ItemClickListener itemClickListener) {
        this.deviceInfoList = deviceInfoList;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.imeiCallback = imeiCallback;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        String deviceName = context.getResources().getString(R.string.DeviceName);
        String deviceIMEI = context.getResources().getString(R.string.DeviceIMEI);
        String deviceInfo = context.getResources().getString(R.string.DeviceInfo);
        String deviceDepartmentCode = context.getResources().getString(R.string.DeviceDepartmentCode);
        holder.tv_name.setText(deviceName+" "+deviceInfoList.get(position).getDeviceName());
        holder.tv_imei.setText(deviceIMEI+" "+deviceInfoList.get(position).getDeviceIMEI());
        holder.tv_info.setText(deviceInfo+" "+deviceInfoList.get(position).getDeviceInfo());
        holder.tv_department_code.setText(deviceDepartmentCode+" "+deviceInfoList.get(position).getDepartmentCode());
        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return deviceInfoList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        imeiCallback.callIMEI(deviceInfoList.get(position).getDeviceIMEI());
        deviceInfoList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void remove(int position) {

    }

    @Override
    public void removeAll() {
        deviceInfoList.clear();
        notifyDataSetChanged();
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_name,tv_department_code,tv_imei,tv_info;
        public LinearLayout outLL;
        public ConstraintLayout cl;
        public ImageView iv;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_imei = itemView.findViewById(R.id.tv_imei);
            tv_department_code = itemView.findViewById(R.id.tv_department_code);
            tv_info = itemView.findViewById(R.id.tv_info);
            outLL = itemView.findViewById(R.id.outLL);
            cl = itemView.findViewById(R.id.cl);
            iv = itemView.findViewById(R.id.iv);
        }
    }

}
