package com.baladika.jadwalkuliah;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class AppOffline extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
