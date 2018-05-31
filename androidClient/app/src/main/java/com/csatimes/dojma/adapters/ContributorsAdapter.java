package com.csatimes.dojma.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Contributor;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.viewholders.ContributorsViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikramaditya on 24/2/17.
 */

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsViewHolder> implements ValueEventListener {

    private static final String TAG = "mac";
    private ArrayList<Contributor> contributors;
    private Context context;

    public ContributorsAdapter(Context context) {
        this.context = context;
        DatabaseReference devRef = FirebaseDatabase.getInstance().getReference()
                .child(DHC.FIREBASE_DATABASE_REFERENCE_CONTRIBUTORS);
        devRef.addValueEventListener(this);
        devRef.keepSynced(true);
    }

    @Override
    public ContributorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View contributor = inflater.inflate(R.layout.item_format_contributors, parent, false);
        return new ContributorsViewHolder(contributor);
    }

    @Override
    public void onBindViewHolder(ContributorsViewHolder holder, int position) {
        holder.populate(contributors.get(position));
    }

    @Override
    public int getItemCount() {
        if(contributors == null)
            return 0;
        else
            return contributors.size();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot == null)
            return;
        if (contributors != null)
            contributors.clear();
        for (DataSnapshot child :
                dataSnapshot.getChildren()) {
            contributors.add(child.getValue(Contributor.class));
        }
        notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
    }

}
