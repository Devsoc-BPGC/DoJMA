package com.csatimes.dojma;

import android.app.Application;

import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application class to initialise libraries.
 */
public class DoJMA extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        FirebaseMessaging.getInstance().subscribeToTopic("newCampusWatchAdded");
        FirebaseMessaging.getInstance().subscribeToTopic("newDojmaNotice");
        Fresco.initialize(this);
        Realm.init(this);
        final RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(DHC.REALM_DOJMA_DATABASE)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(DHC.REALM_DATABASE_SCHEMA_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Fresco.initialize(this);
    }
}
