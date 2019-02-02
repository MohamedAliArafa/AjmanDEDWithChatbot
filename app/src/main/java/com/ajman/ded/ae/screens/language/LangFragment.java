package com.ajman.ded.ae.screens.language;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.screens.main.MainContract;
import com.ajman.ded.ae.utility.AppPreferenceManager;
import com.ajman.ded.ae.utility.SharedTool.UserData;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LangFragment extends Fragment {

    @BindView(R.id.btn_arabic)
    Button mArabicButton;

    @BindView(R.id.btn_english)
    Button mEnglishButton;

    @BindView(R.id.lang_container_ll)
    LinearLayout mLangContainer;

    @BindView(R.id.iv_logo)
    ImageView mLogoImage;

    @BindView(R.id.iv_logo_colored)
    ImageView mLogoColoredImage;
    LangContract.UserActions mPresenter;
    private MainContract.ModelView mListener;

    public LangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LangPresenter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lang_menu, container, false);
        ButterKnife.bind(this, view);
        Animation slideInUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_up);
        Animation slideInRightAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
        Animation slideInDownAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_down);
        mLangContainer.startAnimation(slideInRightAnimation);
        mLogoColoredImage.startAnimation(slideInUpAnimation);
        mLogoImage.startAnimation(slideInDownAnimation);
        mArabicButton.setOnClickListener(v -> {
            if (!Objects.equals(LocaleManager.getLanguage(getActivity()), LocaleManager.LANGUAGE_ARABIC)) {
                LocaleManager.setNewLocale(getContext(), LocaleManager.LANGUAGE_ARABIC, true);
                UserData.saveLocalization(getActivity(), "ar");
            }
            if (UserData.getUserObject(getContext()) != null) {
                mListener.launchLanding();
            } else {
                if (isFirstLaunch()) {
                    AppPreferenceManager.putBool(getContext(), AppPreferenceManager.KEY_IS_FIRST_TIME, false);
                    mListener.requestRestartActivity();
                } else {
                    mListener.launchLoginMenu();
                }
            }
        });

        mEnglishButton.setOnClickListener(v -> {
            if (!Objects.equals(LocaleManager.getLanguage(getActivity()), LocaleManager.LANGUAGE_ENGLISH)) {
                LocaleManager.setNewLocale(getActivity(), LocaleManager.LANGUAGE_ENGLISH, true);
                UserData.saveLocalization(getActivity(), "en");
            }
            if (UserData.getUserObject(getActivity()) != null) {
                mListener.launchLanding();
            } else {
                if (isFirstLaunch()) {
                    AppPreferenceManager.putBool(getContext(), AppPreferenceManager.KEY_IS_FIRST_TIME, false);
                    mListener.requestRestartActivity();
                } else {
                    mListener.launchLoginMenu();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListener == null)
            mListener = (MainContract.ModelView) getActivity();
    }

    public boolean isFirstLaunch() {
        return AppPreferenceManager.getBool(getContext(), AppPreferenceManager.KEY_IS_FIRST_TIME, true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainContract.ModelView) {
            mListener = (MainContract.ModelView) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
