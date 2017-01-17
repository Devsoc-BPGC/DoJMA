package com.csatimes.dojma.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.viewholders.MessItemRVViewHolder;

import io.realm.RealmList;

/**
 * Created by vikramaditya on 22/12/16.
 */

public class MessAdapter extends RecyclerView.Adapter<MessItemRVViewHolder> {

    RealmList<MessItem> messItems;

    public MessAdapter(RealmList<MessItem> messItems) {
        this.messItems = messItems;
    }

    @Override
    public MessItemRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessItemRVViewHolder(View.inflate(parent.getContext(), R.layout.item_format_mess_menu, null));
    }

    @Override
    public void onBindViewHolder(MessItemRVViewHolder holder, int position) {
        holder.title.setText(messItems.get(position).getTitle());
        holder.image.setImageURI(messItems.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return messItems.size();
    }
}
