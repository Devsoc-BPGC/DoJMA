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

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsViewHolder> {

    private List<String> contributorsList;

    public ContributorsAdapter(List<String> contributorsList) {
        this.contributorsList = contributorsList;
    }

    @Override
    public ContributorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContributorsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_format_contributors, parent, false));
    }

    @Override
    public void onBindViewHolder(ContributorsViewHolder holder, int position) {
        holder.nameTextView.setText(contributorsList.get(position));
    }

    @Override
    public int getItemCount() {
        return contributorsList.size();
    }
}
