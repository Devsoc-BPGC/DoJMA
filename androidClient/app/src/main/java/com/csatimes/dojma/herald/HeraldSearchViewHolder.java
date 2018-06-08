package com.csatimes.dojma.herald;

import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.articles.ArticleViewerActivity;
import com.csatimes.dojma.models.HeraldItem;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Herald item in Searchable activity
 */

public class HeraldSearchViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView date;
    public SimpleDraweeView simpleDraweeView;
    public HeraldItem item;

    public HeraldSearchViewHolder(final View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.search_herald_title);
        date = itemView.findViewById(R.id.search_herald_date);
        simpleDraweeView = itemView.findViewById(R.id.search_herald_image);

        itemView.setOnClickListener(v ->
                ArticleViewerActivity.readArticle(itemView.getContext(),
                        Integer.parseInt(item.getPostID()))
        );
    }
}
