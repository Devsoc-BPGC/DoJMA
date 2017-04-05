package com.csatimes.dojma.utilities;

/**
 * Created by vikramaditya on 27/2/17.
 */

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.database.Exclude;

import io.realm.annotations.Ignore;

public class StackTransformer implements ViewPager.PageTransformer {

 public  void transformPage(View view, float position) {

        if (0 < position && position < 1f) {
            // Fade the page out
            view.setAlpha(1f - position);
            // Counteract the default slide transition
            view.setTranslationX(-view.getWidth() * position);

        } else {
            //view.setAlpha(1f);
            view.setTranslationX(0f);
        }
    }

}