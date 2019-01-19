package com.csatimes.dojma.utilities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Rushikesh Jogdand.
 */
public final class FirebaseValues {
    public static final String CONTRIB = "contributors";
    public static final String MEMBER = "members";
    public static final String CAMPUS_WATCH = "campusWatch";
    public static final String MSG_TYPE_CAMPUS_WATCH = "campusWatch";
    public static final String VIDEOS = "videos";

    public static DatabaseReference videosRef() {
        DatabaseReference vidRef = FirebaseDatabase.getInstance().getReference()
                .child(VIDEOS);
        vidRef.keepSynced(true);
        return vidRef;
    }
}
