package com.ajman.ded.ae.screens.home;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ajman.ded.ae.faetures.chatbot.ChatActivity;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.google.android.material.appbar.AppBarLayout;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends BaseActivity {
    private boolean isLoaded = false;
    private HomeFragment homeFragment;
    private int param;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    int mPlayerLength = 0;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CircleImageView assistant_avatar = findViewById(R.id.assistant_image);
        AppCompatTextView assistant_text_bubble = findViewById(R.id.assistant_text_bubble);

        Uri uriaudio= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        player = MediaPlayer.create(HomeActivity.this, uriaudio);
        player.setLooping(false); // Set looping
        player.setVolume(100,100);

        ImageView mLogo = findViewById(R.id.logo);
        AppBarLayout mAppBar = findViewById(R.id.appbar);
        param = getIntent().getIntExtra("param", 0);
        assistant_avatar.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ChatActivity.class));
        });
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            // Do something after 5s = 5000ms
            Animation expandIn = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.expand_in);
            assistant_avatar.startAnimation(expandIn);
            expandIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    player.seekTo(0);
                    player.start();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    assistant_text_bubble.startAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.expand_in));
                    assistant_text_bubble.setVisibility(View.VISIBLE);
                    if(player.isPlaying()){
                        player.stop();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            assistant_avatar.setVisibility(View.VISIBLE);
        }, 2000);
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
