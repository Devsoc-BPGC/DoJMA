package com.csatimes.dojma;

/**
 * Created by yash on 1/7/16.
 */
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static java.lang.Boolean.TRUE;

//import static com.csatimes.dojma.R.color.blue500;

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

    //int color=getResources().getColor(blue500);

    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;
    //CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();




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

        //pre warming the browser

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

        /*//sharing intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND,Uri.parse(urlList.get(POSITION-1)));
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Check out this article: " +currentURL );
        PendingIntent share_menuIntent = PendingIntent.getActivity(this, 0, shareIntent, 0);
        */

        //copy link
        Intent copy_intent=new Intent(this,CopyLinkBroadcastReceiver.class);
        String copy_label="Copy Link";
        PendingIntent copy_pendingIntent=PendingIntent.getBroadcast(this,0,copy_intent,PendingIntent.FLAG_UPDATE_CURRENT);








        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(this,R.color.blue500))
                .setCloseButtonIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_arrow_back))
                .addDefaultShareMenuItem()
                .addMenuItem(copy_label,copy_pendingIntent)
                .setStartAnimations(this,R.anim.slide_in_right,R.anim.slid_out_right)
                .setExitAnimations(this,R.anim.slide_in_right,R.anim.slid_out_right)
                .setSecondaryToolbarColor(ContextCompat.getColor(this,R.color.amber500))
                .enableUrlBarHiding()
                //.setActionButton(BitmapFactory.decodeResource(getResources(),R.id.action_share)," dsd",share_menuIntent,TRUE)
                .build();
        mCustomTabsIntent.launchUrl(this, Uri.parse(currentURL));









    }


}
