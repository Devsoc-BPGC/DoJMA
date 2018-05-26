package com.csatimes.dojma.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.GazetteItem;
import com.facebook.drawee.view.SimpleDraweeView;

import io.realm.RealmList;

/**
 * Adapter to handle the data in the rv
 */

public class GazettesAdapter extends RecyclerView.Adapter<GazettesAdapter.GazetteItemViewHolder> {

    private RealmList<GazetteItem> gazetteItems;
    private onGazetteItemClickedListener onGazetteItemClickedListener;

    public GazettesAdapter(RealmList<GazetteItem> gazetteItems) {
        this.gazetteItems = gazetteItems;
        this.onGazetteItemClickedListener = null;
    }

    @Override
    public GazettesAdapter.GazetteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GazetteItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_format_gazette, parent, false));
    }

    @Override
    public void onBindViewHolder(GazettesAdapter.GazetteItemViewHolder holder, int position) {
        holder.title.setText(gazetteItems.get(position).getTitle() + "\n"
                + gazetteItems.get(position).getReleaseDateFormatted());
        holder.image.setImageURI(gazetteItems.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return gazetteItems.size();
    }

    public void setOnGazetteItemClickedListener(GazettesAdapter.onGazetteItemClickedListener onGazetteItemClickedListener) {
        this.onGazetteItemClickedListener = onGazetteItemClickedListener;
    }

    public interface onGazetteItemClickedListener {
        void onClicked(GazetteItem gi);
    }

    class GazetteItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public SimpleDraweeView image;

        GazetteItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_format_gazette_title);
            image = (SimpleDraweeView) itemView.findViewById(R.id.item_format_gazette_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onGazetteItemClickedListener != null) {
                        onGazetteItemClickedListener.onClicked(gazetteItems.get(getAdapterPosition()));
                    }
                }
            });

        }
    }


}

