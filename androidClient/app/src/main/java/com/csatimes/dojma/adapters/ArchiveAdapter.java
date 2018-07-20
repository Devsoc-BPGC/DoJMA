package com.csatimes.dojma.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Archive;
import com.csatimes.dojma.viewholders.ArchiveViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveViewHolder>{

    private List<Archive> archiveItems;

    public ArchiveAdapter(List<Archive> archiveItems) {
        this.archiveItems = archiveItems;
    }

    @Override
    public ArchiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArchiveViewHolder(View.inflate(parent.getContext(), R.layout.item_format_archives, null));
    }

    @Override
    public void onBindViewHolder(ArchiveViewHolder holder, int position) {
        holder.populate(archiveItems.get(position));
    }

    @Override
    public int getItemCount() {
        return archiveItems.size();
    }
}
