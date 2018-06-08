package com.csatimes.dojma.adapters;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.SlideshowItem;
import com.facebook.drawee.view.SimpleDraweeView;

import io.realm.RealmList;

/**
 * Created by vikramaditya on 27/2/17.
 */

public class SlideshowPagerAdapter extends PagerAdapter {
    private RealmList<SlideshowItem> slideshowItems;

    public SlideshowPagerAdapter(RealmList<SlideshowItem> slideshowItems) {
        this.slideshowItems = slideshowItems;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_format_slideshow, container, false);

        if (slideshowItems.size() == getCount()) {
            ((SimpleDraweeView) itemView.findViewById(R.id.item_format_slideshow_image_sdv)).setImageURI(slideshowItems.get(position).getImageUrl());
            ((TextView) itemView.findViewById(R.id.item_format_slideshow_title_tv)).setText(slideshowItems.get(position).getTitle());
            ((TextView) itemView.findViewById(R.id.item_format_slideshow_subtitle_tv)).setText(slideshowItems.get(position).getSubTitle());
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return slideshowItems.size() != 0 ? slideshowItems.size() : 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
