package com.csatimes.dojma.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.aboutapp.MacApi;
import com.csatimes.dojma.adapters.MemberAdapter;
import com.csatimes.dojma.models.Member;
import com.csatimes.dojma.models.Person;
import com.csatimes.dojma.utilities.Browser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AboutDojmaActivity extends AppCompatActivity {

    private final Realm realm = Realm.getDefaultInstance();
    private List<Member> dojmaMembers = new ArrayList<>(0);
    public static final String EXTRA_DOJMA_MEM = "dojmaMembers";
    public static final String TAG_PREFIX = "mac.";
    public static final String TAG = TAG_PREFIX + AboutDojmaActivity.class.getSimpleName();
    public static final String MAC_API_BASE_URL = "https://us-central1-mac-bpgc.cloudfunctions.net";
    private Browser browser;
    private final MacApi api = new Retrofit.Builder()
            .baseUrl(MAC_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MacApi.class);
    private final Callback<List<Person>> memberCallBack = new Callback<List<Person>>() {
        @Override
        public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
            final List<Person> members = response.body();
            if (members == null) {
                return;
            }

            realm.executeTransactionAsync(realm -> {
                for (final Person member : members) {
                    realm.insertOrUpdate(member);
                }
                Log.e(TAG, members.size() + "members");
            });
        }

        @Override
        public void onFailure(Call<List<Person>> call, Throwable t) {
            Log.e(TAG, t.getMessage(), t.fillInStackTrace());
        }
    };

    @Keep
    public static void launchAboutDojmaActivity(@NonNull final Context context,
                                              final List<Member> dojmaMembers) {
        final Intent launchIntent = new Intent(context, AboutDojmaActivity.class);
        if (dojmaMembers != null) {
            launchIntent.putExtra(EXTRA_DOJMA_MEM,
                    (new Gson()).toJson(dojmaMembers.toArray()));
        }
        context.startActivity(launchIntent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acquireData(savedInstanceState, getIntent());
        initViews();
        populateViews();
        api.getPostHolders().enqueue(memberCallBack);
        browser = new Browser(this);

        ImageView twitter = findViewById(R.id.twitter_dojma);
        twitter.setOnClickListener(view -> {
            browser.launchUrl("https://twitter.com/dojmabitsgoa?lang=en");
        });
        ImageView facebook = findViewById(R.id.facebook_dojma);
        facebook.setOnClickListener(view -> {
            browser.launchUrl("https://www.facebook.com/DoJMABITSGoa/");
        });
        ImageView youtube = findViewById(R.id.youtube_dojma);
        youtube.setOnClickListener(view -> {
            browser.launchUrl("https://www.youtube.com/channel/UCtO0OtOBQ40poBQpJZ8ZVuA");
        });

    }

    private void acquireData(final Bundle bundle, final Intent intent) {
        if ((intent != null) && (intent.hasExtra(EXTRA_DOJMA_MEM))) {
                dojmaMembers = Arrays.asList((new Gson())
                        .fromJson(intent.getStringExtra(EXTRA_DOJMA_MEM), Member[].class));
        } else if ((bundle != null) && (bundle.containsKey(EXTRA_DOJMA_MEM))) {
                dojmaMembers = Arrays.asList((new Gson())
                        .fromJson(bundle.getString(EXTRA_DOJMA_MEM), Member[].class));
        }
    }

    private void initViews() {
        setContentView(R.layout.activity_about_dojma);
        final Toolbar toolbar = findViewById(R.id.about_dojma_toolbar);

        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        final Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.mac_color));
    }

    private void populateViews() {
        ((RecyclerView) findViewById(R.id.rv_dojma_members))
                .setAdapter(new MemberAdapter(dojmaMembers));

        findViewById(R.id.about_dojma_text)
                .setVisibility(dojmaMembers.size() > 0 ? View.VISIBLE : View.GONE);
    }
}

