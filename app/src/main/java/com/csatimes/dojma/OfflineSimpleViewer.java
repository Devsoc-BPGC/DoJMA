package com.csatimes.dojma;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.csatimes.dojma.models.HeraldNewsItemFormat;

import io.realm.Realm;

public class OfflineSimpleViewer extends AppCompatActivity {
    private HeraldNewsItemFormat article;

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_simple_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.offline_toolbar);
        TextView articleText = (TextView) findViewById(R.id.offline_article);
        TextView articleTitle = (TextView) findViewById(R.id.offline_article_title);
        TextView articleAuthor = (TextView) findViewById(R.id.offline_article_author);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String postID = intent.getStringExtra("POSTID");
         Realm database = Realm.getDefaultInstance();

        if (postID != null) {
            article = database.where(HeraldNewsItemFormat.class)
                    .equalTo("postID", postID)
                    .findFirst();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                articleTitle.setText(Html.fromHtml(article.getTitle(), Html.FROM_HTML_MODE_LEGACY));
                articleText.setText(Html.fromHtml(article.getContent(), Html.FROM_HTML_MODE_LEGACY));
                articleTitle.setText(Html.fromHtml(article.getTitle(), Html.FROM_HTML_MODE_LEGACY));
                if (article.getAuthorName() != null)
                    articleAuthor.setText(Html.fromHtml(article.getAuthorName(), Html.FROM_HTML_MODE_LEGACY));
                else if (article.getAuthorFName() != null)
                    articleAuthor.setText(Html.fromHtml(article.getAuthorFName(), Html.FROM_HTML_MODE_LEGACY));
                else if (article.getAuthorLName() != null)
                    articleAuthor.setText(Html.fromHtml(article.getAuthorLName(), Html.FROM_HTML_MODE_LEGACY));
                else if (article.getAuthorNName() != null)
                    articleAuthor.setText(Html.fromHtml(article.getAuthorNName(), Html.FROM_HTML_MODE_LEGACY));
                else articleAuthor.setText("dojma_admin");

            } else {
                articleTitle.setText(Html.fromHtml(article.getTitle()));
                articleText.setText(Html.fromHtml(article.getContent()));

                if (article.getAuthorName() != null)
                    articleAuthor.setText(Html.fromHtml(article.getAuthorName()));
                else if (article.getAuthorFName() != null)
                    articleAuthor.setText(Html.fromHtml(article.getAuthorFName()));
                else if (article.getAuthorLName() != null)
                    articleAuthor.setText(Html.fromHtml(article.getAuthorLName()));
                else if (article.getAuthorNName() != null)
                    articleAuthor.setText(Html.fromHtml(article.getAuthorNName()));
                else articleAuthor.setText("dojma_admin");

            }
        }

        database.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.offline_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.offline_menu_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, article.getUrl());
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share via"));
        }

        return super.onOptionsItemSelected(item);
    }
}
