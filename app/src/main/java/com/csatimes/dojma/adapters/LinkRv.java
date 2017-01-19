package com.csatimes.dojma.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.viewholders.LinkItemViewHolder;

import io.realm.RealmList;

public class LinkRv extends RecyclerView.Adapter<LinkItemViewHolder> {
    private RealmList<LinkItem> linkItems;
    private Context context;

    public LinkRv(RealmList<LinkItem> linkItems, Context context) {
        this.linkItems = linkItems;
        this.context = context;
    }

    @Override
    public LinkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout
                .item_format_links, parent, false);
        return new LinkItemViewHolder(view,context);

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
