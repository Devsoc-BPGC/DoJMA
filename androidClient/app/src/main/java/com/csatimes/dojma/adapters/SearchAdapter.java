package com.csatimes.dojma.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.herald.HeraldSearchViewHolder;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.models.TypeItem;
import com.csatimes.dojma.utilities.ColorList;
import com.csatimes.dojma.viewholders.ContactItemViewHolder;
import com.csatimes.dojma.viewholders.EventItemViewHolder;
import com.csatimes.dojma.viewholders.LinkItemViewHolder;
import com.csatimes.dojma.viewholders.MessItemViewHolder;
import com.csatimes.dojma.viewholders.SimpleTextViewHolder;

import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_TITLE;
import static com.csatimes.dojma.utilities.DHC.DAYS_IN_FN;
import static com.csatimes.dojma.utilities.DHC.DAYS_IN_MONTH;
import static com.csatimes.dojma.utilities.DHC.DAYS_IN_YR;
import static com.csatimes.dojma.utilities.DHC.MS_IN_DAY;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_EVENT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_HERALD_ARTICLE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_LINK;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_MESS;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_TITLE;

/**
 * Adapter to place articles in the search rv
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = SearchAdapter.class.getSimpleName();
    private final List<TypeItem> results;
    private final Context mContext;
    @Nullable
    private final Date mCurrentDate;
    @Nullable
    private final OnImageClicked onImageClicked;
    private Activity mActivity;

    public SearchAdapter(final Context mContext, final List<TypeItem> results) {
        this.results = results;
        this.mContext = mContext;
        this.mCurrentDate = null;
        this.onImageClicked = mContext instanceof OnImageClicked
                ? (OnImageClicked) this.mContext
                : null;

    }

    public SearchAdapter(final Activity activity, final List<TypeItem> results,
                         final Date mCurrentDate) {
        this.results = results;
        this.mContext = activity;
        this.mCurrentDate = mCurrentDate;
        this.mActivity = activity;
        this.onImageClicked = mActivity instanceof OnImageClicked
                ? (OnImageClicked) this.mActivity
                : null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                      final int viewType) {

        final RecyclerView.ViewHolder viewHolder;
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view;
        switch (viewType) {
            case CONTACT_ITEM_TYPE_TITLE:
            case SEARCH_ITEM_TYPE_TITLE: {
                view = inflater.inflate(R.layout.item_format_simple_text, parent, false);
                viewHolder = new SimpleTextViewHolder(view);
                break;
            }
            case SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE:
            case SEARCH_ITEM_TYPE_HERALD_ARTICLE: {
                return new HeraldSearchViewHolder(inflater
                        .inflate(R.layout.item_format_search_herald,
                                parent,
                                false)
                );
            }
            case SEARCH_ITEM_TYPE_EVENT: {
                view = inflater.inflate(R.layout.item_format_event, parent, false);
                viewHolder = new EventItemViewHolder(view, mContext);
                break;
            }
            case CONTACT_ITEM_TYPE_CONTACT:
            case SEARCH_ITEM_TYPE_CONTACT: {
                view = inflater.inflate(R.layout.item_format_contact, parent, false);
                viewHolder = new ContactItemViewHolder(view, mContext);
                break;
            }
            case SEARCH_ITEM_TYPE_LINK: {
                view = inflater.inflate(R.layout.item_format_links, parent, false);
                viewHolder = new LinkItemViewHolder(view, mActivity);
                break;
            }
            case SEARCH_ITEM_TYPE_MESS: {
                view = inflater.inflate(R.layout.item_format_mess_menu, parent, false);
                viewHolder = new MessItemViewHolder(view, onImageClicked);
                break;
            }
            default: {
                Log.e(TAG, "got empty view type");
                //noinspection AssignmentToNull
                viewHolder = null;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,
                                 final int position) {

        switch (getItemViewType(position)) {
            case SEARCH_ITEM_TYPE_TITLE:
            case CONTACT_ITEM_TYPE_TITLE:
                final SimpleTextViewHolder stvh = (SimpleTextViewHolder) holder;
                stvh.text.setText((CharSequence) results.get(position).getValue());
                break;

            case SEARCH_ITEM_TYPE_HERALD_ARTICLE:
            case SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE:
                final HeraldSearchViewHolder hsvh = (HeraldSearchViewHolder) holder;
                final HeraldItem hi = (HeraldItem) results.get(position).getValue();
                hsvh.item = hi;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    hsvh.title.setText(Html.fromHtml(hi.title, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    hsvh.title.setText(Html.fromHtml(hi.title));
                }

                hsvh.date.setText(hi.updateDate);
                hsvh.simpleDraweeView.setImageURI(Uri.parse(hi.thumbnailUrl));
                break;

            case SEARCH_ITEM_TYPE_EVENT:
                final EventItemViewHolder eivh = (EventItemViewHolder) holder;
                final EventItem ei = (EventItem) results.get(position).getValue();
                eivh.item = ei;
                eivh.title.setText(ei.getTitle());
                eivh.location.setText(ei.getLocation());
                eivh.dateTime.setText(String.format("%s\n%s", ei.getStartDateFormatted(), ei.getStartTimeFormatted()));
                eivh.desc.setText(ei.getDesc());
                eivh.up.setVisibility(View.INVISIBLE);
                eivh.down.setVisibility(View.INVISIBLE);
                setColor(ei, eivh);
                break;

            case SEARCH_ITEM_TYPE_CONTACT:
            case CONTACT_ITEM_TYPE_CONTACT:
                final ContactItemViewHolder civh = (ContactItemViewHolder) holder;
                final ContactItem ci = (ContactItem) results.get(position).getValue();
                civh.contactItem = ci;
                civh.contactName.setText(ci.name);

                if (!TextUtils.isEmpty(ci.number)) {
                    civh.contactCall.setVisibility(VISIBLE);
                } else {
                    civh.contactCall.setVisibility(GONE);
                }

                if (!TextUtils.isEmpty(ci.email)) {
                    civh.contactEmail.setVisibility(VISIBLE);
                } else {
                    civh.contactEmail.setVisibility(GONE);
                }

                if (!TextUtils.isEmpty(ci.sub1)) {
                    civh.contactSub1.setVisibility(VISIBLE);
                    civh.contactSub1.setText(ci.sub1);
                } else {
                    civh.contactSub1.setVisibility(GONE);
                }

                if (!TextUtils.isEmpty(ci.sub2)) {
                    civh.contactSub1.setVisibility(VISIBLE);
                    civh.contactSub2.setText(ci.sub2);
                } else {
                    civh.contactSub2.setVisibility(GONE);
                }

                if (!TextUtils.isEmpty(ci.icon)) {
                    civh.contactIcon.setImageURI(Uri.parse(ci.icon));
                } else {
                    civh.contactIcon.setImageURI(Uri.parse("res://" + mContext.getPackageName()
                            + "/" + R.drawable.ic_contact));
                }
                break;

            case SEARCH_ITEM_TYPE_LINK:
                final LinkItemViewHolder livh = (LinkItemViewHolder) holder;
                final LinkItem li = (LinkItem) results.get(position).getValue();
                livh.linkItem = li;
                livh.title.setText(li.getTitle());
                livh.url.setText(li.getUrl());
                break;

            case SEARCH_ITEM_TYPE_MESS:
                final MessItemViewHolder mivh = (MessItemViewHolder) holder;
                final MessItem mi = (MessItem) results.get(position).getValue();
                mivh.title.setText(mi.title);
                mivh.link = mi.imageUrl;
                mivh.image.setImageURI(Uri.parse(mivh.link));
                break;
            default:
                break;
        }
    }

    private void setColor(final EventItem ei, final EventItemViewHolder eivh) {
        if (ei.getStartDateObj() != null && mCurrentDate != null) {
            final long diff = -mCurrentDate.getTime() + ei.getStartDateObj().getTime();
            final int color;
            color = diff <= 0
                    ? ContextCompat.getColor(mContext, ColorList.NO_PRIORITY)
                    : getColorFromDate(diff);
            eivh.status.setColorFilter(color);
            eivh.dateTime.setTextColor(color);
        } else {
            eivh.dateTime.setTextColor(ContextCompat.getColor(mContext, ColorList.LOWEST_PRIORITY));
            eivh.status.setColorFilter(Color.GRAY);
        }
    }

    private int getColorFromDate(final long diff) {
        final int color;
        //noinspection IfStatementWithTooManyBranches
        if (diff > 0 && diff <= MS_IN_DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGHEST_PRIORITY);
        } else if (diff > MS_IN_DAY && diff <= 3 * MS_IN_DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGHER_PRIORITY);
        } else if (diff > 3 * MS_IN_DAY && diff <= 7 * MS_IN_DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGH_PRIORITY);
        } else if (diff > 7 * MS_IN_DAY && diff <= DAYS_IN_FN * MS_IN_DAY) {
            color = ContextCompat.getColor(mContext, ColorList.NORMAL_PRIORITY);
        } else if (diff > DAYS_IN_MONTH * MS_IN_DAY && diff <= DAYS_IN_YR * MS_IN_DAY) {
            color = ContextCompat.getColor(mContext, ColorList.LOWER_PRIORITY);
        } else {
            color = ContextCompat.getColor(mContext, ColorList.LOWEST_PRIORITY);
        }
        return color;
    }

    @Override
    public int getItemViewType(final int position) {
        return results.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public interface OnImageClicked {
        void onClicked(String uri);
    }
}
