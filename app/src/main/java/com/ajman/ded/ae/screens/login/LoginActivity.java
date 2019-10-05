package com.ajman.ded.ae.screens.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.uaepass.AuthTokenModel;
import com.ajman.ded.ae.models.uaepass.ProfileModel;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;
import static com.ajman.ded.ae.screens.login.LoginFragment.REDIRECT_URL;
import static com.ajman.ded.ae.screens.login.LoginFragment.webViewRequestCode;
import static com.ajman.ded.ae.utility.Constants.CODE_RESULT_KEY;


public class LoginActivity extends AppCompatActivity {
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
//        if (Build.VERSION.SDK_INT > 23) {
//            requestContactPermission();
//        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == webViewRequestCode && resultCode == RESULT_OK) {
            String code = data.getStringExtra(CODE_RESULT_KEY);
            Api apiUaePass = ApiBuilder.uaeAuthServerApi();
            Call<AuthTokenModel> callAuth = apiUaePass.getToken("authorization_code", REDIRECT_URL, code);
            callAuth.enqueue(new retrofit2.Callback<AuthTokenModel>() {
                @Override
                public void onResponse(@NonNull Call<AuthTokenModel> call, @NonNull Response<AuthTokenModel> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        Log.d("Token:", response.body().getAccessToken());
                        Api apiUaeResources = ApiBuilder.uaeResourcesApi(response.body().getAccessToken());
                        apiUaeResources.getProfile().enqueue(new Callback<ProfileModel>() {
                            @Override
                            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                                assert response.body() != null;
                                Toast.makeText(LoginActivity.this, "Welcome, " + response.body().getFullnameEN(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ProfileModel> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AuthTokenModel> call, @NonNull Throwable t) {
                    Log.d("PIN ERROR:", String.valueOf(t));
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
