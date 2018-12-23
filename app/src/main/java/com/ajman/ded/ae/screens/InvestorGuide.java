package com.ajman.ded.ae.screens;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.utility.pdfViewer.adapter.BasePDFPagerAdapter;
import com.ajman.ded.ae.utility.pdfViewer.adapter.PDFViewPager;


public class InvestorGuide extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String STATE_CURRENT_PAGE_INDEX = "current_page";
    private PDFViewPager pdfViewPager;
    private int mCurrentPage = 0;
    private ImageView next;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_investor_guide;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pdfViewPager = findViewById(R.id.pdfViewPager);
        pdfViewPager.addOnPageChangeListener(this);
        next = findViewById(R.id.next);
        next.setOnClickListener(this);
        findViewById(R.id.previous).setOnClickListener(this);
    }


    @Override
    public void onDestroy() {
        ((BasePDFPagerAdapter) pdfViewPager.getAdapter()).close();

        super.onDestroy();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCurrentPage != 0) {
            outState.putInt(STATE_CURRENT_PAGE_INDEX, mCurrentPage);
        }
    }


    @Override
    public void onClick(View view) {
        mCurrentPage = pdfViewPager.getCurrentItem();
        switch (view.getId()) {
            case R.id.next:
                pdfViewPager.setCurrentItem(mCurrentPage + 1);
                break;
            case R.id.previous:
                pdfViewPager.setCurrentItem(mCurrentPage - 1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = pdfViewPager.getCurrentItem();
        if (mCurrentPage <= 1)
            findViewById(R.id.previous).setVisibility(View.GONE);
        else
            findViewById(R.id.previous).setVisibility(View.VISIBLE);

        if (mCurrentPage == pdfViewPager.getAdapter().getCount() - 2)
            findViewById(R.id.next).setVisibility(View.GONE);
        else
            findViewById(R.id.next).setVisibility(View.VISIBLE);


    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
