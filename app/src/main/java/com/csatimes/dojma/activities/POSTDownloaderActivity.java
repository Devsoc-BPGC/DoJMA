package com.csatimes.dojma.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.models.PosterItem;
import com.csatimes.dojma.services.UpdateCheckerService;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;

import io.realm.Realm;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_CONTACTS;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_EVENTS;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_GAZETTES;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_LINKS;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_MESS;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_MISC_CARD;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_NAVBAR;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_NAVBAR_IMAGE_URL;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_NAVBAR_TITLE;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_POSTERS;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_TAXI;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_ACTION_NO_SUCCESS;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_HERALD_DEFAULT_PAGES;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_INTENT_ENABLE_NOTIFICATION;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_INTENT_PAGES;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_MISC_CARD_MESSAGE;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_NAVBAR_IMAGE_URL;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_NAVBAR_TITLE;

public class POSTDownloaderActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "activities.PostDownloaderActivity";

    private TextSwitcher mStatusSwitcher;
    private DecoView mCircleLoadingView;
    private AppCompatButton mStartButton;
    private int mMainLoaderIndex = 0;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Realm mDatabase;

    private float QUOTA_UPDATE_SERVICE = 30;
    private float QUOTA_GAZETTES = 10;
    private float QUOTA_EVENTS = 10;
    private float QUOTA_CONTACTS = 7;
    private float QUOTA_TAXI = 8;
    private float QUOTA_POSTERS = 5;
    private float QUOTA_MESS = 10;
    private float QUOTA_LINKS = 10;
    private float QUOTA_NAVBAR = 5;
    private float QUOTA_MISC_CARD = 5;

    private float QUOTA_TOTAL = 0;

    private float CIRCLE_MAX = 100;
    private float CIRCLE_MIN = 0;
    private int CIRCLE_SPEED_DEFAULT = 300;

    private boolean firebaseDownload;
    private boolean dojmaDownload;


    private SharedPreferences.Editor mEditor;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent.getAction().equals(UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS)) {
                mEditor.putBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), false);
                mEditor.apply();
                QUOTA_TOTAL += QUOTA_UPDATE_SERVICE;
                mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                dojmaDownload = true;
            } else if (intent.getAction().equals(UPDATE_SERVICE_ACTION_NO_SUCCESS)) {
                int errorLine = mCircleLoadingView.addSeries(new SeriesItem.Builder(ContextCompat.getColor(context, R.color.red500))
                        .setRange(CIRCLE_MIN, CIRCLE_MAX, CIRCLE_MIN)
                        .setSpinDuration(CIRCLE_SPEED_DEFAULT)
                        .setSpinClockwise(false)
                        .setCapRounded(true)
                        .setSeriesLabel(SeriesLabel.createLabel("Herald"))
                        .build());
                mCircleLoadingView.moveTo(errorLine, QUOTA_UPDATE_SERVICE);
                dojmaDownload = false;
            }
            updateStatus();
        }
    };

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_postdownloader);

        mStatusSwitcher = (TextSwitcher) findViewById(R.id.content_post_downloader_status_switcher);
        mCircleLoadingView = (DecoView) findViewById(R.id.content_post_downloader_circle_view);
        mStartButton = (AppCompatButton) findViewById(R.id.content_post_downloader_start_btn);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }


        dojmaDownload = false;
        firebaseDownload = false;

        //Add the background series of grey
        mCircleLoadingView.addSeries(new SeriesItem.Builder(ContextCompat.getColor(this, R.color.grey400))
                .setRange(CIRCLE_MIN, CIRCLE_MAX, CIRCLE_MAX)
                .setCapRounded(false)
                .setInitialVisibility(true)
                .build());

        setupSwitcher(mStatusSwitcher);

        if (!DHC.isOnline(this)) {
            //Add a red error series if not connected
            int index = mCircleLoadingView.addSeries(new SeriesItem.Builder(ContextCompat.getColor(this, R.color.red500))
                    .setRange(CIRCLE_MIN, CIRCLE_MAX, CIRCLE_MIN)
                    .setCapRounded(true)
                    .setSpinDuration(2000)
                    .setInitialVisibility(true)
                    .build());
            mCircleLoadingView.moveTo(index, CIRCLE_MAX);
            mStatusSwitcher.setVisibility(GONE);

        } else {

            mStatusSwitcher.setText("Setting up ... ");

            SeriesItem mainSeries = new SeriesItem.Builder(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                    .setRange(CIRCLE_MIN, CIRCLE_MAX, CIRCLE_MIN)
                    .setSpinDuration(CIRCLE_SPEED_DEFAULT)
                    .setCapRounded(true)
                    .setInitialVisibility(false)
                    .build();

            mMainLoaderIndex = mCircleLoadingView.addSeries(mainSeries);

            databaseReference.addListenerForSingleValueEvent(returnSingleValueEventListener());
        }
        mStartButton.setOnClickListener(this);
    }

    private void setupSwitcher(TextSwitcher mStatusSwitcher) {

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(this,
                R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this,
                R.anim.fade_out);

        // set the animation type of textSwitcher
        mStatusSwitcher.setInAnimation(in);
        mStatusSwitcher.setOutAnimation(out);

        //Setup the TextView inside the switcher
        mStatusSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                // Create run time textView with some attributes like gravity,
                // color, etc.
                TextView statusText = new TextView(POSTDownloaderActivity.this);
                statusText.setPadding(0, 25, 0, 0);
                statusText.setGravity(Gravity.CENTER);
                statusText.setTextSize(16);
                statusText.setTextColor(Color.WHITE);
                return statusText;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase = Realm.getDefaultInstance();
        SharedPreferences mSharedPreferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (DHC.isOnline(this)) {
            if (!UpdateCheckerService.isInstanceCreated()) {
                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(HeraldItem.class);
                    }
                });
                Intent intent = new Intent(this, UpdateCheckerService.class);
                intent.putExtra(UPDATE_SERVICE_INTENT_PAGES, 1);
                intent.putExtra(UPDATE_SERVICE_INTENT_ENABLE_NOTIFICATION, false);
                startService(intent);
            }

            IntentFilter intf = new IntentFilter();
            intf.addAction(UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS);
            intf.addAction(UPDATE_SERVICE_ACTION_NO_SUCCESS);
            registerReceiver(broadcastReceiver, intf);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDatabase.close();
        mEditor.apply();
    }

    private ValueEventListener returnSingleValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Download Gazettes
                {
                    final DataSnapshot gazettesShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_GAZETTES);
                    mDatabase.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(GazetteItem.class);
                        }
                    });
                    for (final DataSnapshot gazetteShotChild : gazettesShot.getChildren()) {
                        try {
                            final GazetteItem foo = gazetteShotChild.getValue(GazetteItem.class);
                            mDatabase.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    GazetteItem bar = realm.createObject(GazetteItem.class, gazetteShotChild.getKey());
                                    bar.setTitle(foo.getTitle());
                                    bar.setDate(foo.getDate());
                                    bar.setUrl(foo.getUrl());
                                    bar.setImageUrl(foo.getImageUrl());
                                    bar.setTime(bar.getTime());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {

                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            DHC.log(TAG, "Error occurred in parsing or storing gazettes data");
                        }
                    }
                    QUOTA_TOTAL += QUOTA_GAZETTES;
                    mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                    mStatusSwitcher.setText("Added gazettes");

                }
                //Download Events
                {
                    final DataSnapshot eventsShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_EVENTS);
                    mDatabase.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(EventItem.class);
                        }
                    });
                    for (final DataSnapshot eventsShotChild : eventsShot.getChildren()) {
                        try {
                            final EventItem foo = eventsShotChild.getValue(EventItem.class);
                            mDatabase.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    EventItem bar = realm.createObject(EventItem.class, eventsShotChild.getKey());
                                    bar.setTitle(foo.getTitle());
                                    bar.setLocation(foo.getLocation());
                                    bar.setDesc(foo.getDesc());
                                    bar.setDateTime(foo.getStartDate() + foo.getStartTime());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {

                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            DHC.log(TAG, "Error occurred in parsing or storing events data");
                        }
                    }
                    QUOTA_TOTAL += QUOTA_EVENTS;
                    mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                    mStatusSwitcher.setText("Added events");

                }

                //Download Contacts
                {
                    final DataSnapshot contactsShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_CONTACTS);
                    QUOTA_TOTAL += QUOTA_CONTACTS;
                    mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                    mStatusSwitcher.setText("Added contacts");
                }
                //Download Links
                {
                    final DataSnapshot linksShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_LINKS);
                    mDatabase.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(LinkItem.class);
                        }
                    });
                    for (final DataSnapshot linksShotChild : linksShot.getChildren()) {
                        try {
                            final LinkItem foo = linksShotChild.getValue(LinkItem.class);
                            mDatabase.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    LinkItem bar = realm.createObject(LinkItem.class);
                                    bar.setTitle(foo.getTitle());
                                    bar.setUrl(foo.getUrl());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {

                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            DHC.log(TAG, "Error occurred in parsing or storing links data");
                        }
                    }
                    QUOTA_TOTAL += QUOTA_LINKS;
                    mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                    mStatusSwitcher.setText("Added links");
                }

                //Download Mess' data
                {
                    final DataSnapshot messShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_MESS);
                    mDatabase.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(MessItem.class);
                        }
                    });
                    for (final DataSnapshot messShotChild : messShot.getChildren()) {
                        try {
                            final MessItem foo = messShotChild.getValue(MessItem.class);
                            mDatabase.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    MessItem bar = realm.createObject(MessItem.class);
                                    bar.setTitle(foo.getTitle());
                                    bar.setImageUrl(foo.getImageUrl());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {

                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            DHC.log(TAG, "Error occurred in parsing or storing mess data");
                        }
                    }
                    QUOTA_TOTAL += QUOTA_MESS;
                    mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                    mStatusSwitcher.setText("Added menu links");

                }

                //Update Navbar title and image url data into the sharedpreferences
                {
                    final DataSnapshot navbarShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_NAVBAR);
                    mEditor.putString(USER_PREFERENCES_NAVBAR_TITLE, navbarShot.child(FIREBASE_DATABASE_REFERENCE_NAVBAR_TITLE).getValue(String.class));
                    mEditor.putString(USER_PREFERENCES_NAVBAR_IMAGE_URL, navbarShot.child(FIREBASE_DATABASE_REFERENCE_NAVBAR_IMAGE_URL).getValue(String.class));
                    mStatusSwitcher.setText("Added nav bar title and image");
                    mEditor.apply();
                    QUOTA_TOTAL += QUOTA_NAVBAR;
                    mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                }

                final DataSnapshot postersShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_POSTERS);
                QUOTA_TOTAL += QUOTA_POSTERS;
                mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                mStatusSwitcher.setText("Added posters");

                //Get latest MISC board message and save it to the sharedprefernces
                {
                    final DataSnapshot miscCardShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_MISC_CARD);

                    mEditor.putString(USER_PREFERENCES_MISC_CARD_MESSAGE, miscCardShot.getValue(String.class));
                    QUOTA_TOTAL += QUOTA_MISC_CARD;
                    mStatusSwitcher.setText("Added MISC info in utilities");
                    mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);

                }

                final DataSnapshot taxisShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_TAXI);
                QUOTA_TOTAL += QUOTA_TAXI;
                mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                mStatusSwitcher.setText("About to finish");
                firebaseDownload = true;
                updateStatus();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.log(TAG, "Database error " + databaseError.getMessage());
                mStatusSwitcher.setText("CLOUD SERVICE ERROR. TRY AGAIN LATER OR REPORT TO ADMIN");
                QUOTA_TOTAL += 100 - QUOTA_UPDATE_SERVICE;
                mCircleLoadingView.moveTo(mMainLoaderIndex, QUOTA_TOTAL);
                firebaseDownload = false;
                updateStatus();
            }
        };
    }

    private void updateStatus() {
        mStartButton.setVisibility(VISIBLE);
        if (!firebaseDownload && !dojmaDownload) {
            mStatusSwitcher.setText("No data downloaded. Please check if you are connected or Skip to download later");
            mStartButton.setVisibility(GONE);
        } else if (firebaseDownload && !dojmaDownload) {
            mStatusSwitcher.setText("Downloading few articles");
            mStartButton.setText("Skip");
        } else if (!firebaseDownload && dojmaDownload) {
            mStatusSwitcher.setText("Getting latest campus info ... ");
            mStartButton.setText("Skip");
        } else {

            StringBuilder sb = new StringBuilder();
            int size = mDatabase.where(HeraldItem.class).findAll().size();
            if (size > 0) sb.append(size).append(" articles\n");
            else sb.append("No articles added");
            size = mDatabase.where(EventItem.class).findAll().size();
            if (size > 0) sb.append(size).append(" event(s)\n");
            size = mDatabase.where(GazetteItem.class).findAll().size();
            if (size > 0) sb.append(size).append(" gazettes\n");
            size = mDatabase.where(LinkItem.class).findAll().size();
            if (size > 0) sb.append(size).append(" links\n");
            size = mDatabase.where(ContactItem.class).findAll().size();
            if (size > 0) sb.append(size).append(" contacts\n");
            size = mDatabase.where(PosterItem.class).findAll().size();
            if (size > 0) sb.append(size).append(" poster(s)\n");
            size = mDatabase.where(MessItem.class).findAll().size();
            if (size > 0) sb.append(size).append(" menu cards\n");

            mStatusSwitcher.setText(sb.toString());
            mStartButton.setText("Start");

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.content_post_downloader_start_btn:
                Intent intent = new Intent(this, MainActivity.class);
                mEditor.putBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), false);
                mEditor.apply();
                Intent updateIntent = new Intent(this, UpdateCheckerService.class);
                updateIntent.putExtra(UPDATE_SERVICE_INTENT_PAGES, UPDATE_SERVICE_HERALD_DEFAULT_PAGES);
                startService(updateIntent);
                startActivity(intent);
                finish();
            default:
                break;
        }
    }
}

