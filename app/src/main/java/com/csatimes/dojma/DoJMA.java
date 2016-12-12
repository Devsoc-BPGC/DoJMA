package com.csatimes.dojma;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DoJMA extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
