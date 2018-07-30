package com.csatimes.dojma.herald;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.HeraldItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Herald adapter
 */
public class HeraldAdapter extends RecyclerView.Adapter<HeraldViewHolder>
        implements RealmChangeListener<RealmResults<HeraldItem>> {
    private final List<HeraldItem> data = new ArrayList<>(0);
    private Realm realm;
    private boolean mustBeFavorites = false;
    private String category;
    private RecyclerView mRv;

    @NonNull
    @Override
    public HeraldViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                               final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_format_herald, parent, false);
        return new HeraldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HeraldViewHolder viewHolder, final int position) {
        viewHolder.populate(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        realm = Realm.getDefaultInstance();
        mRv = recyclerView;
        queryDb();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull final RecyclerView recyclerView) {
        realm.close();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    private void queryDb() {
        final RealmQuery<HeraldItem> query = realm.where(HeraldItem.class);
        if (mustBeFavorites) {
            query.equalTo(HeraldItem.FAV, true);
        }
        if (category != null) {
            query.equalTo(HeraldItem.CATEGORY, category, Case.INSENSITIVE);
        }
        query.sort(EventItem.FIELD_ORIGINAL_DATE, Sort.DESCENDING);
        final RealmResults<HeraldItem> results = query.findAllAsync();
        results.addChangeListener(this);
    }

    public void onlyFavorites() {
        this.mustBeFavorites = true;
    }

    @Override
    public void onChange(@NonNull final RealmResults<HeraldItem> heraldItems) {
        data.clear();
        for (final HeraldItem item : heraldItems) {
            data.add(realm.copyFromRealm(item));
        }
        mRv.getRecycledViewPool().clear();
        notifyDataSetChanged();
    }

    public void setCategory(final String category) {
        this.category = category;
    }
}

