package com.ajman.ded.ae.dependency.component;

import com.ajman.ded.ae.dependency.module.LocationModule;
import com.ajman.ded.ae.dependency.scope.AjmanScope;
import com.ajman.ded.ae.screens.complaints.SubmitActivity;

import dagger.Component;

@AjmanScope
@Component(modules = LocationModule.class)
public interface LocationComponent {
    void inject(SubmitActivity submitActivity);
}