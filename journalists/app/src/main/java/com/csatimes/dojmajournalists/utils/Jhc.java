package com.csatimes.dojmajournalists.utils;

import com.google.firebase.BuildConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Journalist Helper Class
 *
 * @author Rushikesh Jogdand.
 */
public final class Jhc {

    public static DatabaseReference getFirebaseRef() {
        if (BuildConfig.DEBUG) {
            return FirebaseDatabase.getInstance().getReference().child("debug");
        } else {
            return FirebaseDatabase.getInstance().getReference();
        }
    }
}
