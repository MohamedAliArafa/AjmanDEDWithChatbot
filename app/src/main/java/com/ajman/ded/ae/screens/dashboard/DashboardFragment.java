package com.ajman.ded.ae.screens.dashboard;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.RtlViewPager;
import com.ajman.ded.ae.ViewDialog;
import com.ajman.ded.ae.libs.MyPagerAdapter;
import com.ajman.ded.ae.utility.AppPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardFragment extends Fragment implements DashInterface {

    @BindView(R.id.vp_dashboard)
    RtlViewPager vpPager;

    List<Fragment> mFragments;
    String[] fragmentsTitles;
    PagerAdapter adapterViewPager;
    private Dialog dialog;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragments = new ArrayList<>();
        mFragments.add(new ApplicationPagerFragment());
        mFragments.add(new LicensePagerFragment());
        mFragments.add(new PermitPagerFragment());
        fragmentsTitles = new String[]{context.getString(R.string.online_services),
                context.getString(R.string.about_ded),
                context.getString(R.string.latest_news)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapterViewPager = new MyPagerAdapter(mFragments, fragmentsTitles, getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        if (!AppPreferenceManager.getBool(getContext(), AppPreferenceManager.KEY_IS_FIRST_SHAKE, false)) {
            dialog = new ViewDialog().showShakeDialog(getActivity());
            AppPreferenceManager.putBool(getContext(), AppPreferenceManager.KEY_IS_FIRST_SHAKE, true);
        }
    }

    @Override
    public void moveNext() {
        if (vpPager.getCurrentItem() < 2)
            vpPager.setCurrentItem(vpPager.getCurrentItem() + 1);
    }

    @Override
    public void movePrev() {
        if (vpPager.getCurrentItem() > 0)
            vpPager.setCurrentItem(vpPager.getCurrentItem() - 1);
    }

    @Override
    public void onDestroyView() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        super.onDestroyView();
    }
}
