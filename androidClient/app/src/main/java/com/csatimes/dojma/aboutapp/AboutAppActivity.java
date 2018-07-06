package com.csatimes.dojma.aboutapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.AndroidApp;
import com.csatimes.dojma.models.Person;
import com.csatimes.dojma.models.SocialLink;
import com.csatimes.dojma.utilities.Browser;
import com.csatimes.dojma.utilities.CustomTextViewRR;
import com.csatimes.dojma.utilities.CustomTextViewRSB;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csatimes.dojma.models.Person.FIELD_NAME;

@SuppressWarnings("NullableProblems")
public class AboutAppActivity extends AppCompatActivity {

    public static final String EXTRA_APP_DESC = "appDescription";
    public static final String EXTRA_APP_DEV = "appDevelopers";
    public static final String EXTRA_APP_NAME = "appName";
    public static final String TAG_PREFIX = "mac.";
    public static final String TAG = TAG_PREFIX + AboutAppActivity.class.getSimpleName();
    public static final String MAC_API_BASE_URL = "https://us-central1-mac-bpgc.cloudfunctions.net";
    private final MacApi api = new Retrofit.Builder()
            .baseUrl(MAC_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MacApi.class);
    private final Realm realm = Realm.getDefaultInstance();
    private final Callback<List<AndroidApp>> appCallBack = new Callback<List<AndroidApp>>() {
        @Override
        public void onResponse(final Call<List<AndroidApp>> call,
                               final Response<List<AndroidApp>> response) {
            final List<AndroidApp> body = response.body();
            if (body == null) return;
            realm.executeTransactionAsync(realm -> {
                for (final AndroidApp app : body) {
                    realm.insertOrUpdate(app);
                }
                Log.e(TAG, body.size() + " apps");
            });
        }

        @Override
        public void onFailure(final Call<List<AndroidApp>> call, final Throwable t) {
            Log.e(TAG, t.getMessage(), t.fillInStackTrace());
        }
    };
    private final Callback<List<SocialLink>> socialCallBack = new Callback<List<SocialLink>>() {
        @Override
        public void onResponse(final Call<List<SocialLink>> call,
                               final Response<List<SocialLink>> response) {
            final List<SocialLink> socialLinks = response.body();
            if (socialLinks == null) {
                return;
            }
            realm.executeTransactionAsync(realm -> {
                for (final SocialLink link : socialLinks) {
                    realm.insertOrUpdate(link);
                }
                Log.e(TAG, socialLinks.size() + " social links");
            });
        }

        @Override
        public void onFailure(final Call<List<SocialLink>> call,
                              final Throwable t) {
            Log.e(TAG, t.getMessage(), t);
        }
    };
    private String appDesc = "";
    private String appName = "";
    private List<Person> appDevelopers = new ArrayList<>(0);
    private RecyclerView cocoRv;
    private final Callback<List<Person>> cocoCallBack = new Callback<List<Person>>() {
        @Override
        public void onResponse(final Call<List<Person>> call,
                               final Response<List<Person>> response) {
            final List<Person> people = response.body();
            if (people == null) {
                return;
            }
            realm.executeTransactionAsync(realm -> {
                final List<Person> newList = new ArrayList<>(0);
                for (final Person p : people) {
                    final Person oldData = realm.where(Person.class)
                            .equalTo(FIELD_NAME, p.name).findFirst();
                    if (oldData != null) {
                        p.isContributor = oldData.isContributor;
                    }
                    realm.insertOrUpdate(p);
                    newList.add(p);
                }

                Log.e(TAG, people.size() + " people");

                runOnUiThread(() -> {
                    findViewById(R.id.tv_coco).setVisibility(newList.size() > 0 ? View.VISIBLE : View.GONE);
                    if (cocoRv.getAdapter() instanceof PersonAdapter) {
                        ((PersonAdapter) cocoRv.getAdapter()).setPersonList(newList);
                    } else {
                        Log.e(TAG, "Adapter of cocoRv should support setPersonList");
                    }
                });
            });
        }

        @Override
        public void onFailure(final Call<List<Person>> call,
                              final Throwable t) {
            Log.e(TAG, t.getMessage(), t.fillInStackTrace());
        }
    };
    private Browser browser;

