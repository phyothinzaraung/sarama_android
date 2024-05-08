package com.koekoetech.sayarma.app;

import android.app.Application;

import com.koekoetech.sayarma.custom_control.AndroidCommonSetup;
import com.koekoetech.sayarma.helper.RealmMigrator;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SARAMA extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidCommonSetup.getInstance().init(getApplicationContext());

        setUpRealm();

    }

    private void setUpRealm() {

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .migration(new RealmMigrator())
                .deleteRealmIfMigrationNeeded()
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
