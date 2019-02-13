package com.ajman.ded.ae.dependency.component;

import com.ajman.ded.ae.dependency.module.LocationModule;
import com.ajman.ded.ae.dependency.scope.AjmanScope;
import com.ajman.ded.ae.screens.ded_eye.NewNotificationActivity;

import dagger.Component;

@AjmanScope
@Component(modules = LocationModule.class)
public interface LocationComponent {
    void inject(NewNotificationActivity newNotificationActivity);
}