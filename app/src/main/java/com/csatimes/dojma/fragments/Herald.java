package com.csatimes.dojma.fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.OfflineSimpleViewerActivity;
import com.csatimes.dojma.adapters.HeraldAdapter;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.csatimes.dojma.utilities.CustomTabActivityHelper;
import com.csatimes.dojma.utilities.DHC;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_ACTION_NO_SUCCESS;


public class Herald extends Fragment implements
        HeraldAdapter.OnItemClickedListener,
        HeraldAdapter.OnShareClickedListener,
        HeraldAdapter.OnLikeClickedListener {

    final String TAG = "fragments.Herald";
    private HeraldAdapter mAdapter;
    private Realm mDatabase;
    private RealmList<HeraldItem> mHeraldItems;
    private RecyclerView mHeraldRecyclerView;
    private TextView mErrorTextView;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().compareTo(UPDATE_SERVICE_ACTION_NO_SUCCESS) == 0) {
                Toast.makeText(context, "No new updates", Toast.LENGTH_SHORT).show();
            } else if (intent.getAction().compareTo(UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS) == 0) {
                mHeraldItems.clear();
                mHeraldItems.addAll(mDatabase.where(HeraldItem.class).findAllSorted("originalDate", Sort.DESCENDING));
                mAdapter.notifyDataSetChanged();
                if (mHeraldItems.size() == 0) mErrorTextView.setVisibility(VISIBLE);
                else mErrorTextView.setVisibility(GONE);
                DHC.makeCustomSnackbar(mHeraldRecyclerView, "Articles were updated", ContextCompat.getColor(context, R.color.colorPrimary), Color.WHITE).show();
            }
        }
    };

    public Herald() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_herald, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mErrorTextView = (TextView) view.findViewById(R.id.fragment_herald_error_text);
        mHeraldRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_herald_rv);

        mHeraldRecyclerView.setHasFixedSize(true);
        mHeraldRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), span()));
        mHeraldRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private int span() {

        //Setup columns according to device screen
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up images grid
        int val = 300;
        float t = dpWidth / val;
        float r = dpWidth % val;
        if (r < val * 0.50)
            return (int) Math.floor(t);
        else
            return (int) Math.ceil(t);
    }


    @Override
    public void onStart() {
        super.onStart();

        //Ready Realm mDatabase
        mDatabase = Realm.getDefaultInstance();
        mHeraldItems = new RealmList<>();
        mHeraldItems.addAll(mDatabase.where(HeraldItem.class)
                .findAllSorted("originalDate", Sort.DESCENDING));
        if (mHeraldItems.size() == 0) mErrorTextView.setVisibility(VISIBLE);
        mAdapter = new HeraldAdapter(mHeraldItems);

        mAdapter.setOnLikeClickedListener(this);
        mAdapter.setOnShareClickedListener(this);
        mAdapter.setOnItemClickedListener(this);

        mHeraldRecyclerView.setAdapter(mAdapter);

        prepareScrollBar();

        //Register the broadcast listener
        IntentFilter intentFilter = generateIntentFilter();
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);

    }

    /**
     * Initialize the scrollbar<br>
     * <b>Always</b> initialise recyclerView first
     */
    private void prepareScrollBar() {
        new DragScrollBar(getContext(), mHeraldRecyclerView, true)
                .setDraggableFromAnywhere(true)
                .setHandleColour(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setBarColour(ContextCompat.getColor(getContext(), R.color.grey500))
                .setHandleOffColour(ContextCompat.getColor(getContext(), R.color.grey800));
    }

    @NonNull
    private IntentFilter generateIntentFilter() {
        IntentFilter foo = new IntentFilter();
        foo.addAction(UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS);
        foo.addAction(UPDATE_SERVICE_ACTION_NO_SUCCESS);
        return foo;
    }

    @Override
    public void onStop() {
        super.onStop();
        //Unregister the receiver onStop
        getActivity().unregisterReceiver(mBroadcastReceiver);
        //Close realm database
        mDatabase.close();
    }

    //When an article is liked mDatabase has to be updated using the postID
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

    //When an article is disliked mDatabase has to be updated using the postID
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

    //When an article is shared sharing intent is started
    @Override
    public void onShare(String postID) {
        HeraldItem foo = mDatabase.where(HeraldItem.class).equalTo("postID", postID).findFirst();

        Intent intent = new Intent((Intent.ACTION_SEND));
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, foo.getTitle_plain() + " at " + foo.getUrl());
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    //Article was clicked, accordingly open the webpage
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

                int colorResource = getChromeCustomTabColorFromTheme();

                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                        .setShowTitle(true)
                        .setToolbarColor(colorResource)
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
                                            Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + getContext().getPackageName()));
                                }

                                startActivity(intent);
                            }
                        });
            } catch (Exception e) {
                DHC.e(TAG, "Error while opening link " + e.getMessage());
                e.printStackTrace();
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

    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
