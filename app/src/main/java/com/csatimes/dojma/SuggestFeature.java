package com.csatimes.dojma;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SuggestFeature extends AppCompatActivity {
    EditText suggestion;
    Intent emailIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_feature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suggestion = (EditText) findViewById(R.id.feature);
                if (!suggestion.getText().toString().matches("")) {

                    emailIntent = new Intent(Intent
                            .ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mac", "f2015209@goa.bits-pilani" +
                            ".ac.in"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FEATURE/SUGG REQUEST FOR DOJMA APP");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, suggestion.getText().toString());
                    Log.e("TAG", suggestion.getText().toString() + " is sug");

                    startActivity(Intent.createChooser(emailIntent, "Send email.."));

                } else {

                    Snackbar snack = Snackbar.make(view, "Empty field!", Snackbar.LENGTH_LONG);

                    View sncview = snack.getView();


                    //If you need to change background color of the snackbar use this snippet
                    /*sncview.setBackgroundColor(ContextCompat.getColor
                            (SuggestFeature.this, R
                                    .color
                                    .black));
                    */

                    //To get the snackbar text
                    TextView snctetxt = (TextView) sncview.findViewById(android.support.design.R.id
                            .snackbar_text);

                    //Set color of the snackbar
                    snctetxt.setTextColor(ContextCompat.getColor
                            (SuggestFeature.this, R
                                    .color
                                    .colorAccent));


                    // To set the typeface of the texxt
                    snctetxt.setTypeface(null,Typeface.BOLD);

                    //finally show the snackbar
                    snack.show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
