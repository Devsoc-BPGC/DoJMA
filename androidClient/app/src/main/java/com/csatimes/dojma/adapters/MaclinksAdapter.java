package com.csatimes.dojma.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Maclinks;
import com.csatimes.dojma.utilities.FirebaseKeys;
import com.csatimes.dojma.viewholders.MacLinksViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MaclinksAdapter extends RecyclerView.Adapter<MacLinksViewHolder> implements ValueEventListener {

    private static final String TAG = "mac";
    private ArrayList<Maclinks> maclinks;

    public MaclinksAdapter() {
        DatabaseReference devRef = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseKeys.FIREBASE_DATABASE_REFERENCE_MACLINKS);
        devRef.addValueEventListener(this);
        devRef.keepSynced(true);
    }

    @Override
    public MacLinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View maclink = inflater.inflate(R.layout.item_format_maclinks, parent, false);
        return new MacLinksViewHolder(maclink);
    }

    @Override
    public void onBindViewHolder(MacLinksViewHolder holder, int position) {
        holder.populate(maclinks.get(position));
    }

    @Override
    public int getItemCount() {
        return maclinks.size();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot == null)
            return;
        if (maclinks != null)
            maclinks.clear();
        for (DataSnapshot child :
                dataSnapshot.getChildren()) {
            maclinks.add(child.getValue(Maclinks.class));
        }
        notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
    }
}
