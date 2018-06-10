package com.csatimes.dojma.campuswatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ShortsVp extends ViewPager {
    public ShortsVp(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setPageTransformer(true, new ShortsPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        final boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views
        return intercepted;
    }

    private MotionEvent swapXY(final MotionEvent ev) {
        final float width = getWidth();
        final float height = getHeight();

        final float newX = (ev.getY() / height) * width;
        final float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

    public class ShortsPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(final View view, final float position) {
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

                //set Y position to swipe in from top
                final float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // [0,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);


                // Scale the page down (between MIN_SCALE and 1)
                final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
