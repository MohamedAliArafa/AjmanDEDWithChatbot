package com.ajman.ded.ae.screens.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ajman.ded.ae.faetures.chatbot.FaqActivity;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.base.BaseActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class DashBoardActivity extends BaseActivity {

    private DashboardFragment dashboardFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_dash_board;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void triggerByInternet() {
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
}
