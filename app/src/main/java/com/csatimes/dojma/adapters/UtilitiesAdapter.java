package com.csatimes.dojma.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.viewholders.UtilitiesCampusMapViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesContactsViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesLinksViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesTitleSubTitleViewHolder;

import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_CONTACTS;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_CONTACTS_TAXI;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_LINKS;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_MAP;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_MESS;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_MISC;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String message;
    private Context context;
    private int taxiCardColor;
    private int taxiTitleColor;
    private int taxiSubTitleColor;
    private int messTitleColor;
    private int messSubTitleColor;
    private int messCardColor;
    private int miscCardColor;
    private int miscTitleColor;
    private int miscSubTitleColor;


    public UtilitiesAdapter(Context context, String message) {
        this.message = message;
        this.context = context;
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(new int[]{
                        R.attr.customContactsTaxiCardBackgroundColor,
                        R.attr.customContactsTaxiCardTitleColor,
                        R.attr.customContactsTaxiCardSubTitleColor,
                        R.attr.customMessCardBackgroundColor,
                        R.attr.customMessCardTitleColor,
                        R.attr.customMessCardSubTitleColor,
                        R.attr.customMiscCardBackgroundColor,
                        R.attr.customMiscCardTitleColor,
                        R.attr.customMiscCardSubTitleColor});
        taxiCardColor = a.getResourceId(0, -1);
        taxiTitleColor = a.getResourceId(1, -1);
        taxiSubTitleColor = a.getResourceId(2, -1);
        messCardColor = a.getResourceId(3, -1);
        messTitleColor = a.getResourceId(4, -1);
        messSubTitleColor = a.getResourceId(5, -1);
        miscCardColor = a.getResourceId(6, -1);
        miscTitleColor = a.getResourceId(7, -1);
        miscSubTitleColor = a.getResourceId(8, -1);
        a.recycle();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case UTILITIES_ITEM_TYPE_CONTACTS:
                view = inflater.inflate(R.layout.viewholder_utilities_contact, parent, false);
                viewHolder = new UtilitiesContactsViewHolder(view, parent.getContext());
                break;
            case UTILITIES_ITEM_TYPE_CONTACTS_TAXI:
                view = inflater.inflate(R.layout.viewholder_utilities_title_subtitle, parent, false);
                viewHolder = new UtilitiesTitleSubTitleViewHolder(view, parent.getContext(), viewType);
                break;
            case UTILITIES_ITEM_TYPE_MESS:
                view = inflater.inflate(R.layout.viewholder_utilities_title_subtitle, parent, false);
                viewHolder = new UtilitiesTitleSubTitleViewHolder(view, parent.getContext(), viewType);
                break;
            case UTILITIES_ITEM_TYPE_MISC:
                view = inflater.inflate(R.layout.viewholder_utilities_title_subtitle, parent, false);
                viewHolder = new UtilitiesTitleSubTitleViewHolder(view, parent.getContext(), viewType);
                break;
            case UTILITIES_ITEM_TYPE_LINKS:
                view = inflater.inflate(R.layout.viewholder_utilities_links, parent, false);
                viewHolder = new UtilitiesLinksViewHolder(view, parent.getContext());
                break;
            case UTILITIES_ITEM_TYPE_MAP:
                view = inflater.inflate(R.layout.viewholder_utilities_map, parent, false);
                viewHolder = new UtilitiesCampusMapViewHolder(view, parent.getContext());
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == UTILITIES_ITEM_TYPE_CONTACTS_TAXI) {
            UtilitiesTitleSubTitleViewHolder utsvh = (UtilitiesTitleSubTitleViewHolder) holder;
            utsvh.cardView.setCardBackgroundColor(ContextCompat.getColor(context, taxiCardColor));
            utsvh.title.setTextColor(ContextCompat.getColor(context, taxiTitleColor));
            utsvh.subTitle.setTextColor(ContextCompat.getColor(context, taxiSubTitleColor));
            utsvh.title.setText(R.string.UTILITIES_CONTACTS_TAXI_title);
            utsvh.subTitle.setText(R.string.UTILITIES_CONTACTS_TAXI_subtitle);
        } else if (holder.getItemViewType() == UTILITIES_ITEM_TYPE_MESS) {
            UtilitiesTitleSubTitleViewHolder utsvh = (UtilitiesTitleSubTitleViewHolder) holder;
            utsvh.cardView.setCardBackgroundColor(ContextCompat.getColor(context, messCardColor));
            utsvh.title.setTextColor(ContextCompat.getColor(context, messTitleColor));
            utsvh.subTitle.setTextColor(ContextCompat.getColor(context, messSubTitleColor));
            utsvh.title.setText(R.string.UTILITIES_MENU_title);
            utsvh.subTitle.setText(R.string.UTILITIES_MENU_subtitle);
        } else if (holder.getItemViewType() == UTILITIES_ITEM_TYPE_MISC) {
            UtilitiesTitleSubTitleViewHolder utsvh = (UtilitiesTitleSubTitleViewHolder) holder;
            utsvh.cardView.setCardBackgroundColor(ContextCompat.getColor(context, miscCardColor));
            utsvh.title.setTextColor(ContextCompat.getColor(context, miscTitleColor));
            utsvh.subTitle.setTextColor(ContextCompat.getColor(context, miscSubTitleColor));
            utsvh.title.setText(R.string.UTILITIES_MISC_title);
            utsvh.subTitle.setText(message);
        }
    }


    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return UTILITIES_ITEM_TYPE_CONTACTS;
            case 1:
                return UTILITIES_ITEM_TYPE_CONTACTS_TAXI;
            case 2:
                return UTILITIES_ITEM_TYPE_MESS;
            case 4:
                return UTILITIES_ITEM_TYPE_LINKS;
            case 3:
                return UTILITIES_ITEM_TYPE_MISC;
            case 5:
                return UTILITIES_ITEM_TYPE_MAP;
            default:
                return -1;
        }
    }
}
