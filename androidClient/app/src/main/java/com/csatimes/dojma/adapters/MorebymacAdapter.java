package com.csatimes.dojma.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Contributor;
import com.csatimes.dojma.models.Morebymac;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.viewholders.ContributorsViewHolder;
import com.csatimes.dojma.viewholders.MorebymacViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MorebymacAdapter extends RecyclerView.Adapter<MorebymacViewHolder> implements ValueEventListener{

    ArrayList<Morebymac> moreApps;
    private Context context;
    private Activity activity;

    public MorebymacAdapter(Context context) {
        this.context = context;
        DatabaseReference devRef = FirebaseDatabase.getInstance().getReference()
                .child(DHC.FIREBASE_DATABASE_REFERENCE_MOREAPPS);
        devRef.addValueEventListener(this);
        devRef.keepSynced(true);
    }

    @Override
    public MorebymacViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View morebymac = inflater.inflate(R.layout.item_format_contributors, parent, false);
        return new MorebymacViewHolder(morebymac, morebymac.getContext());
    }

    @Override
    public void onBindViewHolder(MorebymacViewHolder holder, int position) {
        holder.populate(moreApps.get(position));
    }

    @Override
    public int getItemCount() {
        if(moreApps == null)
            return 0;
        else
            return moreApps.size();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot == null)
            return;

        if (moreApps != null)
        {moreApps.clear();}
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            moreApps.add(child.getValue(Morebymac.class));
        }
        notifyDataSetChanged();

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
    }
}
