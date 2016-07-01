package com.csatimes.dojma;

/**
 * Created by yash on 1/7/16.
 */
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ChromeCustomTab extends AppCompatActivity{
    private static String currentURL;
    private static List<String> urlList;
    private static List<String> titleList;
    private static int POSITION;
    private WebView webView;
    private ProgressBar downloadProgressBar;
    private String postID;
    private Realm database;
    private RealmConfiguration realmConfiguration;
    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_chrome_custom_tab);


        POSITION = 0;
        urlList = new ArrayList<>();
        titleList = new ArrayList<>();
        Intent intent = getIntent();
        postID = intent.getStringExtra("POSTID");
        currentURL = intent.getStringExtra("URL");
        urlList.add(currentURL);
        titleList.add(intent.getStringExtra("TITLE"));
        POSITION++;

        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient= customTabsClient;
                mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCustomTabsClient= null;
            }
        };
        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .build();
        mCustomTabsIntent.launchUrl(this, Uri.parse(currentURL));


    }

}
