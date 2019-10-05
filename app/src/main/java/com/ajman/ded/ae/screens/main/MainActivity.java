package com.ajman.ded.ae.screens.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.screens.IntroActivity;
import com.ajman.ded.ae.screens.dashboard.DashBoardActivity;
import com.ajman.ded.ae.screens.language.LangFragment;
import com.ajman.ded.ae.screens.login.LoginFragment;
import com.ajman.ded.ae.screens.loginMenu.LoginMenuFragment;
import com.ajman.ded.ae.screens.splash.SplashFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;
import static com.ajman.ded.ae.utility.Constants.LOGIN_FRAGMENT_KEY;

public class MainActivity extends AppCompatActivity implements MainContract.ModelView {

    public static boolean SplashAnimation = true;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult().getToken();

                    Log.d("NEZAR Device Token:", token);
                });

        if (!isTaskRoot()) {
            finish();
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, SplashFragment.newInstance());
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void launchLanding() {
        TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(new Intent(this, DashBoardActivity.class))
                .startActivities();
    }

    @Override
    public void launchLogin() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment(), LOGIN_FRAGMENT_KEY);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void launchLoginMenu() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, LoginMenuFragment.newInstance());
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void launchLanguage() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LangFragment(), LOGIN_FRAGMENT_KEY);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void requestRestartActivity() {
        restartActivity();
    }

    @Override
    public void launchIntro() {
        startActivity(new Intent(this, IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
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

    private void restartActivity() {
        SplashAnimation = false;
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
