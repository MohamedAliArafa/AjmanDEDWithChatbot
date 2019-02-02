package com.ajman.ded.ae.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.ajman.ded.ae.screens.login.LoginActivity;
import com.ajman.ded.ae.screens.loginMenu.LoginMenuFragment;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by root on 9/29/17.
 */

public class LoginPagerAdapter extends PagerAdapter {
    private LoginMenuFragment mContext;
    private FragmentManager mFragmentManager;

    public LoginPagerAdapter(LoginMenuFragment context, FragmentManager fragmentManager) {
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext.getContext());
        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
        layout.findViewById(R.id.lang_btn).setOnClickListener(view -> {
            switch (position) {
                case 0:
                    mContext.getActivity().startActivity(new Intent(mContext.getActivity(), HomeActivity.class));
                    break;
                case 1:
                    mContext.getActivity().startActivity(new Intent(mContext.getActivity(), LoginActivity.class));
                    break;
                case 2:
                    mContext.getActivity().startActivity(new Intent(mContext.getActivity(), LoginActivity.class));
                    break;
            }
            mContext.getActivity().finish();
        });
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return CustomPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

    enum CustomPagerEnum {

        GUEST(R.string.continue_guest, R.layout.login_guest_view_item),
        REGISTER(R.string.continue_register, R.layout.login_register_view_item),
        LOGIN(R.string.continue_login, R.layout.login_login_view_item);

        private int mTitleResId;
        private int mLayoutResId;

        CustomPagerEnum(int titleResId, int layoutResId) {
            mTitleResId = titleResId;
            mLayoutResId = layoutResId;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }

    }


}
