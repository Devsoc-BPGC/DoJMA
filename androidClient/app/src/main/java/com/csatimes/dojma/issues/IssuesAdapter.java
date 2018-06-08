package com.csatimes.dojma.issues;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.HeraldItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author Rushikesh Jogdand.
 */
public class IssuesAdapter extends RecyclerView.Adapter<IssueVh>
        implements RealmChangeListener<RealmResults<HeraldItem>> {
    private final List<String> issuesList = new ArrayList<>(0);
    private Realm realm;

    @NonNull
    @Override
    public IssueVh onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.viewholder_issue,
                parent,
                false);
        return new IssueVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IssueVh holder, final int position) {
        holder.populate(issuesList.get(position));
    }

    @Override
    public int getItemCount() {
        return issuesList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        realm = Realm.getDefaultInstance();
        startQuery();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull final RecyclerView recyclerView) {
        realm.close();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    private void startQuery() {
        final RealmResults<HeraldItem> res = realm.where(HeraldItem.class)
                .distinct(HeraldItem.CATEGORY)
                .sort(HeraldItem.CATEGORY, Sort.ASCENDING)
                .findAllAsync();
        res.addChangeListener(this);
    }

    @Override
    public void onChange(@NonNull final RealmResults<HeraldItem> heraldItems) {
        issuesList.clear();
        for (final HeraldItem item : heraldItems) {
            issuesList.add(item.getCategory());
        }
        notifyDataSetChanged();
    }
}
