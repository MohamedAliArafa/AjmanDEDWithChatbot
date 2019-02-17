package com.ajman.ded.ae.screens.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.AnimatedFragment;
import com.ajman.ded.ae.libs.MyPagerAdapter;
import com.rd.PageIndicatorView;
import com.rd.draw.data.RtlMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    FragmentPagerAdapter adapterViewPager;
    List<Fragment> mFragments;
    String[] fragmentsTitles;
    @BindView(R.id.pager)
    ViewPager vpPager;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView mPageIndicatorView;
    private int mParam;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(int param) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM, 0);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragments = new ArrayList<>();
        mFragments.add(new HomeTabFragment());
        mFragments.add(new AboutDEDFragment());
        mFragments.add(new ServicesFragment());
        fragmentsTitles = new String[]{context.getString(R.string.online_services),
                context.getString(R.string.about_ded),
                context.getString(R.string.latest_news)}
        ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        //init and set the adapter
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapterViewPager = new MyPagerAdapter(mFragments, fragmentsTitles, getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOffscreenPageLimit(mFragments.size());
        if (mParam == -2) {
            vpPager.setCurrentItem(2, true);
        }
        mPageIndicatorView.setViewPager(vpPager);
        mPageIndicatorView.setRtlMode(RtlMode.Auto);
        // make the pager RTL by calling the last fragment in list
//        vpPager.setCurrentItem(mFragments.size() - 1);

        // Attach the page change listener inside the activity
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                if (!(mFragments.get(position) instanceof ServicesFragment))
                    ((AnimatedFragment) mFragments.get(position)).startAnimation();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }
}
