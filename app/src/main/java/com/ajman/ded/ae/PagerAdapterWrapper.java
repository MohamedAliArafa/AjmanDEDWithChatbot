package com.ajman.ded.ae;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

class PagerAdapterWrapper extends PagerAdapter {
    @NonNull
    private final PagerAdapter adapter;

    protected PagerAdapterWrapper(@NonNull PagerAdapter adapter) {
        this.adapter = adapter;
    }

    @NonNull
    public PagerAdapter getInnerAdapter() {
        return this.adapter;
    }

    public int getCount() {
        return this.adapter.getCount();
    }

    public boolean isViewFromObject(View view, Object object) {
        return this.adapter.isViewFromObject(view, object);
    }

    public CharSequence getPageTitle(int position) {
        return this.adapter.getPageTitle(position);
    }

    public float getPageWidth(int position) {
        return this.adapter.getPageWidth(position);
    }

    public int getItemPosition(Object object) {
        return this.adapter.getItemPosition(object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        return this.adapter.instantiateItem(container, position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        this.adapter.destroyItem(container, position, object);
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        this.adapter.setPrimaryItem(container, position, object);
    }

    public void notifyDataSetChanged() {
        this.adapter.notifyDataSetChanged();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        this.adapter.registerDataSetObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.adapter.unregisterDataSetObserver(observer);
    }

    public Parcelable saveState() {
        return this.adapter.saveState();
    }

    public void restoreState(Parcelable state, ClassLoader loader) {
        this.adapter.restoreState(state, loader);
    }

    public void startUpdate(ViewGroup container) {
        this.adapter.startUpdate(container);
    }

    public void finishUpdate(ViewGroup container) {
        this.adapter.finishUpdate(container);
    }
}
