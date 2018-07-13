package com.csatimes.dojma.campuswatch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ShortsItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import io.realm.Realm;

import static com.csatimes.dojma.aboutapp.AboutAppActivity.TAG;
import static com.csatimes.dojma.models.ShortsItem.FIELD_READ;
import static com.csatimes.dojma.models.ShortsItem.saveFirebaseData;
import static com.csatimes.dojma.utilities.FirebaseKeys.CAMPUS_WATCH;

public class ShortsAdapter extends PagerAdapter {

    private final List<ShortsItem> shortsItems = new ArrayList<>(0);

    @SuppressWarnings("WeakerAccess")
    public ShortsAdapter() {
        getDataFromFirebase();
        getDataFromRealm();
    }

    private void getDataFromRealm() {
        shortsItems.clear();
        final Realm realm = Realm.getDefaultInstance();
        for (final ShortsItem item : realm.where(ShortsItem.class).equalTo(FIELD_READ, false).findAll()) {

            shortsItems.add(realm.copyFromRealm(item));

        }
        realm.close();
    }

    private void getDataFromFirebase() {
        FirebaseDatabase.getInstance().getReference(CAMPUS_WATCH)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        final Realm db = Realm.getDefaultInstance();
                        db.executeTransaction(realm -> {
                            shortsItems.clear();
                            shortsItems.addAll(saveFirebaseData(dataSnapshot, realm));
                            notifyDataSetChanged();
                        });
                        db.close();
                    }

                    @Override
                    public void onCancelled(final DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
                    }
                });
    }

    @Override
    public int getCount() {
        return shortsItems.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.vh_shorts,
                        container,
                        false);

        final TextView title = itemView.findViewById(R.id.tv_title_shorts);
        final TextView content = itemView.findViewById(R.id.tv_content);
        final TextView timestamp = itemView.findViewById(R.id.tv_timestamp_shorts);
        final SimpleDraweeView imageSdv = itemView.findViewById(R.id.sdv_shorts);
        final ShortsItem shortsItem = shortsItems.get(position);

        title.setText(shortsItem.title);
        content.setText(shortsItem.content);
        timestamp.setText(shortsItem.timestamp);
        imageSdv.setImageURI(shortsItem.imageUrl);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(final ViewGroup container, final int position, final Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

}
