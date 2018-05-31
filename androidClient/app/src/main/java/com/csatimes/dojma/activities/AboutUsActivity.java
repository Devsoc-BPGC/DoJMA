package com.csatimes.dojma.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ContributorsAdapter;
import com.csatimes.dojma.adapters.MorebymacAdapter;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "mac";
    public static String ABOUT_US_FACEBOOK_URL = "https://www.facebook.com/MACBITSGoa";
    public static String ABOUT_US_FACEBOOK_PAGE_ID = "MACBITSGoa";
    public static String ABOUT_US_GITHUB_URL = "https://github.com/MobileApplicationsClub";
    public static String ABOUT_US_LINKEDIN_URL = "https://www.linkedin.com/mwlite/company/13598216";
    public static String ABOUT_US_WEBSITE_URL = "https://macbitsgoa.com";
    public static String ABOUT_US_GOOGLEPLAY_URL = "https://play.google.com/store/search?q=Mobile%20App%20Club%20-%20BITS%20Goa&c=apps&hl=en";
    Context context = AboutUsActivity.this;
    Activity activity = AboutUsActivity.this;
    Exception e;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = findViewById(R.id.about_us_toolbar);
        ImageButton facebookImgBtn = findViewById(R.id.content_about_us_fb_imgbtn);
        ImageButton googlePlayImgBtn = findViewById(R.id.content_about_us_google_play_imgbtn);
        ImageButton githubImgBtn = findViewById(R.id.content_about_us_github_imgbtn);
        ImageButton linkedinImgBtn = findViewById(R.id.content_about_us_linkedin_imgbtn);
        ImageButton websiteImgBtn = findViewById(R.id.content_about_us_website_imgbtn);

        androidx.recyclerview.widget.RecyclerView morebymacRecyclerView = findViewById(R.id.content_about_us_morebymac_rv);
        androidx.recyclerview.widget.RecyclerView contributorsRecyclerView = findViewById(R.id.content_about_us_contributors_rv);

        Intent copy_intent = new Intent(context, CopyLinkBroadcastReceiver.class);
        PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(context, 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String copy_label = "Copy Link";

        int colorResource = getChromeCustomTabColorFromTheme();
        final CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(colorResource)
                .setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arrow_back_white_24dp))
                .addMenuItem(copy_label, copy_pendingIntent)
                .addDefaultShareMenuItem()
                .enableUrlBarHiding()
                .build();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about_app);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // clear FLAG_TRANSLUCENT_STATUS flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.mac_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.mac_color));
        }

        morebymacRecyclerView.setHasFixedSize(false);
        morebymacRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        morebymacRecyclerView.setAdapter(new MorebymacAdapter(AboutUsActivity.this));


        contributorsRecyclerView.setHasFixedSize(false);
        contributorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contributorsRecyclerView.setAdapter(new ContributorsAdapter(AboutUsActivity.this));

        findViewById(R.id.content_about_us_app_name_tv).requestFocus();
        facebookImgBtn.setOnClickListener(this);
        googlePlayImgBtn.setOnClickListener(this);
        linkedinImgBtn.setOnClickListener(this);
        githubImgBtn.setOnClickListener(this);
        websiteImgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent copy_intent = new Intent(context, CopyLinkBroadcastReceiver.class);
        PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(context, 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String copy_label = "Copy Link";

        int colorResource = getChromeCustomTabColorFromTheme();
        final CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(colorResource)
                .setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arrow_back_white_24dp))
                .addMenuItem(copy_label, copy_pendingIntent)
                .addDefaultShareMenuItem()
                .enableUrlBarHiding()
                .build();
        switch (id) {
            case R.id.content_about_us_fb_imgbtn:
                try {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(this);
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);
                } catch (ActivityNotFoundException e) {
                    customTabsIntent.launchUrl(context, Uri.parse(ABOUT_US_FACEBOOK_URL));
                }
                break;
            case R.id.content_about_us_linkedin_imgbtn:
                customTabsIntent.launchUrl(context, Uri.parse(ABOUT_US_LINKEDIN_URL));
                break;
            case R.id.content_about_us_website_imgbtn:
                customTabsIntent.launchUrl(context, Uri.parse(ABOUT_US_WEBSITE_URL));
                break;
            case R.id.content_about_us_github_imgbtn:
                customTabsIntent.launchUrl(context, Uri.parse(ABOUT_US_GITHUB_URL));
                break;
            case R.id.content_about_us_google_play_imgbtn:
                customTabsIntent.launchUrl(context, Uri.parse(ABOUT_US_GOOGLEPLAY_URL));
            default:
                break;
        }
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(final Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + ABOUT_US_FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + ABOUT_US_FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return ABOUT_US_FACEBOOK_URL; //normal web url
        }
    }

    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
