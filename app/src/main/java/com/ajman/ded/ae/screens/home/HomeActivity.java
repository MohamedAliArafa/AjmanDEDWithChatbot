package com.ajman.ded.ae.screens.home;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;


public class HomeActivity extends BaseActivity implements Bindable {

    private static Context context;
    private ImageView mLogo;
    private AppBarLayout mAppBar;
    private boolean restartService = true;
    private String FRAGMENT_TAG_DASHBOARD = "dashboard_key_fragment";
    private String FRAGMENT_TAG_HOME = "home_key_fragment";
    private String FRAGMENT_TAG_LOGIN = "login_key_fragment";
    private String FRAGMENT_TAG_ACCOUNT = "account_key_fragment";
    private Merlin merlin;
    private HomeFragment homeFragment;
    private int param;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

//    public static String getBaseUrl(){
//        return AppPreferenceManager.getString(context, AppPreferenceManager.KEY_IS_BASE_URL, "eservices.ajmanded.ae");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogo = findViewById(R.id.logo);
        mAppBar = findViewById(R.id.appbar);
        HomeActivity.context = this;
        param = getIntent().getIntExtra("param", 0);

        super.merlin.registerConnectable(() -> {
            if (homeFragment == null)
                homeFragment = HomeFragment.newInstance(param);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment);
            fragmentTransaction.commitAllowingStateLoss();
        });

        super.merlin.registerDisconnectable(() -> {
            SweetAlertDialog pDialog = new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText(getString(R.string.no_internet)).setCustomImage(R.drawable.wifi_off);
            pDialog.show();
            pDialog.setCancelable(true);
        });
        super.merlin.registerBindable(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()) {
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText(getString(R.string.no_internet)).setCustomImage(R.drawable.wifi_off);
            pDialog.show();
            pDialog.setCancelable(true);
        }
    }
}
