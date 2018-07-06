package com.csatimes.dojma.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewRR extends TextView {
    public CustomTextViewRR(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/ralewayregular.ttf");
        setTypeface(tf);
    }
}
