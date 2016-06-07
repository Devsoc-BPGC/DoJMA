package com.csatimes.dojma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadForFirstTimeActivity extends AppCompatActivity {
    private static int downloadAttempt = 0;
    private static int num = 1;
    private static int num2 = 1;
    private boolean downloadSuccess = false;
    private ProgressBar progressBar;
    private Window window;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button retryDownloadButton;
    private Thread newThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_download_for_first_time);
        num = 0;
        num2 = 0;
        retryDownloadButton = (Button) findViewById(R.id.retry_download);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                loadList();
            }
        }).start();


        //after downloading is successfully complete,
        //start Main activity

        //if downloaded then run this
        //after thread completion


    }


    public void loadList() {
        long timeStart = System.currentTimeMillis();


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
                    new URL("http://csatimes.co.in/dojma/page/11")
            );


        } catch (MalformedURLException e) {
            //e.printStackTrace();
            new SimpleAlertDialog().showDialog(this, "MalformedURLException", e.toString(), "OK", "",
                    true, false);
        }
        long endTime = System.currentTimeMillis();
        Log.e("TAG", "time taken to load download list " + (endTime - timeStart));

    }


    //Async method to download html document for parsing
    private class DownloadDocument extends AsyncTask<URL, Float, Void> {
        int poo = 0;

        @Override
        protected void onProgressUpdate(Float... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DatabaseOperations dop = new DatabaseOperations(getBaseContext());
            SQLiteDatabase sql = dop.getReadableDatabase();
            if (dop.getInformation().getCount() >= 30) {
                preferences = getSharedPreferences(DojmaHelperMethod.USER_PREFERENCES, MODE_PRIVATE);
                editor = preferences.edit();
                editor.putBoolean("FIRST_TIME_INSTALL", false);
                editor.apply();
                startActivity(new Intent(DownloadForFirstTimeActivity.this, HomeActivity.class));
            } else {
                Log.e("TAG", "size is " + dop.getInformation().getCount());
                Toast.makeText(DownloadForFirstTimeActivity.this, "Download Failed! Try again " +
                                "later",
                        Toast.LENGTH_LONG).show();

                DownloadForFirstTimeActivity.this.finish();
            }
            progressBar.setProgress(100);
        }

        //AsyncTask has <params,progress,result> format
        protected Void doInBackground(URL... url) {
            org.jsoup.nodes.Document[] foo = new Document[url.length];
            //initialise documents to null
            for (poo = 0; poo < url.length; poo++)
                foo[poo] = null;

            for (int poo = 0; poo < url.length; poo++) {
                try {
                    {
                        Log.e("TAG", "Connecting to URL " + url[poo]);
                        foo[poo] = Jsoup.connect(url[poo].toString()).get();
                        if (foo[poo] == null) Log.e("TAG", "Document null when downloaded");
                        else {
                            Log.e("TAG", foo[poo].title());
                            int noOfArticles = 0;
                            //notify of download success
                            String imageurl;

                            //Get all elements that have the "article" tag
                            Elements documentElements = foo[poo].getElementsByTag("article");

                            Attributes postIDAttribute, mainAttribute, dateAttrib, imageURLAttribute;
                            for (Element element : documentElements) {
                                postIDAttribute = element.attributes();
                                mainAttribute = element.child(0).child(0).attributes();
                                dateAttrib = element.child(1).child(0).child(0).child(0)
                                        .child(0).attributes
                                                ();
                                //or 1 at last pos // check for updated time also //time check
                                // is also important


                                //check if article has an associated img to it
                                if (!element.getElementsByAttribute("src").isEmpty()) {
                                    imageURLAttribute = element.child(0).child(0).child(0)
                                            .attributes();
                                    imageurl = imageURLAttribute.get
                                            ("src");
                                } else {
                                    imageurl = "-1";
                                }

                                ///adding all these parsed values to database using insertRow
                                // method of DatabaseOperations object

                                DatabaseOperations dop = new DatabaseOperations
                                        (DownloadForFirstTimeActivity.this);
                                {
                                    //String postid, String title,String postURL, String date,String updateDate, String
                                    //author, String imageurl

                                    //Cursor object foo is used to check whether that link already
                                    // exists or not
                                    //if it doesn't then add otherwise ignore
                                    Cursor cursor = dop.getReadableDatabase().rawQuery("SELECT * " +
                                            "FROM" +
                                            " " + TableData.TableInfo.TABLE_NAME + " WHERE " +
                                            TableData.TableInfo.tablePostID + " = " +
                                            "'" + postIDAttribute.get("id") + "';", null);


                                    if (cursor.getCount() <= 0) {
                                        cursor.close();
                                        dop.insertRow(postIDAttribute.get("id"), mainAttribute.get
                                                        ("title"), mainAttribute.get("href"), dateAttrib
                                                        .get("datetime").substring(0, 10),
                                                "update", "dojma_admin", imageurl);
                                        Log.e("TAG", "Added row");

                                    } else {
                                        Log.e("TAG", "foo not 0 - Record existed!");
                                        cursor.close();
                                       /* SQLiteDatabase sdb = dop.getWritableDatabase();
                                        sdb.execSQL("UPDATE " + TableData.TableInfo.TABLE_NAME +
                                                "\nSET " + TableData.TableInfo.tableURL + "='"
                                                + mainAttribute.get("href") + "'\nWHERE " + TableData
                                                .TableInfo.tablePostID + "='" + postIDAttribute.get("id")
                                                + "';");
*/
                                    }
                                }
                            }


                        }
                    }
                } catch (IOException e) {
                    Log.e("TAG", "Error occurred");
                }

            }

            return null;
        }


    }


}
