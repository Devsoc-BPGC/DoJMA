package com.csatimes.dojma;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application class to initialise libraries.
 */
public class DoJMA extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        Fresco.initialize(this);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(DHC.REALM_DOJMA_DATABASE)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(DHC.REALM_DATABASE_SCHEMA_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    /**
     * This is where multi dex needs to be installed.
     * @param base base context
     */
    @Override
    protected void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
