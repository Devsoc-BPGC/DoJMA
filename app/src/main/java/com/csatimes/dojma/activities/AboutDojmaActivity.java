package com.csatimes.dojma.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import com.csatimes.dojma.R;

public class AboutDojmaActivity extends BaseActivity {

    String html = "<b>The Editorial Team</b><br>" +
            "<br>" +
            " <br>" +
            "<br>" +
            "<b>Chief Coordinator</b>: Sumangala Patki<br>" +
            "<br>" +
            "<b>Chief Editor</b>: M Kaushik Reddy<br>" +
            "<br>" +
            "<b>Head, Waves</b>: Nitish Kulshrestha<br>" +
            "<br>" +
            "<b>Head, Quark</b>: Rishi Raj Grandhe<br>" +
            "<br>" +
            "<b>Head, Spree</b>: Mahima Samant<br>" +
            "<br>" +
            "<br>" +
            "<b>Journalists</b><br>" +
            "<br>" +
            " <br>" +
            "<br>" +
            "Adheep Das<br>" +
            "<br>" +
            "Akshay Ginodia<br>" +
            "<br>" +
            "Ayush Anand<br>" +
            "<br>" +
            "Chand Sethi<br>" +
            "<br>" +
            "Harsh Lodha<br>" +
            "<br>" +
            "Nidhi Kadkol<br>" +
            "<br>" +
            "Nissim Gore-Datar<br>" +
            "<br>" +
            "Ranajoy Roy<br>" +
            "<br>" +
            "Rohan Gajendragadkar<br>" +
            "<br>" +
            "Shashank Subramaniam<br>" +
            "<br>" +
            " <br>" +
            "<br>" +
            "Aman Arora<br>" +
            "<br>" +
            "Anurag Kumar<br>" +
            "<br>" +
            "Esha Swaroop<br>" +
            "<br>" +
            "Lucky Kaul<br>" +
            "<br>" +
            "Roshan Dattatri<br>" +
            "<br>" +
            "Roshan Nair<br>" +
            "<br>" +
            "Saloni Dash<br>" +
            "<br>" +
            " <br>" +
            "<br>" +
            "Aditya Lahiri<br>" +
            "<br>" +
            "Akshat Mittal<br>" +
            "<br>" +
            "Aman Singh Yadav<br>" +
            "<br>" +
            "Ankit<br>" +
            "<br>" +
            "Apoorva Dhamne<br>" +
            "<br>" +
            "Aryan Agarwal<br>" +
            "<br>" +
            "Darshan Hegde<br>" +
            "<br>" +
            "Mridul Summan<br>" +
            "<br>" +
            "Parth Ganatra<br>" +
            "<br>" +
            "Parul Verma<br>" +
            "<br>" +
            "Poojan Thakkar<br>" +
            "<br>" +
            "Rachit Rastogi<br>" +
            "<br>" +
            "Saumya Maheshwari<br>" +
            "<br>" +
            "Sharat Patil<br>" +
            "<br>" +
            "Siddhinita Wandekar<br>" +
            "<br>" +
            "Soumalya Barari<br>" +
            "<br>" +
            "Vaibhav Garg";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dojma);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_dojma_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) findViewById(R.id.about_dojma_text);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(html));
        }
    }
}
