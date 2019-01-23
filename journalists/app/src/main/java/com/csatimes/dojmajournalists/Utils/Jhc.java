package com.csatimes.dojmajournalists.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Journalist Helper Class
 *
 * @author Rushikesh Jogdand.
 */
public class Jhc {

    public static DatabaseReference getFirebaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }
}
