package com.csatimes.dojma.aboutapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.AndroidApp;
import com.csatimes.dojma.utilities.Browser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * @author Rushikesh Jogdand.
 */
public class AndroidAppAdapter extends RecyclerView.Adapter<AndroidAppVh>
        implements RealmChangeListener<RealmResults<AndroidApp>> {
    private final Realm realm = Realm.getDefaultInstance();
    private final Browser browser;
    private final List<AndroidApp> apps = new ArrayList<>(0);

    public AndroidAppAdapter(final Browser browser) {
        getData();
        this.browser = browser;
    }

    private void getData() {
        realm.where(AndroidApp.class).findAllAsync().addChangeListener(this);
    }

    @NonNull
    @Override
    public AndroidAppVh onCreateViewHolder(@NonNull final ViewGroup parent,
                                           final int viewType) {
        return new AndroidAppVh(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vh_android_app, parent, false),
                browser
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final AndroidAppVh holder,
                                 final int position) {
        holder.populate(apps.get(position));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public void onChange(final RealmResults<AndroidApp> androidApps) {
        apps.clear();
        for (final AndroidApp app : androidApps) {
            apps.add(realm.copyFromRealm(app));
        }
        notifyDataSetChanged();
    }
}
