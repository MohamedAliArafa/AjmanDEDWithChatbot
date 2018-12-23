package com.ajman.ded.ae.screens.language;

import android.content.Context;

import com.ajman.ded.ae.MyApplication;
import com.ajman.ded.ae.libs.LocaleManager;


/**
 * Created by root on 10/2/17.
 */

public class LangPresenter implements LangContract.UserActions {

    Context mContext;
    MyApplication mApplication;

    public LangPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void chooseArabic() {
        LocaleManager.setNewLocale(mContext, LocaleManager.LANGUAGE_ARABIC);
    }

    @Override
    public void chooseEnglish() {
        LocaleManager.setNewLocale(mContext, LocaleManager.LANGUAGE_ENGLISH);
    }
}
