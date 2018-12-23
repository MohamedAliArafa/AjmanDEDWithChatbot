package com.ajman.ded.ae;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.preference.PreferenceManager;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.utility.pdfViewer.asset.CopyAsset;
import com.ajman.ded.ae.utility.pdfViewer.asset.CopyAssetThreadImpl;
import java.io.File;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    final String[] sampleAssets = {"sample.pdf"};


    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
//        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initSampleAssets();
//        Fabric.with(this, new Crashlytics());
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    private void initSampleAssets() {
        CopyAsset copyAsset = new CopyAssetThreadImpl(this, new Handler());
        for (String asset : sampleAssets) {
            copyAsset.copy(asset, new File(getCacheDir(), asset).getAbsolutePath());
        }
    }


    public void removeUser() {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext()).edit();
        editor.remove("UserName");
        editor.remove("Password");
        editor.apply();
    }

    public void addUser(String username, String password) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext()).edit();
        editor.putString("UserName", username);
        editor.putString("Password", password);
        editor.apply();
    }

}
