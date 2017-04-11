package com.csatimes.dojma.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.R;

public class AboutDojmaActivity extends BaseActivity implements View.OnClickListener{

    String ABOUT_US_FACEBOOK_PAGE_ID="DoJMABITSGoa";
    String ABOUT_US_FACEBOOK_URL="https://www.facebook.com/DoJMABITSGoa";

    String html = "\n" +
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><p>We at <i><b>The Department of Journalism and Media Affairs</b></i>, BITS Pilani K.K. Birla Goa Campus (a.k.a. DoJMA) are a group of  writers, designers, cartoonists and photographers from all streams and years of study who strive to bring to you timely updates on all that happens on campus.</p>\n" +
            "\n" +
            "<p>A Departmental Association established in 2006, DoJMA is responsible for keeping the student body abreast of all that is relevant to it, on campus and off it. DoJMA also oversees event reportage for the 3 college festivals- Waves, Quark and Spree. Our extensive coverage can be accessed through the following channels:</p>\n" +
            "\n" +
            "<p><b>Campus Watch</b>: Frequent news updates that provide same-day pictorial coverage of notable events on campus, disseminated primarily via Facebook posts from our page.</p>\n" +
            "\n" +
            "<p><b>BITS Gazette</b>: DoJMA’s fortnightly newsletter, which provides a succinct round-up of all the goings-on on campus. Catch it on hostel notice boards or in your BITSmail inbox.</p>\n" +
            "\n" +
            "<p><b>BITS Herald</b>: Our flagship publication, which throughout its rich history has been providing news bytes, cartoons, interviews of campus notables (and noteables on campus), insights on campus happenings and BITSians’ perspectives on global events. Ever transforming in keeping with the times, the Herald has in the past been a newsletter, a digital magazine and a blog. It now comes to you in its newest avatar, as the core of the DoJMA App.</p>\n" +
            "\n" +
            "\n" +
            "<p><b>The DoJMA team:</b></p>\n" +
            "\n" +
            "<p><b>Chief Coordinator</b>: Rohan Gajendragadkar</p>\n" +
            "<p><b>Chief Editor</b>: Nissim Gore-Datar</p>\n" +
            "<p><b>Head, Waves</b>: Somya Shubhangi</p>\n" +
            "<p><b>Head, Quark</b>: Ranajoy Roy</p>\n" +
            "<p><b>Head, Spree</b>: Shashank Subramaniam</p>\n" +
            "<p><b>Design Head</b>: Swati Shikha</p>\n" +
            "<p><b>Cartoon Editor</b>: Akshay Ginodia</p>\n" +
            "<p><b>Editor</b>: Harsh Lodha</p>\n" +
            "\n" +
            "<p><b>Journalists</b>-</p>\n" +
            "<p>Aabir Abubaker, Aarsh, Adheep Das, Ananya Shivaditya, Anirudh Dwarakanath, Aparajita Haldar, Ayush Anand, Chand Sethi, Esha Swaroop, Jayachandran Siva, Mahima Samant, Meghana Gupta, Nidhi Kadkol, Nitish Kulshrestha, Rahul Hardikar, Rishi Raj Grandhe,Roshan Dattatri, Roshan Nair, Sahith Dambekodi, Sakshi Mehra, Saloni Dash, Sangeeth Jayprakash, Sangeeth Jayaprakash,  Shriya Srivastava, Shubham Gupta, Srijani Biswas, Sumangala Patki, Tulasi Ravindran, Vedant Kumar</p>\n" +
            "\n" + "</body></html>";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dojma);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_dojma_toolbar);

        ImageButton facebookImgBtn = (ImageButton) findViewById(R.id.content_about_dojma_fb_imgbtn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) findViewById(R.id.about_dojma_text);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(html));
        }

        facebookImgBtn.setOnClickListener(this);
    }

    //.....

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(final Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + ABOUT_US_FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + ABOUT_US_FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return ABOUT_US_FACEBOOK_URL; //normal web url
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.content_about_dojma_fb_imgbtn:
                try {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(this);
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ABOUT_US_FACEBOOK_URL));
                    Toast.makeText(this, "Opening in browser", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }


    //....



}
