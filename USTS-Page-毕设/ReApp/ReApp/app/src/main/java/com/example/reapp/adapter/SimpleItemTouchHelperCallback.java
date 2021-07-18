package com.example.reapp.adapter;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.myinterface.ItemTouchAdapterHelper;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback{

    private ItemTouchAdapterHelper itemTouchAdapterHelper;

    public SimpleItemTouchHelperCallback(ItemTouchAdapterHelper itemTouchAdapterHelper) {
        this.itemTouchAdapterHelper = itemTouchAdapterHelper;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchAdapterHelper.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.itemView.scrollTo(-(int) dX, 0);
            if (viewHolder instanceof DeviceAdapter.DeviceViewHolder) {
                try {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((DeviceAdapter.DeviceViewHolder) viewHolder).iv.getLayoutParams();
                    RecyclerView.LayoutParams rParams = (RecyclerView.LayoutParams) ((DeviceAdapter.DeviceViewHolder) viewHolder).outLL.getLayoutParams();
                    int width = viewHolder.itemView.getWidth();
                    int height = viewHolder.itemView.getHeight();
                    int ivWidth = ((DeviceAdapter.DeviceViewHolder) viewHolder).iv.getWidth();
                    int intDx = (int) dX;
//                    ((DeviceAdapter.DeviceViewHolder) viewHolder).cl.layout(intDx,0,width + intDx, height);
//                    ((DeviceAdapter.DeviceViewHolder) viewHolder).iv.layout((int) (width + dX), 0, (int) (width + ivWidth + dX), height);
                    rParams.width = width;
                    rParams.height = height;
                    ((DeviceAdapter.DeviceViewHolder) viewHolder).outLL.setLayoutParams(rParams);
                    System.out.println("layout" + dX);
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
//        viewHolder.itemView.setScrollX(0);
    }

    public int getSlideLimitation(RecyclerView.ViewHolder viewHolder){
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        return viewGroup.getChildAt(0).getLayoutParams().height;
    }
}
