package com.csatimes.dojma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.HeraldItem;

import io.realm.Realm;

public class OfflineSimpleViewerActivity extends AppCompatActivity {

    private HeraldItem mHeraldArticle;

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
            mHeraldArticle = database.where(HeraldItem.class)
                    .equalTo("postID", postID)
                    .findFirst();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                articleTitle.setText(Html.fromHtml(mHeraldArticle.getTitle(), Html.FROM_HTML_MODE_LEGACY));
                articleText.setText(Html.fromHtml(mHeraldArticle.getContent(), Html.FROM_HTML_MODE_LEGACY));
                articleTitle.setText(Html.fromHtml(mHeraldArticle.getTitle(), Html.FROM_HTML_MODE_LEGACY));
                if (mHeraldArticle.getAuthorName() != null)
                    articleAuthor.setText(Html.fromHtml(mHeraldArticle.getAuthorName(), Html.FROM_HTML_MODE_LEGACY));
                else if (mHeraldArticle.getAuthorFName() != null)
                    articleAuthor.setText(Html.fromHtml(mHeraldArticle.getAuthorFName(), Html.FROM_HTML_MODE_LEGACY));
                else if (mHeraldArticle.getAuthorLName() != null)
                    articleAuthor.setText(Html.fromHtml(mHeraldArticle.getAuthorLName(), Html.FROM_HTML_MODE_LEGACY));
                else if (mHeraldArticle.getAuthorNName() != null)
                    articleAuthor.setText(Html.fromHtml(mHeraldArticle.getAuthorNName(), Html.FROM_HTML_MODE_LEGACY));
                else articleAuthor.setText("dojma_admin");

            } else {
                articleTitle.setText(Html.fromHtml(mHeraldArticle.getTitle()));
                articleText.setText(Html.fromHtml(mHeraldArticle.getContent()));

                if (mHeraldArticle.getAuthorName() != null)
                    articleAuthor.setText(Html.fromHtml(mHeraldArticle.getAuthorName()));
                else if (mHeraldArticle.getAuthorFName() != null)
                    articleAuthor.setText(Html.fromHtml(mHeraldArticle.getAuthorFName()));
                else if (mHeraldArticle.getAuthorLName() != null)
                    articleAuthor.setText(Html.fromHtml(mHeraldArticle.getAuthorLName()));
                else if (mHeraldArticle.getAuthorNName() != null)
                    articleAuthor.setText(Html.fromHtml(mHeraldArticle.getAuthorNName()));
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
            intent.putExtra(Intent.EXTRA_TEXT, mHeraldArticle.getUrl());
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share via"));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }
}
