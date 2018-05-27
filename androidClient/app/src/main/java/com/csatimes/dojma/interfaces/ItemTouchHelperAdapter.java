package com.csatimes.dojma.interfaces;

import com.csatimes.dojma.models.HeraldItem;

/**
 * Created by vikramaditya on 15/1/17.
 */

public interface ItemTouchHelperAdapter {
    void onItemDismiss(int position);

    void onItemEmployed(int position, HeraldItem foo);
}
