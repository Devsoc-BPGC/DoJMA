package com.csatimes.dojma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.csatimes.dojma.DHC.directory;


public class DownloadForFirstTimeActivity extends AppCompatActivity {
    private static int downloadAttempt = 0;
    private static int num = 1;
    private static int num2 = 1;
    private boolean downloadSuccess = false;
    private Window window;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private RealmConfiguration realmConfiguration;
    private Realm database;
    private CircularFillableLoaders progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_download_for_first_time);
        num = 0;
        num2 = 0;


        progress = (CircularFillableLoaders) findViewById(R.id.progressBar);
        progress.setProgress(0);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.navigationBarColor));
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                loadList();
            }
        }).start();


    }


    public void loadList() {
        try {

            new DownloadDocument().execute(
                    new URL("http://csatimes.co.in/dojma/"),
                    new URL("http://csatimes.co.in/dojma/page/2"),
                    new URL("http://csatimes.co.in/dojma/page/3"),
                    new URL("http://csatimes.co.in/dojma/page/4"),
                    new URL("http://csatimes.co.in/dojma/page/5"),
                    new URL("http://csatimes.co.in/dojma/page/6"),
                    new URL("http://csatimes.co.in/dojma/page/7"),
                    new URL("http://csatimes.co.in/dojma/page/8"),
                    new URL("http://csatimes.co.in/dojma/page/9"),
                    new URL("http://csatimes.co.in/dojma/page/10"),
                    new URL("http://csatimes.co.in/dojma/page/11"),
                    new URL("http://csatimes.co.in/dojma/page/12")
            );


        } catch (MalformedURLException e) {
            new SimpleAlertDialog().showDialog(this, "MalformedURLException", e.toString(), "OK", "",
                    true, false);
        }
    }


    //Async method to download html document for parsing
    private class DownloadDocument extends AsyncTask<URL, Integer, Void> {
        int i = 0;
        Cursor cursor;

        //AsyncTask has <params,progress,result> format
        protected Void doInBackground(URL... htmlURL) {
            org.jsoup.nodes.Document[] HTMLDocuments = new Document[htmlURL.length];


            //initialise documents to null
            for (i = 0; i < htmlURL.length; i++)
                HTMLDocuments[i] = null;

            //Setting up realm database
            realmConfiguration = new RealmConfiguration.Builder(DownloadForFirstTimeActivity.this)
                    .name
                            (DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
            database = Realm.getDefaultInstance();


            for (i = 0; i < htmlURL.length; i++) {
                try {

                    Log.e("TAG", "Connecting to URL " + htmlURL[i]);
                    HTMLDocuments[i] = Jsoup.connect(htmlURL[i].toString()).get();
                    if (HTMLDocuments[i] == null)
                        Log.e("TAG", "HTML download failed");
                    else {
                        Log.e("TAG", HTMLDocuments[i].title());

                        int noOfArticles = 0;
                        //notify onProgressUpdate using predefined method

                        String imageURL;
                        //Get all elements that have the "article" tag
                        Elements documentElements = HTMLDocuments[i].getElementsByTag("article");

                        Attributes postIDAttribute, mainAttribute, dateAttrib,
                                updateDateAttrib, imageURLAttribute;

                        int postsCounter = 0;
                        for (Element element : documentElements) {
                            noOfArticles++;
                            postsCounter++;
                            postIDAttribute = element.attributes();
                            mainAttribute = element.child(0).child(0).attributes();
                            dateAttrib = element.child(1).child(0).child(0).child(0)
                                    .child(0).attributes
                                            ();
                            final String postIDTemp = postIDAttribute.get("id");


                            try {
                                updateDateAttrib = element.child(1).child(0).child(0).child(0)
                                        .child(1).attributes
                                                ();

                            } catch (Exception e) {
                                updateDateAttrib = dateAttrib;

                            }

                            if (!element.getElementsByAttribute("src").isEmpty()) {
                                imageURLAttribute = element.child(0).child(0).child(0)
                                        .attributes();
                                imageURL = imageURLAttribute.get
                                        ("src");
                            } else {
                                imageURL = "-1";
                            }


                            // also //time check
                            // is also important
                            long postExists = 0;
                            postExists = database.where
                                    (HeraldNewsItemFormat.class).equalTo("postID", postIDTemp)
                                    .count();
                            if (postExists == 0) {

                                database.beginTransaction();
                                HeraldNewsItemFormat temp = database.createObject(HeraldNewsItemFormat.class);
                                temp.setTitle(mainAttribute.get("title"));
                                temp.setPostID(postIDTemp);
                                temp.setAuthor("dojma_admin");
                                temp.setUpdateDate(updateDateAttrib.get("datetime").substring(0, 10));
                                temp.setLink(mainAttribute.get("href"));
                                temp.setOriginalDate(dateAttrib.get("datetime").substring(0, 10));
                                temp.setImageURL(imageURL);
                                temp.setOriginalTime("ogtime");
                                temp.setUpdateTime("uptime");
                                temp.setImageSavedLoc(directory + "/" + postIDTemp + ".jpeg");
                                database.commitTransaction();
                            } else {
                                Log.e("TAG", "Record existed!");
                                //update database if required
                                //this condition will probably happen only on app update
                                //so we can defer it for later
                            }
                            publishProgress(100 * (i + 1) / htmlURL.length *
                                    postsCounter /
                                    noOfArticles);


                        }

                    }

                } catch (IOException e) {
                    Log.e("TAG", e.toString());
                    if (i == 0) {
                        Snackbar.make(progress, "Failed to download the latest 16 articles.Please " +
                                "update again later", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
            database.close();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            realmConfiguration = new RealmConfiguration.Builder(DownloadForFirstTimeActivity.this)
                    .name
                            (DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
            database = Realm.getDefaultInstance();


            long noOfPosts = database.where(HeraldNewsItemFormat.class).count();
            database.close();
            if (noOfPosts >= 100) {
                progress.setProgress(100);
                preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
                editor = preferences.edit();
                editor.putBoolean(getString(R.string.SP_first_install), false);
                editor.apply();

                realmConfiguration = new RealmConfiguration.Builder(DownloadForFirstTimeActivity.this)
                        .name
                                (DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
                Realm.setDefaultConfiguration(realmConfiguration);
                database = Realm.getDefaultInstance();

                final RealmResults<HeraldNewsItemFormat> results = database.where(HeraldNewsItemFormat
                        .class).findAll();

//                for (i = 0; i < results.size(); i++) {
//
//                }
                database.close();
                startActivity(new Intent(DownloadForFirstTimeActivity.this, HomeActivity.class));
                finish();
            } else {
                DownloadForFirstTimeActivity.this.finish();
                System.exit(0);
            }
        }

    }


}
