package com.csatimes.dojma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ShortsItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class ShortsVerticalViewPagerAdapter extends PagerAdapter {

    String mTitleResources[] = {"Title 1", "Title 2", "Title 3", "Title 4", "Title 5"};
    String mSubtitleResources[] = {"Short Sample text 1!", "Short Sample text 2!", "Short Sample text 3!", "Short Sample text 4!", "Short Sample text 5!"};
    String mImageResources[] = {};
    Context mContext;
    LayoutInflater mLayoutInflater;
    String ref = "campusWatch";

    private List<ShortsItem> mShortsItems = new ArrayList<>();
    private List<String> mShortsKeys = new ArrayList<>();


    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = mDatabase.getReference(ref);
    private ChildEventListener mChildEventListener;

    public ShortsVerticalViewPagerAdapter(Context context) {
        mContext = context;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ShortsItem shortsItem = dataSnapshot.getValue(ShortsItem.class);

                mShortsItems.add(shortsItem);
                mShortsKeys.add(dataSnapshot.getKey());
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ShortsItem shortsItem = dataSnapshot.getValue(ShortsItem.class);
                String shortsKey = dataSnapshot.getKey();

                int shortsIndex = mShortsKeys.indexOf(shortsKey);
                if(shortsIndex > -1) {
                    mShortsItems.set(shortsIndex, shortsItem);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String shortsKey = dataSnapshot.getKey();

                int shortsIndex = mShortsKeys.indexOf(shortsKey);
                if(shortsIndex > -1) {
                    mShortsItems.remove(shortsIndex);
                    mShortsKeys.remove(shortsIndex);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addChildEventListener(childEventListener);
        mChildEventListener = childEventListener;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mShortsItems.size();
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

        ShortsItem shortsItem = mShortsItems.get(position);

        title.setText(shortsItem.getTitle());
        subtitle.setText(shortsItem.getSubtitle());
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
