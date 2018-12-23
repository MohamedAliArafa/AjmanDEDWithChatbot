package com.ajman.ded.ae.screens.dashboard;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.ajman.ded.ae.FaqActivity;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.NetworkStatus;


public class DashBoardActivity extends BaseActivity implements Bindable {

    private DashboardFragment dashboardFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_dash_board;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        super.merlin.registerBindable(this);
        super.merlin.registerConnectable(() -> {

            if (dashboardFragment == null)
                dashboardFragment = new DashboardFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, dashboardFragment);
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            fragmentTransaction.detach(dashboardFragment);
            fragmentTransaction.attach(dashboardFragment);
            fragmentTransaction.commitAllowingStateLoss();
        });
        super.merlin.registerDisconnectable(() -> {
            SweetAlertDialog pDialog = new SweetAlertDialog(DashBoardActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText(getString(R.string.no_internet)).setCustomImage(R.drawable.wifi_off);
            pDialog.show();
            pDialog.setCancelable(true);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                startActivity(new Intent(DashBoardActivity.this, FaqActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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
