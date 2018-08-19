package com.csatimes.dojma.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.csatimes.dojma.R;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;

public class SlidingImageAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Integer> GalImages;

    public SlidingImageAdapter(Context context, ArrayList<Integer> GalImages){
        this.context=context;
        this.GalImages = GalImages;
    }
    @Override
    public int getCount() {
        return GalImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
//        int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
//        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(GalImages.get(position));
        (container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((ImageView) object);
    }
}