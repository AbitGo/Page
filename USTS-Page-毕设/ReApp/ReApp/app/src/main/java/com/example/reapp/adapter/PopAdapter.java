package com.example.reapp.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.myinterface.ItemClickListener;
import com.lock.myapplication.R;

import java.util.List;

public class PopAdapter extends RecyclerView.Adapter<PopAdapter.PopViewHolder> {

    private List<String> strings;

    private ItemClickListener itemClickListener;

    public PopAdapter(List<String> strings, ItemClickListener itemClickListener) {
        this.strings = strings;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_item, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull PopViewHolder holder, int position) {
        holder.textView.setText(strings.get(position) + "");
        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class PopViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public PopViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title);
        }
    }
}
