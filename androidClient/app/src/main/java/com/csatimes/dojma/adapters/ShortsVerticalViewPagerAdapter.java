package com.csatimes.dojma.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ShortsItem;
import com.csatimes.dojma.models.Tag;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

import static com.csatimes.dojma.aboutapp.AboutAppActivity.TAG;

public class ShortsVerticalViewPagerAdapter extends PagerAdapter {

    private LayoutInflater mLayoutInflater;
    private String ref = "campusWatch";

    private List<ShortsItem> mShortsItems = new ArrayList<>();
    private List<String> mShortsKeys = new ArrayList<>();


    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = mDatabase.getReference(ref);

    public ShortsVerticalViewPagerAdapter(Context context) {
        Context mContext = context;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "ShortsAdapter:onChildAdded:" + dataSnapshot.getKey());
                ShortsItem shortsItem = dataSnapshot.getValue(ShortsItem.class);

                mShortsItems.add(shortsItem);
                mShortsKeys.add(dataSnapshot.getKey());
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "ShortsAdapter:onChildChanged:" + dataSnapshot.getKey());
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
                Log.d(TAG, "ShortsAdapter:onChildRemoved:" + dataSnapshot.getKey());
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
                Log.d(TAG, "ShortsAdapter:onChildMoved" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "ShortsAdapter:onCancelled", databaseError.toException());
                Toast.makeText(mContext, "Failed to load card.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        mRef.addChildEventListener(childEventListener);
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
        TextView timestamp = (TextView) itemView.findViewById(R.id.textview_timestamp_shorts);

        SimpleDraweeView draweeView = (SimpleDraweeView) itemView.findViewById(R.id.draweeview_shorts);

        ShortsItem shortsItem = mShortsItems.get(position);

        title.setText(shortsItem.getTitle());
        subtitle.setText(shortsItem.getSubtitle());
        timestamp.setText(shortsItem.getTimestamp());
        Uri uri = Uri.parse(shortsItem.getImageUrl());
        draweeView.setImageURI(uri);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((LinearLayout) object);
    }
}
