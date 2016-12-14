package com.csatimes.dojma.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.GazetteItem;

import io.realm.RealmList;

/**
 * Adapter to handle the data in the rv
 */

public class GazettesRV extends RecyclerView.Adapter<GazettesRV.GazetteItemViewHolder> {

    private RealmList<GazetteItem> gazetteItems;
    private onGazetteItemClickedListener onGazetteItemClickedListener;

    public GazettesRV(RealmList<GazetteItem> gazetteItems) {
        this.gazetteItems = gazetteItems;
        this.onGazetteItemClickedListener = null;
    }

    @Override
    public GazettesRV.GazetteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GazetteItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_format_gazette, parent, false));
    }

    @Override
    public void onBindViewHolder(GazettesRV.GazetteItemViewHolder holder, int position) {
        holder.title.setText(gazetteItems.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return gazetteItems.size();
    }

    public void setOnGazetteItemClickedListener(GazettesRV.onGazetteItemClickedListener onGazetteItemClickedListener) {
        this.onGazetteItemClickedListener = onGazetteItemClickedListener;
    }

    public interface onGazetteItemClickedListener {
        void onClicked(String url, String title);
    }

    class GazetteItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        GazetteItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.gazette_item_format_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onGazetteItemClickedListener != null) {
                        onGazetteItemClickedListener.onClicked(gazetteItems.get(getAdapterPosition()).getUrl(), gazetteItems.get(getAdapterPosition()).getTitle());
                    }
                }
            });
        }
    }

}

