package com.csatimes.dojma.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.LinkItem;

import io.realm.RealmList;

public class LinkRv extends RecyclerView.Adapter<LinkRv.LinkItemViewHolder> {
    private RealmList<LinkItem> linkItems;
    private OnLinkClickedListener onLinkClickedListener;

    public LinkRv(RealmList<LinkItem> linkItems) {
        this.linkItems = linkItems;
        this.onLinkClickedListener = null;
    }

    @Override
    public LinkRv.LinkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinkRv.LinkItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_format_links, parent, false));

    }

    @Override
    public void onBindViewHolder(LinkRv.LinkItemViewHolder holder, int position) {
        holder.title.setText(linkItems.get(position).getTitle());
        holder.url.setText(linkItems.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return linkItems.size();
    }

    public void setOnLinkClickedListener(OnLinkClickedListener onLinkClickedListener) {
        this.onLinkClickedListener = onLinkClickedListener;
    }

    public interface OnLinkClickedListener {
        void onClick(String url);
    }

    class LinkItemViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView url;

        LinkItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_format_links_title);
            url = (TextView) itemView.findViewById(R.id.item_format_links_url);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onLinkClickedListener != null)
                        onLinkClickedListener.onClick(linkItems.get(getAdapterPosition()).getUrl());
                }
            });
        }
    }
}
