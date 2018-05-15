package com.csatimes.dojma.callbacks;

import android.graphics.Canvas;
import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;

import com.csatimes.dojma.adapters.HeraldAdapter;
import com.csatimes.dojma.interfaces.ItemTouchHelperAdapter;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.utilities.DHC;

/**
 * Created by vikramaditya on 15/1/17.
 */

public class SimpleItemTouchCallback extends ItemTouchHelper.Callback {
    private ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchCallback(
            ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final HeraldItem foo = ((HeraldAdapter.HeraldViewHolder) viewHolder).item;
        final int pos = viewHolder.getAdapterPosition();
        mAdapter.onItemDismiss(pos);
        Snackbar snackbar = DHC.makeCustomSnackbar(viewHolder.itemView, "Removed from favourites", 0xFF3b5998, Color.WHITE);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.onItemEmployed(pos, foo);
            }
        });
        snackbar.show();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, androidx.recyclerview.widget.RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY,
                actionState, isCurrentlyActive);
    }
}
