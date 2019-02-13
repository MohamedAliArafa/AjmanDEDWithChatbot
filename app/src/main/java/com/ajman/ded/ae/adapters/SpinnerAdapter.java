package com.ajman.ded.ae.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.Country;
import com.ajman.ded.ae.models.StockholderType;
import com.ajman.ded.ae.models.notification.tybe.ResponseContent;

import java.util.List;
import java.util.Objects;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ENGLISH;
import static com.ajman.ded.ae.screens.ded_eye.NewNotificationActivity.TYPE_TYPE;
import static com.ajman.ded.ae.screens.registeration.RegisterFragment.TYPE_NATIONALITY;
import static com.ajman.ded.ae.screens.registeration.RegisterFragment.TYPE_STOCK;

/**
 * Created by Ahmed on 11/29/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List items;
    private final int mResource;
    private final int type;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource,
                          @NonNull List objects, int type) {
        super(context, resource, 0, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
        this.type = type;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.row_spinners_dropdown, parent, false);
        TextView tv = (TextView) view;
        switch (type) {
            case TYPE_STOCK:
                if (position == 0) {
                    tv.setText(R.string.select_stock_holder_type);
                } else {
                    StockholderType typeData = (StockholderType) items.get(position - 1);
                    if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ENGLISH))
                        tv.setText(typeData.getNameEN());
                    else
                        tv.setText(typeData.getNameAR());
                }
                break;
            case TYPE_NATIONALITY:
                if (position == 0) {
                    tv.setText(R.string.select_nationality);
                } else {
                    Country typeData = (Country) items.get(position - 1);
                    if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ENGLISH))
                        tv.setText(typeData.getNameEN());
                    else
                        tv.setText(typeData.getNameAR());
                }
                break;
            case TYPE_TYPE:
                if (position == 0) {
                    tv.setText(mContext.getString(R.string.notification_type));
                } else {
                    ResponseContent typeData = (ResponseContent) items.get(position - 1);
                    if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ENGLISH))
                        tv.setText(typeData.getNameEN());
                    else
                        tv.setText(typeData.getNameAR());
                }
                break;
        }
        return view;
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.spinner_row, parent, false);
        TextView tv = (TextView) view;
        switch (type) {
            case TYPE_STOCK:
                if (position == 0) {
                    tv.setText(R.string.select_stock_holder_type);
                } else {
                    StockholderType typeData = (StockholderType) items.get(position - 1);
                    if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ENGLISH))
                        tv.setText(typeData.getNameEN());
                    else
                        tv.setText(typeData.getNameAR());
                }
                break;
            case TYPE_NATIONALITY:
                if (position == 0) {
                    tv.setText(R.string.select_nationality);
                } else {
                    Country typeData = (Country) items.get(position - 1);
                    if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ENGLISH))
                        tv.setText(typeData.getNameEN());
                    else
                        tv.setText(typeData.getNameAR());
                }
                break;
            case TYPE_TYPE:
                if (position == 0) {
                    tv.setText(mContext.getString(R.string.notification_type));
                } else {
                    ResponseContent typeData = (ResponseContent) items.get(position - 1);
                    if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ENGLISH))
                        tv.setText(typeData.getNameEN());
                    else
                        tv.setText(typeData.getNameAR());
                }
                break;
        }
        return view;
    }


    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }
}