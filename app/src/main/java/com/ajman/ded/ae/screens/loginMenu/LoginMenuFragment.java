package com.ajman.ded.ae.screens.loginMenu;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.ajman.ded.ae.screens.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginMenuFragment extends Fragment {

    @BindView(R.id.btn_login)
    Button mLoginButton;

    @BindView(R.id.btn_guest)
    Button mGuestButton;

    @BindView(R.id.lang_container_ll)
    LinearLayout mLangContainer;

    @BindView(R.id.iv_logo)
    ImageView mLogoImage;

    @BindView(R.id.iv_logo_colored)
    ImageView mLogoColoredImage;

    public LoginMenuFragment() {
        // Required empty public constructor
    }

    public static LoginMenuFragment newInstance() {
        return new LoginMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_menu, container, false);
        ButterKnife.bind(this, view);
        Animation slideInUpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_up);
        Animation slideInRightAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        Animation slideInDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_down);
        mLangContainer.startAnimation(slideInRightAnimation);
        mLogoColoredImage.startAnimation(slideInUpAnimation);
        mLogoImage.startAnimation(slideInDownAnimation);
        mGuestButton.setOnClickListener(view1 -> {
            getActivity().startActivity(new Intent(getContext(), HomeActivity.class));
            getActivity().finish();
        });
        mLoginButton.setOnClickListener(view1 -> {
            getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
            //TODo Clear Login
        });
        return view;
    }

}
