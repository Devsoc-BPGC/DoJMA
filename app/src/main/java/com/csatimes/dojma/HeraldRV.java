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
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.analytics.Tracker;
import com.like.LikeButton;
import com.like.OnLikeListener;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class HeraldRV extends RecyclerView.Adapter<HeraldRV.ViewHolder> implements
        ItemTouchHelperAdapter, View.OnClickListener {
    private Context context;
    private RealmList<HeraldNewsItemFormat> resultsList;
    private int pixels = DHC.dpToPx(25);
    private Realm database;
    private int dismissPosition;
    private Tracker mTracker;
    private boolean isGoogleChromeInstalled = false;
    private CustomTabsIntent customTabsIntent;
    private Activity activity;
    private RecyclerView recyclerView;

    public HeraldRV(Context context, RealmList<HeraldNewsItemFormat> resultsList, Realm
            database, Activity activity) {
        this.context = context;
        this.resultsList = resultsList;
        this.database = database;
        this.activity = activity;
        Fresco.initialize(context);


//        AnalyticsApplication application = (AnalyticsApplication) getActivity()
//                .getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Herald");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void setGoogleChromeInstalled(boolean isGoogleChromeInstalled) {
        this.isGoogleChromeInstalled = isGoogleChromeInstalled;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View herald_card_view_format = inflater.inflate(R.layout.herald_card_view_format, parent, false);
        // Return a new holder instance
        return new ViewHolder(herald_card_view_format);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final HeraldNewsItemFormat foobar = resultsList.get(position);
        final int pos = position;

        holder.date.setText(foobar.getOriginalDate());
        if (foobar.getAuthorName() != null)
            holder.author.setText(foobar.getAuthorName());
        else if (foobar.getAuthorSlug() != null)
            holder.author.setText(foobar.getAuthorSlug());
        else holder.author.setText("dojam_Admin");


        if (foobar.isFav())
            holder.fav.setLiked(true);
        else holder.fav.setLiked(false);

        if (foobar.isRead()) {
            // holder.card.setCardBackgroundC(ContextCompat.getColor(context,R.color
            //       .cardview_shadow_end_color));
        }
        //since Html.fromHtml is deprecated from N onwards we add the special flag
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.desc.setText(Html.fromHtml(foobar.getExcerpt(), Html.FROM_HTML_MODE_LEGACY));
            holder.title.setText(Html.fromHtml(foobar.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.desc.setText(Html.fromHtml(foobar.getExcerpt()));
            holder.title.setText(Html.fromHtml(foobar.getTitle()));
        }
        try {
            holder.imageView.setImageURI(Uri.parse(foobar.getImageURL())

            );
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        Fresco.shutDown();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView rv) {

        database.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                HeraldNewsItemFormat temp = database.where(HeraldNewsItemFormat.class).equalTo
                        ("postID", resultsList.get(position).getPostID()).findFirst();
                temp.setDismissed(true);
            }
        });
        Log.e("TAG", "dismissed at " + position);
        dismissPosition = position;
        this.resultsList.remove(position);
        this.notifyItemRemoved(position);

        Snackbar.make(rv, "Article dismissed", Snackbar.LENGTH_LONG).setAction("UNDO", this).show();

    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onClick(View view) {
        database.beginTransaction();
        HeraldNewsItemFormat temp = database.where(HeraldNewsItemFormat.class).equalTo
                ("dismissed", true).findAll().last();
        temp.setDismissed(false);

        database.commitTransaction();

        resultsList.add(dismissPosition, temp);
        this.notifyItemInserted(dismissPosition);
        recyclerView.smoothScrollToPosition(dismissPosition);
    }


    private boolean isNetworkAvailable(Context context) {
        Log.e("TAG", "in isNetworkAvailable");
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SimpleDraweeView imageView;
        public TextView title;
        public TextView author;
        public TextView date;
        public TextView desc;
        public LikeButton fav;
        public CardView card;
        public ImageButton share;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.herald_rv_item_image);
            author = (TextView) itemView.findViewById(R.id.herald_rv_item_author);
            date = (TextView) itemView.findViewById(R.id.herald_rv_item_date);
            title = (TextView) itemView.findViewById(R.id.herald_rv_item_title);
            desc = (TextView) itemView.findViewById(R.id.herald_rv_desc);
            fav = (LikeButton) itemView.findViewById(R.id.herald_like_button);
            card = (CardView) itemView;
            share = (ImageButton) itemView.findViewById(R.id.herald_rv_share_button);
            imageView.getHierarchy().setProgressBarImage(new CircleImageDrawable());

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
                        Log.e("TAG", "internet is there");
                        if (isGoogleChromeInstalled) {
                            Log.e("TAG", "gc installed");
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
                                    .setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arrow_back))
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
                            Log.e("TAG", "openWebpage");
                            Intent openWebpage = new Intent(context, OpenWebpage.class);

                            openWebpage.putExtra("URL", resultsList.get(getAdapterPosition()).getUrl());
                            openWebpage.putExtra("TITLE", resultsList.get(getAdapterPosition()).getTitle());
                            openWebpage.putExtra("POSTID", resultsList.get(getAdapterPosition()).getPostID());

                            context.startActivity(openWebpage);
                        }
                    } else {
                        Log.e("TAG", "reached intent");
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
                    context.startActivity(Intent.createChooser(intent, "Share link via"));


                }

            }

        }
    }

}
