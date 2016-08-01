package com.csatimes.dojma;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;
import com.turingtechnologies.materialscrollbar.IDateableAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class HeraldRV extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        IDateableAdapter {
    private Context context;
    private RealmList<HeraldNewsItemFormat> resultsList;
    private Realm database;
    private int dismissPosition;
    private boolean isGoogleChromeInstalled = false;
    private CustomTabsIntent customTabsIntent;
    private Activity activity;
    private boolean textState = false;
    private boolean landscape = false;

    public HeraldRV(Context context, RealmList<HeraldNewsItemFormat> resultsList, Realm
            database, Activity activity) {
        this.context = context;
        this.resultsList = resultsList;
        this.database = database;
        this.activity = activity;

    }

    public static Date parseDate(String date) {
        try {
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            return simpleDate.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            View herald_card_view_format = inflater.inflate(R.layout.herald_card_view_format, parent, false);
            return new HeraldPotraitViewHolder(herald_card_view_format);
        } else {
            View herald_card_view_format_landscape = inflater.inflate(R.layout.herald_card_view_format_landscape, parent, false);
            return new HeraldLandscapeViewHolder(herald_card_view_format_landscape);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == 0) {
            HeraldPotraitViewHolder viewHolder = (HeraldPotraitViewHolder) holder;
            final HeraldNewsItemFormat foobar = resultsList.get(position);


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
            Picasso.with(context).load(Uri.parse(foobar.getImageURL())).transform(new CircleCropTransformation()).into(viewHolder.imageView);
        } else {
            HeraldLandscapeViewHolder viewHolder = (HeraldLandscapeViewHolder) holder;
            final HeraldNewsItemFormat foobar = resultsList.get(position);
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
//        if (foobar.isRead()) {
//            // holder.card.setCardBackgroundC(ContextCompat.getColor(context,R.color
//            //       .cardview_shadow_end_color));
//        }

            //since Html.fromHtml is deprecated from N onwards we add the special flag
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                viewHolder.desc.setText(Html.fromHtml(foobar.getExcerpt(), Html.FROM_HTML_MODE_LEGACY));
                viewHolder.title.setText(Html.fromHtml(foobar.getTitle(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                viewHolder.desc.setText(Html.fromHtml(foobar.getExcerpt()));
                viewHolder.title.setText(Html.fromHtml(foobar.getTitle()));
            }
            Picasso.with(context).load(Uri.parse(foobar.getImageURL())).transform(new CircleCropTransformation()).into(viewHolder.imageView);

        }
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (landscape) return 1;
        else return 0;
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public Date getDateForElement(int element) {
        return parseDate(resultsList.get(element).getOriginalDate());
    }

    void setGoogleChromeInstalled(boolean isGoogleChromeInstalled) {
        this.isGoogleChromeInstalled = isGoogleChromeInstalled;
    }

    private class HeraldPotraitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView date;
        ImageView imageView;
        TextView desc;
        LikeButton fav;
        ImageButton share;

        HeraldPotraitViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.herald_rv_item_image);
            date = (TextView) itemView.findViewById(R.id.herald_rv_item_date);
            title = (TextView) itemView.findViewById(R.id.herald_rv_item_title);
            desc = (TextView) itemView.findViewById(R.id.herald_rv_desc);
            fav = (LikeButton) itemView.findViewById(R.id.herald_like_button);
            share = (ImageButton) itemView.findViewById(R.id.herald_rv_share_button);
            itemView.setOnClickListener(this);
            fav.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    database.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            HeraldNewsItemFormat foo = realm.where(HeraldNewsItemFormat.class)
                                    .equalTo("postID", resultsList.get(getAdapterPosition()).getPostID
                                            ()).findFirst();
                            foo.setFav(true);
                        }
                    });
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    database.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            HeraldNewsItemFormat foo = realm.where(HeraldNewsItemFormat.class)
                                    .equalTo("postID", resultsList.get(getAdapterPosition()).getPostID
                                            ()).findFirst();
                            foo.setFav(false);
                        }
                    });
                }
            });
            share.setOnClickListener(this);
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void onClick(View view) {
            {
                if (view.getId() == itemView.getId()) {
                    if (isNetworkAvailable(context)) {
                        if (isGoogleChromeInstalled) {
                            try {
                                Intent intent = new Intent((Intent.ACTION_SEND));
                                intent.putExtra(android.content.Intent.EXTRA_TEXT, resultsList.get
                                        (getAdapterPosition()).getUrl());

                                Intent copy_intent = new Intent(context, CopyLinkBroadcastReceiver.class);
                                PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(context, 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                String copy_label = "Copy Link";

                                customTabsIntent = new CustomTabsIntent.Builder()
                                        .setShowTitle(true)
                                        .setToolbarColor(ContextCompat.getColor(context, R.color
                                                .blue500))
                                        .setCloseButtonIcon(BitmapFactory.decodeResource(context
                                                .getResources(), R.drawable.ic_arrow_back_white_24dp))
                                        // .addDefaultShareMenuItem()
                                        .addMenuItem(copy_label, copy_pendingIntent)
                                        .setStartAnimations(context, R.anim.slide_in_right, R.anim.fade_out)
                                        .setExitAnimations(context, R.anim.fade_in, R.anim.slide_out_right)
                                        .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.amber500))
                                        //.setActionButton(BitmapFactory.decodeResource(context
                                        // .getResources(), R.drawable.ic_share_white_24dp), "Share",
                                        //PendingIntent.getActivity(context, 69,
                                        //  intent, PendingIntent.FLAG_UPDATE_CURRENT), true)
                                        .addDefaultShareMenuItem()
                                        .enableUrlBarHiding()
                                        .build();

                                CustomTabActivityHelper.openCustomTab(activity, customTabsIntent,
                                        Uri.parse(resultsList.get(getAdapterPosition()).getUrl()),
                                        new CustomTabActivityHelper.CustomTabFallback() {
                                            @Override
                                            public void openUri(Activity activity, Uri uri) {
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                intent.putExtra(Intent.EXTRA_REFERRER,
                                                        Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + context.getPackageName()));

                                                context.startActivity(intent);
                                            }
                                        });
                            } catch (Exception e) {
                                Intent openWebpage = new Intent(context, OpenWebpage.class);

                                openWebpage.putExtra("URL", resultsList.get(getAdapterPosition()).getUrl());
                                openWebpage.putExtra("TITLE", resultsList.get(getAdapterPosition()).getTitle());
                                openWebpage.putExtra("POSTID", resultsList.get(getAdapterPosition()).getPostID());

                                context.startActivity(openWebpage);

                            }
                        } else {
                            Intent openWebpage = new Intent(context, OpenWebpage.class);

                            openWebpage.putExtra("URL", resultsList.get(getAdapterPosition()).getUrl());
                            openWebpage.putExtra("TITLE", resultsList.get(getAdapterPosition()).getTitle());
                            openWebpage.putExtra("POSTID", resultsList.get(getAdapterPosition()).getPostID());

                            context.startActivity(openWebpage);
                        }
                    } else {
                        Intent intent = new Intent(context, OfflineSimpleViewer.class);
                        intent.putExtra("POSTID", resultsList.get(getAdapterPosition()).getPostID());
                        context.startActivity(intent);
                    }
                } else if (view.getId() == share.getId()) {
                    Intent intent = new Intent((Intent.ACTION_SEND));
                    intent.setType("text/plain");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, resultsList.get
                            (getAdapterPosition()).getUrl());
                    context.startActivity(Intent.createChooser(intent, "Share url via"));


                }

            }

        }
    }

    private class HeraldLandscapeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView date;
        ImageView imageView;
        TextView desc;
        LikeButton fav;
        ImageButton share;

        public HeraldLandscapeViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.herald_rv_item_image);
            date = (TextView) itemView.findViewById(R.id.herald_rv_item_date);
            title = (TextView) itemView.findViewById(R.id.herald_rv_item_title);
            desc = (TextView) itemView.findViewById(R.id.herald_rv_desc);
            fav = (LikeButton) itemView.findViewById(R.id.herald_like_button);
            share = (ImageButton) itemView.findViewById(R.id.herald_rv_share_button);
            itemView.setOnClickListener(this);
            fav.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    database.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            HeraldNewsItemFormat foo = realm.where(HeraldNewsItemFormat.class)
                                    .equalTo("postID", resultsList.get(getAdapterPosition()).getPostID
                                            ()).findFirst();
                            foo.setFav(true);
                        }
                    });
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    database.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            HeraldNewsItemFormat foo = realm.where(HeraldNewsItemFormat.class)
                                    .equalTo("postID", resultsList.get(getAdapterPosition()).getPostID
                                            ()).findFirst();
                            foo.setFav(false);
                        }
                    });
                }
            });
            share.setOnClickListener(this);
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void onClick(View view) {
            {
                if (view.getId() == itemView.getId()) {
                    if (isNetworkAvailable(context)) {
                        if (isGoogleChromeInstalled) {
                            Intent intent = new Intent((Intent.ACTION_SEND));
                            intent.putExtra(android.content.Intent.EXTRA_TEXT, resultsList.get
                                    (getAdapterPosition()).getUrl());

                            Intent copy_intent = new Intent(context, CopyLinkBroadcastReceiver.class);
                            PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(context, 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            String copy_label = "Copy Link";

                            customTabsIntent = new CustomTabsIntent.Builder()
                                    .setShowTitle(true)
                                    .setToolbarColor(ContextCompat.getColor(context, R.color
                                            .blue500))
                                    .setCloseButtonIcon(BitmapFactory.decodeResource(context
                                            .getResources(), R.drawable.ic_arrow_back_white_24dp))
                                    // .addDefaultShareMenuItem()
                                    .addMenuItem(copy_label, copy_pendingIntent)
                                    .setStartAnimations(context, R.anim.slide_in_right, R.anim.fade_out)
                                    .setExitAnimations(context, R.anim.fade_in, R.anim.slide_out_right)
                                    .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.amber500))
                                    .setActionButton(BitmapFactory.decodeResource(context
                                                    .getResources(), R.drawable.ic_share_white_24dp), "Share",
                                            PendingIntent.getActivity(context, 69,
                                                    intent, PendingIntent.FLAG_UPDATE_CURRENT), true)
                                    .addDefaultShareMenuItem()
                                    .enableUrlBarHiding()
                                    .build();

                            CustomTabActivityHelper.openCustomTab(activity, customTabsIntent,
                                    Uri.parse(resultsList.get(getAdapterPosition()).getUrl()),
                                    new CustomTabActivityHelper.CustomTabFallback() {
                                        @Override
                                        public void openUri(Activity activity, Uri uri) {
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            intent.putExtra(Intent.EXTRA_REFERRER,
                                                    Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + context.getPackageName()));

                                            context.startActivity(intent);
                                        }
                                    });
                        } else {
                            Intent openWebpage = new Intent(context, OpenWebpage.class);

                            openWebpage.putExtra("URL", resultsList.get(getAdapterPosition()).getUrl());
                            openWebpage.putExtra("TITLE", resultsList.get(getAdapterPosition()).getTitle());
                            openWebpage.putExtra("POSTID", resultsList.get(getAdapterPosition()).getPostID());

                            context.startActivity(openWebpage);
                        }
                    } else {
                        Intent intent = new Intent(context, OfflineSimpleViewer.class);
                        intent.putExtra("POSTID", resultsList.get(getAdapterPosition()).getPostID
                                ());
                        context.startActivity(intent);
                    }
                } else if (view.getId() == share.getId()) {
                    Intent intent = new Intent((Intent.ACTION_SEND));
                    intent.setType("text/plain");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, resultsList.get
                            (getAdapterPosition()).getUrl());
                    context.startActivity(Intent.createChooser(intent, "Share url via"));


                }

            }

        }
    }

}
