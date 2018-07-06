package com.csatimes.dojma.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewRL extends TextView {
    public CustomTextViewRL(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/ralewaylight.ttf");
        setTypeface(tf);
    }
}
