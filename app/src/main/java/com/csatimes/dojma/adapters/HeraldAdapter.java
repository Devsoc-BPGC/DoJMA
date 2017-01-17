package com.csatimes.dojma.adapters;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.interfaces.ItemTouchHelperAdapter;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.utilities.CircleImageDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;

/**
 * Herald adapter
 */

public class HeraldAdapter extends RecyclerView.Adapter<HeraldAdapter.HeraldViewHolder> implements ItemTouchHelperAdapter {
    //Listeners
    private OnLikeClickedListener mOnLikeClickedListener;
    private OnShareClickedListener mOnShareClickedListener;
    private OnItemClickedListener mOnItemClickedListener;
    private OnScrollUpdateListener mOnScrollUpdateListener;
    private RealmList<HeraldItem> data;
    public HeraldAdapter(RealmList<HeraldItem> data) {
        this.mOnLikeClickedListener = null;
        this.mOnShareClickedListener = null;
        this.mOnItemClickedListener = null;
        this.mOnScrollUpdateListener = null;
        this.data = data;
    }

    @Override
    public HeraldAdapter.HeraldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View herald_card_view_format = inflater.inflate(R.layout.item_format_herald, parent, false);
        return new HeraldViewHolder(herald_card_view_format);

    }

    @Override
    public void onBindViewHolder(HeraldAdapter.HeraldViewHolder viewHolder, int position) {

        HeraldItem foobar = data.get(position);
        viewHolder.item = foobar;

        //Shift this method to HeraldItemFormat
        try {
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            Date of = simpleDate.parse(foobar.getOriginalDate());
            SimpleDateFormat tf = new SimpleDateFormat("dd MMM , ''yy", Locale.UK);
            viewHolder.date.setText(tf.format(of));
        } catch (Exception e) {
            viewHolder.date.setText(foobar.getOriginalDate());
        }

        if (foobar.isFav())
            viewHolder.fav.setLiked(true);
        else viewHolder.fav.setLiked(false);

        //since Html.fromHtml is deprecated from N onwards we add the special flag
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewHolder.desc.setText(Html.fromHtml(foobar.getExcerpt(), Html.FROM_HTML_MODE_LEGACY));
            viewHolder.title.setText(Html.fromHtml(foobar.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.desc.setText(Html.fromHtml(foobar.getExcerpt()));
            viewHolder.title.setText(Html.fromHtml(foobar.getTitle()));
        }
        viewHolder.imageView.setImageURI(Uri.parse(foobar.getImageURL()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(data.get(position).getPostID());
    }

    public void setOnLikeClickedListener(OnLikeClickedListener onLikeClickedListener) {
        this.mOnLikeClickedListener = onLikeClickedListener;
    }

    public void setOnShareClickedListener(OnShareClickedListener mOnShareClickedListener) {
        this.mOnShareClickedListener = mOnShareClickedListener;
    }

    public void setOnItemClickedListener(OnItemClickedListener mOnItemClickedListener) {
        this.mOnItemClickedListener = mOnItemClickedListener;
    }

    public void setOnScrollUpdateListener(OnScrollUpdateListener mOnScrollUpdateListener) {
        this.mOnScrollUpdateListener = mOnScrollUpdateListener;
    }

    @Override
    public void onItemDismiss(int position) {
        //Also update database of un fav article
        if (mOnItemClickedListener != null)
            mOnLikeClickedListener.onDisLiked(data.get(position).getPostID());
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemEmployed(int position, HeraldItem foo) {
        //Also update database of un fav article
        if (mOnItemClickedListener != null)
            mOnLikeClickedListener.onLiked(foo.getPostID());
        data.add(position,foo);
        notifyItemInserted(position);
        if (mOnScrollUpdateListener!=null){
            mOnScrollUpdateListener.onUpdate(position);
        }
    }

    public interface OnLikeClickedListener {
        void onLiked(String postID);

        void onDisLiked(String postID);
    }

    public interface OnShareClickedListener {
        void onShare(String postID);
    }

    public interface OnItemClickedListener {
        void onClick(String postID);
    }

    public interface OnScrollUpdateListener {
        void onUpdate(int pos);
    }

    public class HeraldViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public HeraldItem item;
        TextView title;
        TextView date;
        SimpleDraweeView imageView;
        TextView desc;
        LikeButton fav;
        ImageButton share;

        HeraldViewHolder(View itemView) {
            super(itemView);

            imageView = (SimpleDraweeView) itemView.findViewById(R.id.item_format_herald_image);
            date = (TextView) itemView.findViewById(R.id.item_format_herald_date);
            title = (TextView) itemView.findViewById(R.id.item_format_herald_title);
            desc = (TextView) itemView.findViewById(R.id.item_format_herald_desc);
            fav = (LikeButton) itemView.findViewById(R.id.item_format_herald_heart);
            share = (ImageButton) itemView.findViewById(R.id.item_format_herald_share);
            imageView.getHierarchy().setProgressBarImage(new CircleImageDrawable());

            fav.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    mOnLikeClickedListener.onLiked(data.get(getAdapterPosition()).getPostID());
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    mOnLikeClickedListener.onDisLiked(data.get(getAdapterPosition()).getPostID());
                }
            });
            itemView.setOnClickListener(this);
            share.setOnClickListener(this);

        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void onClick(View view) {

            String postID = item.getPostID();

            if (view.getId() == itemView.getId()) {
                mOnItemClickedListener.onClick(postID);
            } else if (view.getId() == share.getId()) {
                mOnShareClickedListener.onShare(postID);
            }

        }


    }
}
