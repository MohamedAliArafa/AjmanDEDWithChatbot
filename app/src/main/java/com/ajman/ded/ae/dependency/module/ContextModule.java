package com.ajman.ded.ae.dependency.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    Context appContext;

    public ContextModule(Context context) {
        this.appContext = context;
    }

    @Provides
    public Context context() {
        return appContext;
    }
}