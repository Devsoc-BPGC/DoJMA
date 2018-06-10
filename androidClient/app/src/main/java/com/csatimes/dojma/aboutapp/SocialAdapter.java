package com.csatimes.dojma.aboutapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.SocialLink;
import com.csatimes.dojma.utilities.Browser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;

/**
 * @author Rushikesh Jogdand.
 */
public class SocialAdapter extends RecyclerView.Adapter<SocialLinkVh> {
    private final List<SocialLink> links = new ArrayList<>(0);
    private final Browser browser;

    public SocialAdapter(@NonNull final Browser browser) {
        getData();
        this.browser = browser;
    }

    private void getData() {
        final Realm realm = Realm.getDefaultInstance();
        links.clear();
        for (final SocialLink link : realm.where(SocialLink.class).findAll()) {
            links.add(realm.copyFromRealm(link));
        }
        realm.close();
    }

    @NonNull
    @Override
    public SocialLinkVh onCreateViewHolder(@NonNull final ViewGroup parent,
                                           final int viewType) {
        return new SocialLinkVh(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vh_social, parent, false),
                browser);
    }

    @Override
    public void onBindViewHolder(@NonNull final SocialLinkVh holder,
                                 final int position) {
        holder.populate(links.get(position));
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
