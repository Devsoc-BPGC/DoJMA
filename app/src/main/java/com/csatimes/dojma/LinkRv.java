package com.csatimes.dojma;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.csatimes.dojma.models.LinkItem;

import java.util.Vector;

/**
 * Created by yash on 4/8/16.
 */

public class LinkRv extends RecyclerView.Adapter<LinkRv.ViewHolder> {
    Context context;
    Vector<LinkItem> linkItems;


    public LinkRv(Context context, Vector<LinkItem> linkItems){
        this.context = context;
        this.linkItems = linkItems;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos=position;
        holder.title.setText(linkItems.get(position).getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkItems.get(pos).getUrl()));

                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No application to load link! " + linkItems
                                    .get(pos).getUrl(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }

        );

    }

    @Override
    public int getItemCount() {
        return linkItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.gazette_item_format_text);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .gazette_item_format, parent, false));

    }
}
