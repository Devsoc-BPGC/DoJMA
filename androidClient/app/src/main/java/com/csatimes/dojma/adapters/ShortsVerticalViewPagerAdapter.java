package com.csatimes.dojma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csatimes.dojma.R;

import androidx.viewpager.widget.PagerAdapter;

public class ShortsVerticalViewPagerAdapter extends PagerAdapter {

    String mTitleResources[] = {"Title 1", "Title 2", "Title 3", "Title 4", "Title 5"};
    String mSubtitleResources[] = {"Short Sample text 1!", "Short Sample text 2!", "Short Sample text 3!", "Short Sample text 4!", "Short Sample text 5!"};
    String mImageResources[] = {};
    Context mContext;
    LayoutInflater mLayoutInflater;

    public ShortsVerticalViewPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.viewholder_shorts, container, false);

        TextView title = (TextView) itemView.findViewById(R.id.textview_title_shorts);
        TextView subtitle = (TextView) itemView.findViewById(R.id.textview_subtitle_shorts);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageview_shorts);

        title.setText(mTitleResources[position]);
        subtitle.setText(mSubtitleResources[position]);
        imageView.setImageResource(R.drawable.bits);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((LinearLayout) object);
    }
}
