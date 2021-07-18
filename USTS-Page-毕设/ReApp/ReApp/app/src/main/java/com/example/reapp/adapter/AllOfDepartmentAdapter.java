package com.example.reapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.myinterface.INotify;
import com.example.reapp.pojo.DepartmentInfo;
import com.lock.myapplication.R;

import java.util.List;

public class AllOfDepartmentAdapter extends RecyclerView.Adapter<AllOfDepartmentAdapter.AllOfDepartmentViewHolder> implements INotify {

    private List<DepartmentInfo> departmentInfoList;

    private Context context;

    public AllOfDepartmentAdapter(Context context, List<DepartmentInfo> departmentInfoList) {
        this.context = context;
        this.departmentInfoList = departmentInfoList;
    }

    @NonNull
    @Override
    public AllOfDepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllOfDepartmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_of_department_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllOfDepartmentViewHolder holder, int position) {
        String name = context.getResources().getString(R.string.AODName);
        String email = context.getResources().getString(R.string.AODEmail);
        holder.tv_name.setText(departmentInfoList.get(position).getUserName());
        holder.tv_email.setText(email + departmentInfoList.get(position).getUserEmail());
        holder.tv_role.setText("用户组: "+getCode(departmentInfoList.get(position).getUserRole()));
        holder.itemView.setOnClickListener(v -> {});
    }

    @Override
    public int getItemCount() {
        return departmentInfoList.size();
    }

    @Override
    public void remove(int position) {

    }

    @Override
    public void removeAll() {
        departmentInfoList.clear();
        notifyDataSetChanged();
    }

    class AllOfDepartmentViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name,tv_email,tv_role;

        public AllOfDepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_role = itemView.findViewById(R.id.tv_role);
        }
    }

    private String getCode(int code){
        switch (code){
            case 1:
                return "部门创建者";
            case 2:
                return "管理员";
            case 3:
                return "用户";
        }
        return "";
    }

}
