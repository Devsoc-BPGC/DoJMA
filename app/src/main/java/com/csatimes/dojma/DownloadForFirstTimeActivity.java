package com.csatimes.dojma;

import android.content.Intent;
import android.content.SharedPreferences;
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

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.csatimes.dojma.DHC.directory;


public class DownloadForFirstTimeActivity extends AppCompatActivity {
    private static int downloadAttempt = 0;
    private boolean downloadSuccess = false;
    private Window window;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private RealmConfiguration realmConfiguration;
    private Realm database;
    private CircularFillableLoaders progress;
    private String startingURL = "http://csatimes.co.in/dojma/";

    //public static boolean installed=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_download_for_first_time);


        progress = (CircularFillableLoaders) findViewById(R.id.progressBar);

        progress.setProgress(0);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.navigationBarColor));
        }
        loadList();


    }


    public void loadList() {

        new DownloadDocument().execute(/*
                "http://csatimes.co.in/dojma/",*/
                "http://csatimes.co.in/dojma/page/2",
                "http://csatimes.co.in/dojma/page/3",
                "http://csatimes.co.in/dojma/page/4",
                "http://csatimes.co.in/dojma/page/5",
                "http://csatimes.co.in/dojma/page/6",
                "http://csatimes.co.in/dojma/page/7",
                "http://csatimes.co.in/dojma/page/8",
                "http://csatimes.co.in/dojma/page/9",
                "http://csatimes.co.in/dojma/page/10",
                "http://csatimes.co.in/dojma/page/11"
        );


    }


    //Async method to download html document for parsing
    private class DownloadDocument extends AsyncTask<String, Integer, Void> {
        int i = 0;

        //AsyncTask has <params,progress,result> format
        protected Void doInBackground(String... htmlURL) {
            org.jsoup.nodes.Document[] HTMLDocuments = new Document[htmlURL.length];


            //initialise documents to null
            for (i = 0; i < htmlURL.length; i++)
                HTMLDocuments[i] = null;

            //Setting up realm database for articles
            realmConfiguration = new RealmConfiguration.Builder(DownloadForFirstTimeActivity.this)
                    .name
                            (DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
            database = Realm.getDefaultInstance();


            for (i = 0; i < htmlURL.length; i++) {
                try {
                    Log.e("TAG", "Connecting to URL " + htmlURL[i]);
                    HTMLDocuments[i] = Jsoup.connect(htmlURL[i]).get();
                    if (HTMLDocuments[i] == null) {
                        Log.e("TAG", "HTML download failed");
                        if (i == 0) Snackbar.make(progress, "Failed to download the latest 16 " +
                                "articles. Please update again later on", Snackbar.LENGTH_LONG).show();
                    } else {
                        Log.e("TAG", HTMLDocuments[i].title());

                        //Get all elements that have the "article" tag
                        Elements documentElements = HTMLDocuments[i].getElementsByTag("article");

                        Attributes postIDAttribute, mainAttribute, dateAttrib,
                                updateDateAttrib, imageURLAttribute;
                        String imageURL;

                        int postsCounter = 0;
                        for (Element element : documentElements) {
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

                            database.beginTransaction();
                            HeraldNewsItemFormat temp = database.createObject(HeraldNewsItemFormat.class);
                            temp.setTitle(mainAttribute.get("title"));
                            temp.setPostID(postIDTemp);
                            temp.setAuthor("dojma_admin");
                            temp.setUpdateDate(updateDateAttrib.get("datetime").substring(0, 10));
                            temp.setLink(mainAttribute.get("href"));
                            temp.setOriginalDate(dateAttrib.get("datetime").substring(0, 10));
                            temp.setImageURL(imageURL);
                            temp.setOriginalTime(dateAttrib.get("datetime").substring(10, 16));
                            temp.setUpdateTime(updateDateAttrib.get("datetime").substring(10, 16));
                            temp.setImageSavedLoc(directory + "/" + postIDTemp + ".jpeg");
                            database.commitTransaction();


                            publishProgress(100 * (i + 1) / htmlURL.length *
                                    postsCounter /
                                    DHC.noOfArticlesPerPage);


                        }

                    }

                } catch (IOException ignored) {

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
                startActivity(new Intent(DownloadForFirstTimeActivity.this, HomeActivity.class));
                finish();
            } else {
                DownloadForFirstTimeActivity.this.finish();
                System.exit(0);
            }
        }

    }


}
