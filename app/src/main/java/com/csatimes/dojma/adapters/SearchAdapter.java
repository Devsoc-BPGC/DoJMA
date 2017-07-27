package com.csatimes.dojma.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.models.TypeItem;
import com.csatimes.dojma.utilities.ColorList;
import com.csatimes.dojma.viewholders.ContactItemViewHolder;
import com.csatimes.dojma.viewholders.EventItemViewHolder;
import com.csatimes.dojma.viewholders.GazetteItemViewHolder;
import com.csatimes.dojma.viewholders.HeraldSearchViewHolder;
import com.csatimes.dojma.viewholders.LinkItemViewHolder;
import com.csatimes.dojma.viewholders.MessItemViewHolder;
import com.csatimes.dojma.viewholders.SimpleTextViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_TITLE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_EVENT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_GAZETTE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_HERALD_ARTICLE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_LINK;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_MESS;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_POSTER;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_TITLE;

/**
 * adapter to place articles,gazettes in the search rv
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TypeItem> results = new ArrayList<>();
    private Context mContext;
    private Date mCurrentDate;
    private Activity mActivity;

    public SearchAdapter(Context mContext, List<TypeItem> results) {
        this.results = results;
        this.mContext = mContext;
        this.mCurrentDate = null;
        if (mContext instanceof OnImageClicked) {
            this.onImageClicked = (OnImageClicked) this.mContext;
        } else onImageClicked = null;

    }

    public SearchAdapter(Activity activity, List<TypeItem> results, Date mCurrentDate) {
        this.results = results;
        this.mContext = activity;
        this.mCurrentDate = mCurrentDate;
        this.mActivity = activity;
        if (mActivity instanceof OnImageClicked) {
            this.onImageClicked = (OnImageClicked) this.mActivity;
        } else onImageClicked = null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case CONTACT_ITEM_TYPE_TITLE:
            case SEARCH_ITEM_TYPE_TITLE:
                view = inflater.inflate(R.layout.item_format_simple_text, parent, false);
                viewHolder = new SimpleTextViewHolder(view);
                break;
            case SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE:
            case SEARCH_ITEM_TYPE_HERALD_ARTICLE:
                view = inflater.inflate(R.layout.item_format_search_herald, parent, false);
                viewHolder = new HeraldSearchViewHolder(view, mContext, mActivity);
                break;
            case SEARCH_ITEM_TYPE_GAZETTE:
                view = inflater.inflate(R.layout.item_format_search_gazette, parent, false);
                viewHolder = new GazetteItemViewHolder(view);
                break;
            case SEARCH_ITEM_TYPE_EVENT:
                view = inflater.inflate(R.layout.item_format_event, parent, false);
                viewHolder = new EventItemViewHolder(view, mContext);
                break;
            case CONTACT_ITEM_TYPE_CONTACT:
            case SEARCH_ITEM_TYPE_CONTACT:
                view = inflater.inflate(R.layout.item_format_contact, parent, false);
                viewHolder = new ContactItemViewHolder(view, mContext);
                break;
            case SEARCH_ITEM_TYPE_LINK:
                view = inflater.inflate(R.layout.item_format_links, parent, false);
                viewHolder = new LinkItemViewHolder(view, mActivity);
                break;
            case SEARCH_ITEM_TYPE_MESS:
                view = inflater.inflate(R.layout.item_format_mess_menu, parent, false);
                viewHolder = new MessItemViewHolder(view, onImageClicked);
                break;
            case SEARCH_ITEM_TYPE_POSTER:
                // view = inflater.inflate(R.layout.item_format_links, parent, false);
                //viewHolder = new PosIte(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case SEARCH_ITEM_TYPE_TITLE:
            case CONTACT_ITEM_TYPE_TITLE:
                SimpleTextViewHolder stvh = (SimpleTextViewHolder) holder;
                stvh.text.setText((String) results.get(position).getValue());
                break;

            case SEARCH_ITEM_TYPE_HERALD_ARTICLE:
            case SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE:
                HeraldSearchViewHolder hsvh = (HeraldSearchViewHolder) holder;
                HeraldItem hi = (HeraldItem) results.get(position).getValue();
                hsvh.item = hi;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    hsvh.title.setText(Html.fromHtml(hi.getTitle(), Html.FROM_HTML_MODE_LEGACY));
                } else hsvh.title.setText(Html.fromHtml(hi.getTitle()));

                hsvh.date.setText(hi.getUpdateDate());
                hsvh.simpleDraweeView.setImageURI(Uri.parse(hi.getThumbnailUrl()));
                break;

            case SEARCH_ITEM_TYPE_GAZETTE:
                GazetteItemViewHolder givh = (GazetteItemViewHolder) holder;
                GazetteItem gi = (GazetteItem) results.get(position).getValue();
                givh.title.setText(gi.getTitle() + "\n" + gi.getReleaseDateFormatted());
                givh.image.setImageURI(gi.getImageUrl());
                break;

            case SEARCH_ITEM_TYPE_EVENT:
                EventItemViewHolder eivh = (EventItemViewHolder) holder;
                EventItem ei = (EventItem) results.get(position).getValue();
                eivh.item = ei;
                eivh.title.setText(ei.getTitle());
                eivh.location.setText(ei.getLocation());
                eivh.dateTime.setText(ei.getStartDateFormatted() + "\n" + ei.getStartTimeFormatted());
                eivh.desc.setText(ei.getDesc());
                eivh.up.setVisibility(View.INVISIBLE);
                eivh.down.setVisibility(View.INVISIBLE);
                setColor(ei, eivh);
                break;

            case SEARCH_ITEM_TYPE_CONTACT:
            case CONTACT_ITEM_TYPE_CONTACT:
                ContactItemViewHolder civh = (ContactItemViewHolder) holder;
                ContactItem ci = (ContactItem) results.get(position).getValue();
                civh.contactItem = ci;
                civh.contactName.setText(ci.getName());

                if (ci.getNumber() != null && ci.getNumber().length() != 0) {
                    civh.contactCall.setVisibility(VISIBLE);
                } else {
                    civh.contactCall.setVisibility(GONE);
                }

                if (ci.getEmail() != null && ci.getEmail().length() != 0) {
                    civh.contactEmail.setVisibility(VISIBLE);
                } else {
                    civh.contactEmail.setVisibility(GONE);
                }

                if (ci.getSub1() != null && ci.getSub1().length() != 0) {
                    civh.contactSub1.setVisibility(View.VISIBLE);
                    civh.contactSub1.setText(ci.getSub1());
                } else {
                    civh.contactSub1.setVisibility(View.GONE);
                }

                if (ci.getSub2() != null && ci.getSub2().length() != 0) {
                    civh.contactSub1.setVisibility(View.VISIBLE);
                    civh.contactSub2.setText(ci.getSub2());
                } else {
                    civh.contactSub2.setVisibility(View.GONE);
                }

                if (ci.getIcon() != null && ci.getIcon().length() != 0) {
                    civh.contactIcon.setImageURI(Uri.parse(ci.getIcon()));
                } else {
                    civh.contactIcon.setImageURI(Uri.parse("res://" + mContext.getPackageName()
                            + "/" + R.drawable.ic_contact));
                }
                break;

            case SEARCH_ITEM_TYPE_LINK:
                LinkItemViewHolder livh = (LinkItemViewHolder) holder;
                LinkItem li = (LinkItem) results.get(position).getValue();
                livh.linkItem = li;
                livh.title.setText(li.getTitle());
                livh.url.setText(li.getUrl());
                break;

            case SEARCH_ITEM_TYPE_MESS:
                MessItemViewHolder mivh = (MessItemViewHolder) holder;
                MessItem mi = (MessItem) results.get(position).getValue();
                mivh.title.setText(mi.getTitle());
                mivh.link = mi.getImageUrl();
                mivh.image.setImageURI(Uri.parse(mivh.link));
                break;
            default:
                break;
        }
    }

    private void setColor(final EventItem ei, final EventItemViewHolder eivh) {
        if (ei.getStartDateObj() != null) {
            long diff = -mCurrentDate.getTime() + ei.getStartDateObj().getTime();
            int color;

            if (diff <= 0) {
                //Irrespective of whether alarm was set, switch is unchecked since it isn't required anymore
                color = ContextCompat.getColor(mContext, ColorList.NO_PRIORITY);

            } else {
                color = getColorFromDate(diff);
            }
            eivh.status.setColorFilter(color);
            eivh.dateTime.setTextColor(color);

        } else {
            eivh.dateTime.setTextColor(ContextCompat.getColor(mContext, ColorList.LOWEST_PRIORITY));
            eivh.status.setColorFilter(Color.GRAY);
        }
    }

    private int getColorFromDate(final long diff) {
        int color;
        long DAY = 24 * 60 * 60 * 1000;
        if (diff > 0 && diff <= DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGHEST_PRIORITY);
        } else if (diff > DAY && diff <= 3 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGHER_PRIORITY);
        } else if (diff > 3 * DAY && diff <= 7 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGH_PRIORITY);
        } else if (diff > 7 * DAY && diff <= 14 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.NORMAL_PRIORITY);
        } else if (diff > 14 * DAY && diff <= 30 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.LOW_PRIORITY);
        } else if (diff > 30 * DAY && diff <= 365 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.LOWER_PRIORITY);
        } else {
            color = ContextCompat.getColor(mContext, ColorList.LOWEST_PRIORITY);
        }
        return color;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return results.get(position).getType();
    }


    public OnImageClicked onImageClicked;

    public interface OnImageClicked {
        void onClicked(String uri);
    }
}
