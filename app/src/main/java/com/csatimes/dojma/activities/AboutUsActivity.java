package com.csatimes.dojma.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ContributorsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    public static String ABOUT_US_FACEBOOK_URL = "https://www.facebook.com/MACBITSGoa";
    public static String ABOUT_US_FACEBOOK_PAGE_ID = "MACBITSGoa";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.about_us_toolbar);
        ImageButton facebookImgBtn = (ImageButton) findViewById(R.id.content_about_us_fb_imgbtn);
        ImageButton googlePlayImgBtn = (ImageButton) findViewById(R.id.content_about_us_google_play_imgbtn);
        RecyclerView contributorsRecyclerView = (RecyclerView) findViewById(R.id.content_about_us_rv);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About DoJMA Android App");

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

        List<String> contributors = generateContributorsData();
        contributorsRecyclerView.setHasFixedSize(false);
        contributorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contributorsRecyclerView.setAdapter(new ContributorsAdapter(contributors));

        findViewById(R.id.content_about_us_app_name_tv).requestFocus();
        facebookImgBtn.setOnClickListener(this);
        googlePlayImgBtn.setOnClickListener(this);
    }

    private ArrayList<String> generateContributorsData() {
        ArrayList<String> foo = new ArrayList<>();
        foo.add("Vikramaditya Kukreja");
        foo.add("Yash Sharan");
        return foo;
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.content_about_us_fb_imgbtn:
                try {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(this);
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ABOUT_US_FACEBOOK_URL));
                    Toast.makeText(this, "Opening in browser", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                break;
            case R.id.content_about_us_google_play_imgbtn:
                Intent googlePlayIntent = new Intent(Intent.ACTION_VIEW);
                googlePlayIntent.setData(Uri.parse("market://search?q=pub:Mobile App Club - BITS Goa"));
                startActivity(googlePlayIntent);
            default:
                break;
        }
    }
}
