package com.csatimes.dojma.utilities;

/**
 * Created by vikramaditya on 27/2/17.
 */

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class StackTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {

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