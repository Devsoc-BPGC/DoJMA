package com.csatimes.dojma.adapters;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.viewholders.LinkItemViewHolder;

import io.realm.RealmList;

public class LinkRv extends RecyclerView.Adapter<LinkItemViewHolder> {
    private RealmList<LinkItem> linkItems;
    private Activity activity;

    public LinkRv(RealmList<LinkItem> linkItems, Activity activity) {
        this.linkItems = linkItems;
        this.activity = activity;
    }

    @Override
    public LinkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout
                .item_format_links, parent, false);
        return new LinkItemViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(LinkItemViewHolder holder, int position) {
        LinkItem foo = linkItems.get(position);
        holder.title.setText(foo.getTitle());
        holder.url.setText(foo.getUrl());
        holder.linkItem = foo;
    }

    @Override
    public int getItemCount() {
        return linkItems.size();
    }
}
