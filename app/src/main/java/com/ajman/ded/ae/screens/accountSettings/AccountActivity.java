package com.ajman.ded.ae.screens.accountSettings;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ajman.ded.ae.FaqActivity;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.ajman.ded.ae.screens.language.LangFragment;
import com.ajman.ded.ae.screens.login.LoginFragment;
import com.ajman.ded.ae.screens.loginMenu.LoginMenuFragment;
import com.ajman.ded.ae.screens.main.MainContract;
import com.ajman.ded.ae.utility.SharedTool.UserData;

import java.util.Objects;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountActivity extends BaseActivity implements MainContract.ModelView {
    TextView changeLanguage, changeAccount, logout;


    public AccountActivity() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_account;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeLanguage = findViewById(R.id.change_language);
        changeAccount = findViewById(R.id.change_account);
        if (UserData.getUserObject(this) == null) {
            findViewById(R.id.logout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.logout).setVisibility(View.VISIBLE);
            logout = findViewById(R.id.logout);
            logout.setOnClickListener(view13 -> {
                UserData.clearUser(this);
                ActivityCompat.finishAffinity(this);
                startActivity(new Intent(this, HomeActivity.class));
            });
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        changeLanguage.setOnClickListener(view1 -> {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            fragmentTransaction.replace(android.R.id.content, new LangFragment());
            fragmentTransaction.commit();
        });

        changeAccount.setOnClickListener(view12 -> {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            fragmentTransaction.replace(android.R.id.content, new LoginFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_call:
                startActivity(new Intent(this, FaqActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void launchLanding() {
        ActivityCompat.finishAffinity(this);
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void launchLogin() {

    }

    @Override
    public void launchLoginMenu() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, LoginMenuFragment.newInstance());
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.commit();
    }

    @Override
    public void launchLanguage() {

    }

    @Override
    public void requestRestartActivity() {

    }

    @Override
    public void launchIntro() {

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
}
