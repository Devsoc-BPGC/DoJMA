package com.csatimes.dojma.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewRM extends TextView {
    public CustomTextViewRM(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/ralewaymedium.ttf");
        setTypeface(tf);
    }
}