    /**
     * Method to launch this activity conveniently.
     *
     * @param context        from which this activity is to be launched.
     * @param appDescription of corresponding app.
     * @param appDevelopers  of corresponding app.
     */
    @Keep
    public static void launchAboutAppActivity(@NonNull final Context context,
                                              final String appDescription,
                                              final List<Person> appDevelopers,
                                              final String appName) {
        final Intent launchIntent = new Intent(context, AboutAppActivity.class);
        if (appDescription != null) {
            launchIntent.putExtra(EXTRA_APP_DESC, appDescription);
        }
        if (appDevelopers != null) {
            launchIntent.putExtra(EXTRA_APP_DEV,
                    (new Gson()).toJson(appDevelopers.toArray()));
        }
        if (appName != null) {
            launchIntent.putExtra(EXTRA_APP_NAME, appName);
        }
        context.startActivity(launchIntent);
    }

    @SuppressWarnings("FeatureEnvy")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acquireData(savedInstanceState, getIntent());
        browser = new Browser(this);
        initViews();
        populateViews();
        api.getAndroidApps().enqueue(appCallBack);
        api.getPostHolders().enqueue(cocoCallBack);
        api.getSocialLinks().enqueue(socialCallBack);
    }

    private void initViews() {
        setContentView(R.layout.activity_about_app);
        final Toolbar toolbar = findViewById(R.id.about_app_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        final Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.mac_color));
    }

    private void acquireData(final Bundle bundle, final Intent intent) {
        if (intent != null) {
            if (intent.hasExtra(EXTRA_APP_DESC)) {
                appDesc = intent.getStringExtra(EXTRA_APP_DESC);
            }
            if (intent.hasExtra(EXTRA_APP_DEV)) {
                appDevelopers = Arrays.asList((new Gson())
                        .fromJson(intent.getStringExtra(EXTRA_APP_DEV), Person[].class));
            }
            if (intent.hasExtra(EXTRA_APP_NAME)) {
                appName = intent.getStringExtra(EXTRA_APP_NAME);
            }
        } else if (bundle != null) {
            if (bundle.containsKey(EXTRA_APP_DESC)) {
                appDesc = bundle.getString(EXTRA_APP_DESC);
            }
            if (bundle.containsKey(EXTRA_APP_DEV)) {
                appDevelopers = Arrays.asList((new Gson())
                        .fromJson(bundle.getString(EXTRA_APP_DEV),
                                Person[].class));
            }
            if (bundle.containsKey(EXTRA_APP_NAME)) {
                appName = bundle.getString(EXTRA_APP_NAME);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putString(EXTRA_APP_DESC, appDesc);
        outState.putString(EXTRA_APP_DEV, (new Gson()).toJson(appDevelopers.toArray()));
        outState.putString(EXTRA_APP_NAME, appName);
        super.onSaveInstanceState(outState);
    }

    private void populateViews() {
        populateAppName();
        populateAppDesc();

        ((RecyclerView) findViewById(R.id.rv_social_links)).setAdapter(new SocialAdapter(browser));
        ((RecyclerView) findViewById(R.id.rv_contributors))
                .setAdapter(new PersonAdapter(appDevelopers, browser));
        findViewById(R.id.tv_contrib)
                .setVisibility(appDevelopers.size() > 0 ? View.VISIBLE : View.GONE);
        populateCoco();
        final RecyclerView androidAppsRv = findViewById(R.id.rv_android_apps);
        androidAppsRv.setAdapter(new AndroidAppAdapter(browser));
    }

    private void populateAppName() {
        final CustomTextViewRSB appNameTv = findViewById(R.id.tv_app_name);
        if (TextUtils.isEmpty(appName)) {
            appNameTv.setVisibility(View.GONE);
        } else {
            appNameTv.setVisibility(View.VISIBLE);
            appNameTv.setText(appName);
        }
    }

    private void populateAppDesc() {
        final CustomTextViewRR descTv = findViewById(R.id.tv_app_desc);
        if (TextUtils.isEmpty(appDesc)) {
            descTv.setVisibility(View.GONE);
        } else {
            descTv.setVisibility(View.VISIBLE);
            descTv.setText(appDesc);
        }
    }

    private void populateCoco() {
        cocoRv = findViewById(R.id.rv_coco);
        final List<Person> people = new ArrayList<>(0);
        for (final Person p : realm.where(Person.class).findAll()) {
            if (!TextUtils.isEmpty(p.postName)) {
                people.add(realm.copyFromRealm(p));
            }
        }
        (findViewById(R.id.tv_coco)).setVisibility(people.size() > 0 ? View.VISIBLE : View.GONE);
        cocoRv.setAdapter(new PersonAdapter(people, browser));
    }

}
