package com.csatimes.dojma.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.Searchable;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.models.HeraldNewsItemFormat;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.viewholders.EventItemViewHolder;
import com.csatimes.dojma.viewholders.GazetteItemViewHolder;
import com.csatimes.dojma.viewholders.HeraldSearchViewHolder;
import com.csatimes.dojma.viewholders.SimpleTextViewHolder;

import java.util.List;

/**
 * adapter to place articles,gazettes in the search rv
 */

public class SearchRV extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TITLE_ITEM_TYPE = 0;
    private static final int ARTICLES_ITEM_TYPE = 1;
    private static final int GAZETTES_ITEM_TYPE = 2;
    private static final int EVENTS_ITEM_TYPE = 3;
    private SparseArray<List<Object>> results = new SparseArray<>();
    private SparseArray<String> titles = new SparseArray<>();
    private int articlesSize;
    private int gazettesSize;
    private int eventsSize;


    public SearchRV(SparseArray<List<Object>> results) {

        this.results = results;
        updateResult();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TITLE_ITEM_TYPE:
                View v1 = inflater.inflate(R.layout.viewholder_simple_text, parent, false);
                viewHolder = new SimpleTextViewHolder(v1);
                break;
            case ARTICLES_ITEM_TYPE:
                View v2 = inflater.inflate(R.layout.item_format_search_herald, parent, false);
                viewHolder = new HeraldSearchViewHolder(v2);
                break;
            case GAZETTES_ITEM_TYPE:
                View v3 = inflater.inflate(R.layout.item_format_gazette, parent, false);
                viewHolder = new GazetteItemViewHolder(v3);
                break;
            case EVENTS_ITEM_TYPE:
                View v4 = inflater.inflate(R.layout.item_format_event, parent, false);
                viewHolder = new EventItemViewHolder(v4);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (results != null && results.size() != 0) {
            if (holder.getItemViewType() == TITLE_ITEM_TYPE) {
                DHC.log("title type");
                SimpleTextViewHolder stvh = (SimpleTextViewHolder) holder;
                stvh.text.setText(titles.get(position));

            } else if (holder.getItemViewType() == ARTICLES_ITEM_TYPE) {
                HeraldSearchViewHolder hsvh = (HeraldSearchViewHolder) holder;

                HeraldNewsItemFormat foo = (HeraldNewsItemFormat) results.get(Searchable.SEARCHABLE_ARTICLES).get(position - 1);
                hsvh.title.setText(foo.getTitle_plain());
                hsvh.date.setText(foo.getUpdateDate());
                hsvh.simpleDraweeView.setImageURI(Uri.parse(foo.getImageURL()));
            } else if (holder.getItemViewType() == GAZETTES_ITEM_TYPE) {
                GazetteItemViewHolder givh = (GazetteItemViewHolder) holder;

                GazetteItem foo = (GazetteItem) results.get(Searchable.SEARCHABLE_GAZETTES).get(position - 2 - articlesSize);
                givh.title.setText(foo.getTitle());

            } else if (holder.getItemViewType() == EVENTS_ITEM_TYPE) {
                EventItemViewHolder eivh = (EventItemViewHolder) holder;

                EventItem foo = (EventItem) results.get(Searchable.SEARCHABLE_EVENTS).get(position - 3 - articlesSize - gazettesSize);
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

        this.articlesSize = this.results.get(Searchable.SEARCHABLE_ARTICLES).size();
        this.gazettesSize = this.results.get(Searchable.SEARCHABLE_GAZETTES).size();
        this.eventsSize = this.results.get(Searchable.SEARCHABLE_EVENTS).size();

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
            return TITLE_ITEM_TYPE;
        } else if (position >= 1 && position <= articlesSize)
            return ARTICLES_ITEM_TYPE;
        else if (position >= articlesSize + 2 && position <= articlesSize + 1 + gazettesSize)
            return GAZETTES_ITEM_TYPE;
        else if (position >= 3 + gazettesSize + articlesSize)
            return EVENTS_ITEM_TYPE;//10
        else return -1;
    }
}
