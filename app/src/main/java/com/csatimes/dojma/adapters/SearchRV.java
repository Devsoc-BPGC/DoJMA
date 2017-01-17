package com.csatimes.dojma.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.SearchableActivity;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.viewholders.EventItemViewHolder;
import com.csatimes.dojma.viewholders.GazetteItemViewHolder;
import com.csatimes.dojma.viewholders.HeraldSearchViewHolder;
import com.csatimes.dojma.viewholders.LinkItemViewHolder;
import com.csatimes.dojma.viewholders.SimpleTextViewHolder;

import java.util.List;

/**
 * adapter to place articles,gazettes in the search rv
 */

public class SearchRV extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_TITLE = 0;
    private static final int ITEM_TYPE_ARTICLES_FAVOURITES = 1;
    private static final int ITEM_TYPE_ARTICLES = 2;
    private static final int ITEM_TYPE_GAZETTES = 3;
    private static final int ITEM_TYPE_EVENTS = 4;
    private static final int ITEM_TYPE_CONTACTS = 5;
    private static final int ITEM_TYPE_LINKS = 6;

    private SparseArray<List<Object>> results = new SparseArray<>();
    private SparseArray<String> titles = new SparseArray<>();


    private int articlesSize;
    private int favArticlesSize;
    private int gazettesSize;
    private int eventsSize;
    private int contactsSize;
    private int linksSize;


    public SearchRV(SparseArray<List<Object>> results) {

        this.results = results;
        updateResult();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM_TYPE_TITLE:
                View v1 = inflater.inflate(R.layout.viewholder_simple_text, parent, false);
                viewHolder = new SimpleTextViewHolder(v1);
                break;
            case ITEM_TYPE_ARTICLES_FAVOURITES:
                View v2 = inflater.inflate(R.layout.item_format_search_herald, parent, false);
                viewHolder = new HeraldSearchViewHolder(v2);
                break;

            case ITEM_TYPE_ARTICLES:
                View v3 = inflater.inflate(R.layout.item_format_search_herald, parent, false);
                viewHolder = new HeraldSearchViewHolder(v3);
                break;
            case ITEM_TYPE_GAZETTES:
                View v4 = inflater.inflate(R.layout.item_format_search_gazette, parent, false);
                viewHolder = new GazetteItemViewHolder(v4);
                break;
            case ITEM_TYPE_EVENTS:
                View v5 = inflater.inflate(R.layout.item_format_event, parent, false);
                viewHolder = new EventItemViewHolder(v5);
                break;
            case ITEM_TYPE_CONTACTS:
                View v6 = inflater.inflate(R.layout.item_format_contact, parent, false);
                //viewHolder = new ContactAdapter.ContactViewHolder(v6);
                break;
            case ITEM_TYPE_LINKS:
                View v7 = inflater.inflate(R.layout.item_format_links, parent, false);
                viewHolder = new LinkItemViewHolder(v7);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (results != null && results.size() != 0) {
            if (holder.getItemViewType() == ITEM_TYPE_TITLE) {
                DHC.log("title type");
                SimpleTextViewHolder stvh = (SimpleTextViewHolder) holder;
                stvh.text.setText(titles.get(position));

            } else if (holder.getItemViewType() == ITEM_TYPE_ARTICLES) {
                HeraldSearchViewHolder hsvh = (HeraldSearchViewHolder) holder;

                HeraldItem foo = (HeraldItem) results.get(SearchableActivity.SEARCHABLE_HERALD).get(position - 1);
                hsvh.title.setText(foo.getTitle_plain());
                hsvh.date.setText(foo.getUpdateDate());
                hsvh.simpleDraweeView.setImageURI(Uri.parse(foo.getImageURL()));
            } else if (holder.getItemViewType() == ITEM_TYPE_GAZETTES) {
                GazetteItemViewHolder givh = (GazetteItemViewHolder) holder;

                GazetteItem foo = (GazetteItem) results.get(SearchableActivity.SEARCHABLE_GAZETTES).get(position - 2 - articlesSize);
                givh.title.setText(foo.getTitle() + "\n" + foo.getReleaseDateFormatted());
                givh.image.setImageURI(foo.getImageUrl());

            } else if (holder.getItemViewType() == ITEM_TYPE_EVENTS) {
                EventItemViewHolder eivh = (EventItemViewHolder) holder;

                EventItem foo = (EventItem) results.get(SearchableActivity.SEARCHABLE_EVENTS).get(position - 3 - articlesSize - gazettesSize);
                eivh.title.setText(foo.getTitle());
                eivh.location.setText(foo.getLocation());
                eivh.dateTime.setText(foo.getStartTime());
                eivh.dateTime.setText(foo.getStartDate());
                eivh.desc.setText(foo.getDesc());
            }
        }

    }

    @Override
    public int getItemCount() {

        if (results != null && results.size() != 0) {
            int total = 0;
            //This is for the titles of each type inflated using SimpleTextViewHolder
            total += results.size();

            //These are for the actual items
            total += articlesSize;
            total += gazettesSize;
            total += eventsSize;
            return total;
        } else return 0;
    }


    public void updateResult() {

        this.articlesSize = this.results.get(SearchableActivity.SEARCHABLE_HERALD).size();
        this.gazettesSize = this.results.get(SearchableActivity.SEARCHABLE_GAZETTES).size();
        this.eventsSize = this.results.get(SearchableActivity.SEARCHABLE_EVENTS).size();

        this.titles.put(0, "Herald(" + articlesSize + ")");
        this.titles.put(1 + articlesSize, "Gazettes(" + gazettesSize + ")");
        this.titles.put(2 + articlesSize + gazettesSize, "Events(" + eventsSize + ")");


    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0
                || articlesSize + 1 == position
                || articlesSize + 2 + gazettesSize == position
                ) {
            return ITEM_TYPE_TITLE;
        } else if (position >= 1 && position <= articlesSize)
            return ITEM_TYPE_ARTICLES;
        else if (position >= articlesSize + 2 && position <= articlesSize + 1 + gazettesSize)
            return ITEM_TYPE_GAZETTES;
        else if (position >= 3 + gazettesSize + articlesSize)
            return ITEM_TYPE_EVENTS;//10
        else return -1;
    }
}
