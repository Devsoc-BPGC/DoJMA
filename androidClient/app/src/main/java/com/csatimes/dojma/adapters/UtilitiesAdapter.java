package com.csatimes.dojma.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.viewholders.UtilitiesCampusMapViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesContactsViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesLinksViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesTitleSubTitleViewHolder;

import static com.csatimes.dojma.utilities.DHC.CONTACTS;
import static com.csatimes.dojma.utilities.DHC.CONTACTS_TAXI;
import static com.csatimes.dojma.utilities.DHC.LINKS;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_MAP;
import static com.csatimes.dojma.utilities.DHC.MESS;
import static com.csatimes.dojma.utilities.DHC.MISC;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum itemTypes {
        CONTACTS, CONTACTS_TAXI, LINKS, MAP, MESS, MISC
    }

    private static final String TAG = UtilitiesAdapter.class.getSimpleName();
    private final String message;
    private final Context context;
    private final int taxiCardColor;
    private final int taxiTitleColor;
    private final int taxiSubTitleColor;
    private final int messTitleColor;
    private final int messSubTitleColor;
    private final int messCardColor;
    private final int miscCardColor;
    private final int miscTitleColor;
    private final int miscSubTitleColor;


    public UtilitiesAdapter(final Context context, final String message) {
        this.message = message;
        this.context = context;
        final TypedArray a = context.getTheme()
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
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view;
        switch (viewType) {
            case CONTACTS: {
                view = inflater.inflate(R.layout.viewholder_utilities_contact, parent, false);
                viewHolder = new UtilitiesContactsViewHolder(view, parent.getContext());
                break;
            }
            case CONTACTS_TAXI: {
                view = inflater.inflate(R.layout.viewholder_utilities_title_subtitle, parent, false);
                viewHolder = new UtilitiesTitleSubTitleViewHolder(view, parent.getContext(), viewType);
                break;
            }
            case MESS: {
                view = inflater.inflate(R.layout.viewholder_utilities_title_subtitle, parent, false);
                viewHolder = new UtilitiesTitleSubTitleViewHolder(view, parent.getContext(), viewType);
                break;
            }
            case MISC: {
                view = inflater.inflate(R.layout.viewholder_utilities_title_subtitle, parent, false);
                viewHolder = new UtilitiesTitleSubTitleViewHolder(view, parent.getContext(), viewType);
                break;
            }
            case LINKS: {
                view = inflater.inflate(R.layout.viewholder_utilities_links, parent, false);
                viewHolder = new UtilitiesLinksViewHolder(view, parent.getContext());
                break;
            }
            case UTILITIES_ITEM_TYPE_MAP: {
                view = inflater.inflate(R.layout.viewholder_utilities_map, parent, false);
                viewHolder = new UtilitiesCampusMapViewHolder(view, parent.getContext());
                break;
            }
            default: {
                //noinspection DuplicateStringLiteralInspection
                Log.e(TAG, "got empty view type");
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == CONTACTS_TAXI) {
            final UtilitiesTitleSubTitleViewHolder utsvh = (UtilitiesTitleSubTitleViewHolder) holder;
            utsvh.cardView.setCardBackgroundColor(ContextCompat.getColor(context, taxiCardColor));
            utsvh.title.setTextColor(ContextCompat.getColor(context, taxiTitleColor));
            utsvh.subTitle.setTextColor(ContextCompat.getColor(context, taxiSubTitleColor));
            utsvh.title.setText(R.string.UTILITIES_CONTACTS_TAXI_title);
            utsvh.subTitle.setText(R.string.UTILITIES_CONTACTS_TAXI_subtitle);
        } else if (holder.getItemViewType() == MESS) {
            final UtilitiesTitleSubTitleViewHolder utsvh = (UtilitiesTitleSubTitleViewHolder) holder;
            utsvh.cardView.setCardBackgroundColor(ContextCompat.getColor(context, messCardColor));
            utsvh.title.setTextColor(ContextCompat.getColor(context, messTitleColor));
            utsvh.subTitle.setTextColor(ContextCompat.getColor(context, messSubTitleColor));
            utsvh.title.setText(R.string.menus);
            utsvh.subTitle.setText(R.string.UTILITIES_MENU_subtitle);
        } else if (holder.getItemViewType() == MISC) {
            final UtilitiesTitleSubTitleViewHolder utsvh = (UtilitiesTitleSubTitleViewHolder) holder;
            utsvh.cardView.setCardBackgroundColor(ContextCompat.getColor(context, miscCardColor));
            utsvh.title.setTextColor(ContextCompat.getColor(context, miscTitleColor));
            utsvh.subTitle.setTextColor(ContextCompat.getColor(context, miscSubTitleColor));
            utsvh.title.setText(R.string.miscellaneous);
            utsvh.subTitle.setText(message);
        }
    }


    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(final int position) {
        switch (position) {
            case 0:
                return CONTACTS;
            case 1:
                return CONTACTS_TAXI;
            case 2:
                return MESS;
            case 4:
                return LINKS;
            case 3:
                return MISC;
            case 5:
                return UTILITIES_ITEM_TYPE_MAP;
            default:
                return -1;
        }
    }
}
