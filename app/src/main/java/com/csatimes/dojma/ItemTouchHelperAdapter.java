package com.csatimes.dojma;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Vikramaditya Kukreja on 27-06-2016.
 */

public interface ItemTouchHelperAdapter {
    void onItemDismiss(int position, RecyclerView rv);
}
