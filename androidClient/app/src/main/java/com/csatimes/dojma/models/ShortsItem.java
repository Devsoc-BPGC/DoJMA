package com.csatimes.dojma.models;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ShortsItem extends RealmObject {
    @Ignore
    public static final String FIELD_ID = "id";
    @Ignore
    public static final String FIELD_READ = "isRead";
    @PrimaryKey
    public String id;
    public String title;
    public String content;
    public String timestamp;
    public String imageUrl;
    public boolean isRead = false;

    public static List<ShortsItem> saveFirebaseData(@NonNull final DataSnapshot dataSnapshot,
                                                    @NonNull final Realm realm) {
        final List<ShortsItem> result = new ArrayList<>(0);
        if (!realm.isInTransaction()) {
            throw new IllegalStateException("db must be in transaction");
        }
        for (final DataSnapshot child : dataSnapshot.getChildren()) {
            final ShortsItem updatedData = child.getValue(ShortsItem.class);
            if (updatedData == null) {
                continue;
            }
            updatedData.id = child.getKey();
            final ShortsItem oldData = realm.where(ShortsItem.class)
                    .equalTo(FIELD_ID, updatedData.id)
                    .findFirst();
            if (oldData != null) {
                updatedData.isRead = oldData.isRead;
            }
            realm.insertOrUpdate(updatedData);
            if (!updatedData.isRead) {
                result.add(updatedData);
            }
        }
        return result;
    }
}
