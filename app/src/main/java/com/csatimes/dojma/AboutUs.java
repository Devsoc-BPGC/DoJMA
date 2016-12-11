package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

public class AboutUs extends AppCompatActivity {
    public static String ABOUT_US_FACEBOOK_URL = "https://www.facebook.com/MACBITSGoa";
    public static String ABOUT_US_FACEBOOK_PAGE_ID = "MACBITSGoa";
    Window window;
    SimpleDraweeView vik;
    SimpleDraweeView yash;
    Button fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_us_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About DoJMA Android App");
        getSupportActionBar().setSubtitle("Mobile Applications Club");

        fb = (Button) findViewById(R.id.about_us_fb_link);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(AboutUs.this);
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);

                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook" +
                            ".com/MACBITSGoa/"));
                    startActivity(intent);
                }
            }
        });
        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= 21) {  // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.mac2_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.mac2_color));
        }

        vik = (SimpleDraweeView) findViewById(R.id.about_us_vik);
        yash = (SimpleDraweeView) findViewById(R.id.about_us_yash);

        vik.setImageURI(Uri.parse("res://" + getPackageName()
                + "/" + R.drawable.vik));
        yash.setImageURI(Uri.parse("res://" + getPackageName()
                + "/" + R.drawable.yash));

    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
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
}
