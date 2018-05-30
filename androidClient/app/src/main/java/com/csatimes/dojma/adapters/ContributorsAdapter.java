package com.csatimes.dojma.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.viewholders.ContributorsViewHolder;

import java.util.List;

/**
 * Created by vikramaditya on 24/2/17.
 */

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsViewHolder> implements ValueEventListener {

    private ArrayList<Contributor> contributors;
    private Context context;
    private Activity activity;
    Exception e;

    public ContributorsAdapter(Context context) {
        this.context = context;
        populate();
        DatabaseReference devRef = FirebaseDatabase.getInstance().getReference()
                .child(DHC.FIREBASE_DATABASE_REFERENCE_CONTRIBUTORS);
        devRef.addValueEventListener(this);
        devRef.keepSynced(true);
    }

    @Override
    public ContributorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View contributor = inflater.inflate(R.layout.item_format_contributors, parent, false);
        return new ContributorsViewHolder(contributor, context);
    }

    @Override
    public void onBindViewHolder(ContributorsViewHolder holder, int position) {
        holder.populate(contributors.get(position));

    }

    @Override
    public int getItemCount() {
        return (contributors.size());
    }

    private void populate() {
        contributors = new ArrayList<>();
        contributors.add(new Contributor(
                "Vikramaditya Kukreja",
                "+919619104435",
                "kukreja.vikramaditya@gmail.com",
                "https://github.com/kukreja-vikramaditya",
                "https://lh4.googleusercontent.com/-0dhUBhZKH94/AAAAAAAAAAI/AAAAAAAAACQ/F7fd4BSFRsY/s96-c/photo.jpg"));
        contributors.add(new Contributor(
                "Rushikesh Jogdand",
                "+917083413997",
                "rushikesh@jogdand.com",
                "https://jogdand.com",
                "https://lh4.googleusercontent.com/-ooZffw6cRtU/AAAAAAAAAAI/AAAAAAAAEf0/27Nk35sCSr8/s96-c/photo.jpg"));
        contributors.add(new Contributor(
                "Tanmay Dixit",
                "9820511699",
                "tanmaydixit1102@gmail.com",
                "https://github.com/tanmaydixit",
                ""));
        contributors.add(new Contributor(
                "Yash Sharan",
                "9552003049",
                "yashsharan.0805@gmail.com",
                "https://github.com/yashsharan",
                ""));
        contributors.add(new Contributor(
                "Agrim Agarwal",
                "9521350428",
                "agrimagarwal12356789@gmail.com",
                "https://github.com/Agrim1234",
                "https://drive.google.com/open?id=1itd1rgB7s-7LTrobLwyV3s9T7j8hSGBI"
        ));
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot == null) return;
        contributors.clear();
        for (DataSnapshot child :
                dataSnapshot.getChildren()) {
            contributors.add(child.getValue(Contributor.class));
        }
        notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        DHC.log("Error accessing Firebase data for developers");
    }

}
