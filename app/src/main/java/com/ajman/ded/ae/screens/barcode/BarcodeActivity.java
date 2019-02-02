package com.ajman.ded.ae.screens.barcode;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.base.BaseActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class BarcodeActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_barcode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new BarcodeFragment());
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.commit();
    }

    @Override
    public void triggerByInternet() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        return true;
    }
}
