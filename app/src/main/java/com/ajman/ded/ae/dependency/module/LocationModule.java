package com.ajman.ded.ae.dependency.module;

import android.content.Context;
import android.location.LocationManager;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.Task;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class LocationModule {

    @Provides
    public Task<LocationSettingsResponse> locationCallback(Context context, LocationSettingsRequest locationSettingRequest) {
        return LocationServices.getSettingsClient(context).checkLocationSettings(locationSettingRequest);
    }

    @Provides
    public LocationSettingsRequest locationSetting(LocationRequest locationRequest) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        return builder.build();
    }

    @Provides
    public LocationRequest locationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        return locationRequest;
    }

    @Provides
    public LocationManager manager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
}