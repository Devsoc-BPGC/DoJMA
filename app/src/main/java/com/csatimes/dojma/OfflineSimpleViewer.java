package com.csatimes.dojma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class OfflineSimpleViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm database = Realm.getDefaultInstance();

        if (postID != null) {
            HeraldNewsItemFormat article = database.where(HeraldNewsItemFormat.class)
                    .equalTo("postID", postID)
                    .findFirst();
            articleText.setText(article.getDesc());
            articleAuthor.setText(article.getUpdateDate() + " • " + article.getAuthor());
            articleTitle.setText(article.getTitle());
        }

        database.close();

    }

}
