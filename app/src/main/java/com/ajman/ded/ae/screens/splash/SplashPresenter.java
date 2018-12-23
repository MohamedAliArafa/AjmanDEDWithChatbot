package com.ajman.ded.ae.screens.splash;

import android.content.Context;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.models.RegisterLookups;
import com.ajman.ded.ae.models.ServiceModuleModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import io.realm.Realm;

class SplashPresenter implements LifecycleObserver {

    private final Context context;
    private SplashContract.ModelView view;

    SplashPresenter(SplashContract.ModelView view, Context context, Lifecycle lifecycle) {
        this.view = view;
        this.context = context;
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        InputStream raw = context.getResources().openRawResource(R.raw.e_service);
        InputStream raw2 = context.getResources().openRawResource(R.raw.register_lookups);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Reader rd2 = new BufferedReader(new InputStreamReader(raw2));
        Gson gson = new Gson();
        ServiceModuleModel[] list = gson.fromJson(rd, ServiceModuleModel[].class);
        List<ServiceModuleModel> models = new ArrayList<>();
        models.addAll(Arrays.asList(list));
        RegisterLookups list2 = gson.fromJson(rd2, RegisterLookups.class);
        RegisterLookups models2 = new RegisterLookups(list2);
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(models);
        Realm.getDefaultInstance().copyToRealmOrUpdate(models2);
        Realm.getDefaultInstance().commitTransaction();
        view.startTimer();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void stop() {
        view.killTimer();
    }
}
