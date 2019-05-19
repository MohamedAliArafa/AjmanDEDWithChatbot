package com.ajman.ded.ae.screens.registeration;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;
import static com.ajman.ded.ae.utility.Constants.REGISTER_FRAGMENT_KEY;

public class RegisterActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        findViewById(R.id.up).setVisibility(View.VISIBLE);
        findViewById(R.id.up).setOnClickListener(view -> onBackPressed());
//        if (Build.VERSION.SDK_INT < 23) {
//
//        } else {
//            requestContactPermission();
//        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new RegisterFragment(), REGISTER_FRAGMENT_KEY);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC)) {
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        } else {
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC)) {
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        } else {
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        }
    }

//    private void requestContactPermission() {
//
//        int hasContactPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
//
//        if (hasContactPermission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 123);
//        } else {
//
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                // Check if the only required permission has been granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Contact permission has now been granted. Showing result.");
                } else {
                    Log.i("Permission", "Contact permission was NOT granted.");
                }
                break;
        }
    }
}
