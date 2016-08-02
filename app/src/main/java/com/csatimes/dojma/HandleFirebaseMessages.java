package com.csatimes.dojma;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class HandleFirebaseMessages extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("TAG", remoteMessage.getTtl() + " getttl");

    }
}
