package com.csatimes.dojma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
    private ImageLoader downloader;
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
            //e.printStackTrace();
            new SimpleAlertDialog().showDialog(this, "MalformedURLException", e.toString(), "OK", "",
                    true, false);
        }
    }


    //Async method to download html document for parsing
    private class DownloadDocument extends AsyncTask<URL, Integer, Void> {
        int foo = 0;
        Cursor cursor;

        //AsyncTask has <params,progress,result> format
        protected Void doInBackground(URL... htmlURL) {
            org.jsoup.nodes.Document[] HTMLDocuments = new Document[htmlURL.length];
            //initialise documents to null
            for (foo = 0; foo < htmlURL.length; foo++)
                HTMLDocuments[foo] = null;
            DatabaseOperations dop = new DatabaseOperations
                    (DownloadForFirstTimeActivity.this);
            SQLiteDatabase sdb = dop.getReadableDatabase();
            for (foo = 0; foo < htmlURL.length; foo++) {
                try {
                    {
                        Log.e("TAG", "Connecting to URL " + htmlURL[foo]);
                        HTMLDocuments[foo] = Jsoup.connect(htmlURL[foo].toString()).get();
                        if (HTMLDocuments[foo] == null) Log.e("TAG", "HTML download failed");
                        else {

                            Log.e("TAG", HTMLDocuments[foo].title());

                            Cursor cursor = null;
                            int noOfArticles = 0;
                            //notify onProgressUpdate using predefined method

                            String imageURL;
                            //Get all elements that have the "article" tag
                            Elements documentElements = HTMLDocuments[foo].getElementsByTag("article");

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
                                    //Incase there is no update date, use date of upload
                                    Log.e("DATE", "update date did not exist");
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


                                //String postid, String title,String postURL, String date,String updateDate, String
                                //author, String imageURL

                                //Cursor object cursor is used to check whether that link
                                // already exists or not
                                //if it doesn't then add otherwise update

                                cursor = sdb.rawQuery("SELECT * " +
                                        "FROM" +
                                        " " + TableData.TableInfo.TABLE_NAME + " WHERE " +
                                        TableData.TableInfo.tablePostID + " = " +
                                        "'" + postIDTemp + "';", null);


                                if (cursor.getCount() <= 0) {

                                    ///adding all these parsed values to database using insertRow
                                    // method of DatabaseOperations object


                                    dop.insertRow(postIDTemp, mainAttribute.get
                                                    ("title"), mainAttribute.get("href"), dateAttrib
                                                    .get("datetime").substring(0, 10),
                                            updateDateAttrib.get("datetime").substring(0, 10),
                                            "dojma_admin", imageURL);
                                    Log.e("TAG", "Added row");

                                } else {
                                    Log.e("TAG", "Record existed!");
                                       /* SQLiteDatabase sdb = dop.getWritableDatabase();
                                        sdb.execSQL("UPDATE " + TableData.TableInfo.TABLE_NAME +
                                                "\nSET " + TableData.TableInfo.tableURL + "='"
                                                + mainAttribute.get("href") + "'\nWHERE " + TableData
                                                .TableInfo.tablePostID + "='" + postIDAttribute.get("id")
                                                + "';");
*/
                                }
                                publishProgress(100 * (foo + 1) / htmlURL.length *
                                        postsCounter /
                                        noOfArticles);


                            }
                            if (!cursor.isClosed())
                                cursor.close();

                        }
                    }
                } catch (IOException e) {
                    Log.e("TAG", e.toString());
                }

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            DatabaseOperations dop = new DatabaseOperations(getBaseContext());
            SQLiteDatabase sql = dop.getReadableDatabase();
            cursor = dop.getInformation();
            if (cursor.getCount() >= 100) {
                progressBar.setProgress(100);
                preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
                editor = preferences.edit();
                editor.putBoolean(getString(R.string.SP_first_install), false);
                editor.apply();
                if (cursor.moveToFirst())
                    for (int i = 0; i < cursor.getCount(); i++) {
                        Log.e("TAG", "Downloading for " + cursor.getString(2));
                        Picasso.with(DownloadForFirstTimeActivity.this).load(cursor.getString(3))
                                .resize(DHC.dpToPx(40), DHC.dpToPx(40)).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                DHC.saveImage(bitmap, cursor.getString(0));

                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
                        cursor.moveToNext();
                    }


                startActivity(new Intent(DownloadForFirstTimeActivity.this, HomeActivity.class));
            } else {
                Log.e("TAG", "size is " + dop.getInformation().getCount());
                Toast.makeText(DownloadForFirstTimeActivity.this, "Download Failed! Try again " +
                                "later",
                        Toast.LENGTH_LONG).show();

                DownloadForFirstTimeActivity.this.finish();
                System.exit(0);
            }
        }

    }


}
