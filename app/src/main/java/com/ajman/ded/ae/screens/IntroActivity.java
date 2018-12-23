package com.ajman.ded.ae.screens;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.app.TaskStackBuilder;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.screens.dashboard.DashBoardActivity;
import com.ajman.ded.ae.utility.AppPreferenceManager;
import com.ajman.ded.ae.utility.SampleSlide;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.github.paolorotolo.appintro.AppIntro2;

import java.util.Objects;


public class IntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Objects.equals(LocaleManager.getLanguage(this), LocaleManager.LANGUAGE_ARABIC)) {
            addSlide(SampleSlide.newInstance(R.drawable.ar_one));
            addSlide(SampleSlide.newInstance(R.drawable.ar_two));
            addSlide(SampleSlide.newInstance(R.drawable.ar_three));
            addSlide(SampleSlide.newInstance(R.drawable.ar_four));
            addSlide(SampleSlide.newInstance(R.drawable.ar_five));
        } else {
            addSlide(SampleSlide.newInstance(R.drawable.en_one));
            addSlide(SampleSlide.newInstance(R.drawable.en_two));
            addSlide(SampleSlide.newInstance(R.drawable.en_three));
            addSlide(SampleSlide.newInstance(R.drawable.en_four));
            addSlide(SampleSlide.newInstance(R.drawable.en_five));
        }
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        if (isLoggedIn()) {
            if (UserData.getUserObject(this) != null) {
                AppPreferenceManager.putBool(this, AppPreferenceManager.KEY_IS_JUST_LOGIN, false);
                ActivityCompat.finishAffinity(this);
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(new Intent(this, DashBoardActivity.class))
                        .startActivities();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        if (isLoggedIn()) {
            if (UserData.getUserObject(this) != null) {
                AppPreferenceManager.putBool(this, AppPreferenceManager.KEY_IS_JUST_LOGIN, false);
                ActivityCompat.finishAffinity(this);
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(new Intent(this, DashBoardActivity.class))
                        .startActivities();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    public boolean isLoggedIn() {
        return AppPreferenceManager.getBool(this, AppPreferenceManager.KEY_IS_JUST_LOGIN, true);
    }
}
