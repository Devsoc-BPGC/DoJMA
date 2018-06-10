package com.csatimes.dojma.models;

import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
@Keep
public class Person extends RealmObject {
    @Ignore
    public static final String FIELD_NAME = "name";
    @Ignore
    public static final String FIELD_POST_NAME = "postName";
    @PrimaryKey
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("phone")
    public String phone;
    @Nullable
    @SerializedName("postName")
    public String postName;
    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("homePage")
    public String homePage;

    public boolean isContributor = false;

    public static List<Person> insertContributorsFromFirebase(@NonNull final DataSnapshot dataSnapshot,
                                                              @NonNull final Realm db) {
        if (!db.isInTransaction()) {
            throw new IllegalStateException("Db must be in transaction");
        }
        final List<Person> result = new ArrayList<>(0);
        for(final DataSnapshot child: dataSnapshot.getChildren()) {
            final Person updatedData = child.getValue(Person.class);
            final Person oldData = db.where(Person.class).equalTo(FIELD_NAME, updatedData.name).findFirst();
            if (oldData != null) {
                updatedData.isContributor = oldData.isContributor;
            }
            db.insertOrUpdate(updatedData);
            result.add(updatedData);
        }
        return result;
    }
}
