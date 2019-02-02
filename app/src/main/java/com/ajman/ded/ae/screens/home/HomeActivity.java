package com.ajman.ded.ae.screens.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.google.android.material.appbar.AppBarLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class HomeActivity extends BaseActivity {
    private boolean isLoaded = false;
    private HomeFragment homeFragment;
    private int param;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView mLogo = findViewById(R.id.logo);
        AppBarLayout mAppBar = findViewById(R.id.appbar);
        param = getIntent().getIntExtra("param", 0);
    }


    @Override
    public void triggerByInternet() {
        if (!isLoaded) {
            if (homeFragment == null)
                homeFragment = HomeFragment.newInstance(param);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        isLoaded = true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
                SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getString(R.string.no_notification));
                pDialog.show();
                pDialog.setCancelable(true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            ActivityCompat.finishAffinity(this);
    }
}
