package com.example.reapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.myinterface.INotify;
import com.example.reapp.myinterface.ItemClickListener;
import com.example.reapp.myinterface.ItemLongClickListener;
import com.example.reapp.pojo.DepartmentInner;
import com.lock.myapplication.R;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> implements INotify {

    private List<DepartmentInner> departmentInners;

    private ItemClickListener clickListener;

    private ItemLongClickListener itemLongClickListener;

    public DepartmentAdapter(List<DepartmentInner> departmentInners, ItemClickListener clickListener,ItemLongClickListener itemLongClickListener) {
        this.departmentInners = departmentInners;
        this.clickListener = clickListener;
        this.itemLongClickListener = itemLongClickListener;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DepartmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.department_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        holder.tv_departmentName.setText("部门名称: " + departmentInners.get(position).getDepartmentName());
        if(departmentInners.get(position).getUserRole()==1){
            holder.tv_departmentRole.setText("角色: 部门创建者");
        }else if(departmentInners.get(position).getUserRole()==2){
            holder.tv_departmentRole.setText("角色: 部门管理员");
        }else if(departmentInners.get(position).getUserRole()==3){
            holder.tv_departmentRole.setText("角色: 用户");
        }
        holder.itemView.setOnClickListener(v -> {
            clickListener.onClick(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            itemLongClickListener.longClickListener(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return departmentInners.size();
    }

    @Override
    public void remove(int position) {
        departmentInners.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll() {
        departmentInners.clear();
        notifyDataSetChanged();
    }

    class DepartmentViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_departmentName;
        public TextView tv_departmentRole;

        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_departmentName = itemView.findViewById(R.id.department_name);
            tv_departmentRole = itemView.findViewById(R.id.department_role);
        }
    }

}
