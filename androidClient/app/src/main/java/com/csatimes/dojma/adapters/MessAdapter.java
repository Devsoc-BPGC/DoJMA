package com.csatimes.dojma.adapters;

import android.content.Context;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.viewholders.MessItemViewHolder;

import io.realm.RealmList;

/**
 * Created by vikramaditya on 22/12/16.
 */

public class MessAdapter extends RecyclerView.Adapter<MessItemViewHolder> {

    private RealmList<MessItem> messItems;
    private SearchAdapter.OnImageClicked onImageClicked;

    public MessAdapter(RealmList<MessItem> messItems, Context context) {
        this.messItems = messItems;
        this.onImageClicked = (SearchAdapter.OnImageClicked) context;
    }

    @Override
    public MessItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessItemViewHolder(View.inflate(parent.getContext(), R.layout.item_format_mess_menu, null),
                onImageClicked);
    }

    @Override
    public void onBindViewHolder(MessItemViewHolder holder, int position) {
        holder.title.setText(messItems.get(position).getTitle());
        holder.link = messItems.get(position).getImageUrl();
        holder.image.setImageURI(Uri.parse(holder.link));
    }

    @Override
    public int getItemCount() {
        return messItems.size();
    }
}
