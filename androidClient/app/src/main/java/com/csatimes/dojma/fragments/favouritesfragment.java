package com.csatimes.dojma.fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.OfflineSimpleViewerActivity;
import com.csatimes.dojma.adapters.HeraldAdapter;
import com.csatimes.dojma.callbacks.SimpleItemTouchCallback;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.csatimes.dojma.utilities.CustomTabActivityHelper;
import com.csatimes.dojma.utilities.DHC;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.Sort;

public class favouritesfragment extends Fragment
        implements HeraldAdapter.OnItemClickedListener,
        HeraldAdapter.OnLikeClickedListener,
        HeraldAdapter.OnShareClickedListener,
        HeraldAdapter.OnScrollUpdateListener{

    private Realm mDatabase;
    private RecyclerView mFavHeraldRecyclerView;
    private TextView mEmptyListTextView;
    private HeraldAdapter mHeraldAdapter;
    private String category;
    private Context context;

    @Override
    public void onStart() {
        super.onStart();
        if (category == null) {
            ItemTouchHelper.Callback callback = new SimpleItemTouchCallback(mHeraldAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(mFavHeraldRecyclerView);
        }
        if (mHeraldAdapter.getItemCount() == 0)
            mEmptyListTextView.setVisibility(View.VISIBLE);
        else {
            mEmptyListTextView.setVisibility(View.GONE);
        }
    }

    public static Fragment newInstance(){
        favouritesfragment favouritesFragment = new favouritesfragment();
        return favouritesFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        category = getActivity().getIntent().getStringExtra("category");
        mDatabase = Realm.getDefaultInstance();
        context = getContext();

        final RealmList<HeraldItem> favouritesList = new RealmList<>();
        RealmQuery<HeraldItem> query = mDatabase.where(HeraldItem.class);
        query = category == null
                ? query.equalTo("fav", true)
                : query.equalTo("category", category);
        favouritesList.addAll(query.sort(EventItem.FIELD_ORIGINAL_DATE, Sort.ASCENDING).findAll());
        mHeraldAdapter = new HeraldAdapter(favouritesList);

        mFavHeraldRecyclerView.setHasFixedSize(true);
        mFavHeraldRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mFavHeraldRecyclerView.addItemDecoration(new androidx.recyclerview.widget.DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        mHeraldAdapter.setHasStableIds(true);
        mHeraldAdapter.setOnLikeClickedListener(this);
        mHeraldAdapter.setOnShareClickedListener(this);
        mHeraldAdapter.setOnItemClickedListener(this);
        mHeraldAdapter.setOnScrollUpdateListener(this);
        mFavHeraldRecyclerView.setAdapter(mHeraldAdapter);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmptyListTextView = (TextView) view.findViewById(R.id.favourites_empty);
        mFavHeraldRecyclerView = (RecyclerView) view.findViewById(R.id.favourites_herald_rv);

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_favourites, container, false);
    }

    @Override
    public void onClick(String postID) {
        HeraldItem foo = mDatabase.where(HeraldItem.class).equalTo("postID", postID).findFirst();

        if (DHC.isOnline(getContext())) {
            try {
                Intent intent = new Intent((Intent.ACTION_SEND));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, foo.getUrl());

                Intent copy_intent = new Intent(getContext(), CopyLinkBroadcastReceiver.class);
                PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(getContext(), 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                String copy_label = "Copy Link";

                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                        .setShowTitle(true)
                        .setToolbarColor(getChromeCustomTabColorFromTheme())
                        .setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp))
                        .addMenuItem(copy_label, copy_pendingIntent)
                        .addDefaultShareMenuItem()
                        .enableUrlBarHiding()
                        .build();

                CustomTabActivityHelper.openCustomTab(getActivity(), customTabsIntent,
                        Uri.parse(foo.getUrl()),
                        new CustomTabActivityHelper.CustomTabFallback() {
                            @Override
                            public void openUri(Activity activity, Uri uri) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                                    intent.putExtra(Intent.EXTRA_REFERRER,
                                            Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + getActivity().getPackageName()));
                                }

                                startActivity(intent);
                            }
                        });
            } catch (Exception e) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(foo.getUrl()));
                startActivity(intent);
            }

        } else {
            Intent intent = new Intent(getContext(), OfflineSimpleViewerActivity.class);
            intent.putExtra("POSTID", postID);
            startActivity(intent);
        }
    }

    @Override
    public void onLiked(final String postID) {
        mDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                HeraldItem foo = realm.where(HeraldItem.class).equalTo("postID", postID).findFirst();
                foo.setFav(true);

            }
        });
    }

    @Override
    public void onDisLiked(final String postID) {
        mDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                HeraldItem foo = realm.where(HeraldItem.class).equalTo("postID", postID).findFirst();
                foo.setFav(false);

            }
        });
    }

    @Override
    public void onShare(String postID) {
        HeraldItem foo = mDatabase.where(HeraldItem.class).equalTo("postID", postID).findFirst();

        Intent intent = new Intent((Intent.ACTION_SEND));
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, foo.getTitle_plain() + " at " + foo.getUrl());
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    @Override
    public void onUpdate(int pos) {
        mFavHeraldRecyclerView.scrollToPosition(pos);
    }

    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
