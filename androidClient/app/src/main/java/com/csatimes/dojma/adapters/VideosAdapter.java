package com.csatimes.dojma.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.VideosItem;
import com.csatimes.dojma.viewholders.VideosViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideosAdapter extends RecyclerView.Adapter<VideosViewHolder> {
    private List<VideosItem> partList;

    public VideosAdapter(List<VideosItem> partList) {
        this.partList = partList;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_format_videos, parent, false);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder holder, int position) {
        VideosItem parts = partList.get(position);
        holder.populate(parts);
    }

    @Override
    public int getItemCount() {
        return partList.size();
    }
}
