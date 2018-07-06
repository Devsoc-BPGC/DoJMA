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

import com.csatimes.dojma.R;
import com.csatimes.dojma.aboutapp.MacApi;
import com.csatimes.dojma.adapters.MemberAdapter;
import com.csatimes.dojma.models.Member;
import com.csatimes.dojma.models.Person;
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
    }

    private void acquireData(final Bundle bundle, final Intent intent) {
        if (intent != null) {
            if (intent.hasExtra(EXTRA_DOJMA_MEM)) {
                dojmaMembers = Arrays.asList((new Gson())
                        .fromJson(intent.getStringExtra(EXTRA_DOJMA_MEM), Member[].class));
            }
        } else if (bundle != null) {
            if (bundle.containsKey(EXTRA_DOJMA_MEM)) {
                dojmaMembers = Arrays.asList((new Gson())
                        .fromJson(bundle.getString(EXTRA_DOJMA_MEM),
                                Member[].class));
            }
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

        /*final String html = "<b>The Editorial Team</b><br>" + "<br>" + " <br>" + "<br>" + "<b>Chief Coordinator</b>: Sumangala Patki<br>" +
                "<br>" + "<b>Chief Editor</b>: M Kaushik Reddy<br>" + "<br>" +
                "<b>Head, Waves</b>: Nitish Kulshrestha<br>" + "<br>" +
                "<b>Head, Quark</b>: Rishi Raj Grandhe<br>" + "<br>" +
                "<b>Head, Spree</b>: Mahima Samant<br>" + "<br>" + "<br>" +
                "<b>Journalists</b><br>" + "<br>" + " <br>" + "<br>" + "Adheep Das<br>" + "<br>" + "Akshay Ginodia<br>" +
                "<br>" + "Ayush Anand<br>" + "<br>" + "Chand Sethi<br>" + "<br>" + "Harsh Lodha<br>" + "<br>" +
                "Nidhi Kadkol<br>" + "<br>" + "Nissim Gore-Datar<br>" + "<br>" + "Ranajoy Roy<br>" + "<br>" +
                "Rohan Gajendragadkar<br>" + "<br>" + "Shashank Subramaniam<br>" + "<br>" + " <br>" + "<br>" +
                "Aman Arora<br>" + "<br>" + "Anurag Kumar<br>" + "<br>" + "Esha Swaroop<br>" + "<br>" +
                "Lucky Kaul<br>" + "<br>" + "Roshan Dattatri<br>" + "<br>" + "Roshan Nair<br>" +
                "<br>" + "Saloni Dash<br>" + "<br>" + " <br>" + "<br>" + "Aditya Lahiri<br>" + "<br>" +
                "Akshat Mittal<br>" + "<br>" + "Aman Singh Yadav<br>" + "<br>" + "Ankit<br>" + "<br>" +
                "Apoorva Dhamne<br>" + "<br>" + "Aryan Agarwal<br>" + "<br>" + "Darshan Hegde<br>" + "<br>" +
                "Mridul Summan<br>" + "<br>" + "Parth Ganatra<br>" + "<br>" + "Parul Verma<br>" + "<br>" +
                "Poojan Thakkar<br>" + "<br>" +
                "Rachit Rastogi<br>" + "<br>" + "Saumya Maheshwari<br>" + "<br>" + "Sharat Patil<br>" + "<br>" +
                "Siddhinita Wandekar<br>" + "<br>" + "Soumalya Barari<br>" + "<br>" + "Vaibhav Garg";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(html));
        }*/

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

