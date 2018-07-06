package com.csatimes.dojma.models;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@SuppressWarnings("DuplicateStringLiteralInspection")
@Keep
public class Member extends RealmObject {

    @PrimaryKey
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("phone")
    public String phone;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("post")
    public String post;

    public static List<Member> insertMembersFromFirebase(@NonNull final DataSnapshot dataSnapshot,
                                                              @NonNull final Realm db) {
        if (!db.isInTransaction()) {
            throw new IllegalStateException("Db must be in transaction");
        }
        final List<Member> result = new ArrayList<>(0);
        for(final DataSnapshot child: dataSnapshot.getChildren()) {
            final Member updatedData = child.getValue(Member.class);
            db.insertOrUpdate(updatedData);
            result.add(updatedData);
        }
        return result;
    }
}
