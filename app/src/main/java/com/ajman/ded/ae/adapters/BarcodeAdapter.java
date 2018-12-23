package com.ajman.ded.ae.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_Mob.ItemOnlineUserAllLicenseMob;
import com.ajman.ded.ae.libs.LocaleManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;

/**
 * Created by mohamed.arafa on 8/28/2017.
 */

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.MyViewHolder> {


    private int mLayout;
    private Context mContext;
    private List<ItemOnlineUserAllLicenseMob> data;

    public BarcodeAdapter(Context context, int layout) {
        mLayout = layout;
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);
        return new MyViewHolder(itemView);
    }

    public void updateData(List<ItemOnlineUserAllLicenseMob> list) {
        data = list;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ARABIC)) {
            holder.mName.setText(data.get(position).getTradeNameAR());
            holder.mLicenceType.setText(data.get(position).getLicenseTypeAr());
            holder.mCompanyType.setText(data.get(position).getLegalFormAr());

        } else {
            holder.mName.setText(data.get(position).getTradeNameEN());
            holder.mLicenceType.setText(data.get(position).getLicenseTypeEn());
            holder.mCompanyType.setText(data.get(position).getLegalFormen());

        }
        holder.mLicenceNo.setText(String.format("%s %s", mContext.getString(R.string.mock_license_no), String.valueOf(data.get(position).getLicenseNumber())));
        holder.mIssueDate.setText(String.format("%s %s", mContext.getString(R.string.mock_issued_at), holder.dateFormat.format(new Date(Long.parseLong(data.get(position).getStartDate().replace("/Date(", "").replace(")/", ""))))));
        holder.mExpireDate.setText(String.format("%s %s", mContext.getString(R.string.mock_expiry_at), holder.dateFormat.format(new Date(Long.parseLong(data.get(position).getEndDate().replace("/Date(", "").replace(")/", ""))))));
        Log.d("DATE IOS", data.get(position).getStartDate().replace("/Date(", "").replace(")/", ""));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDateFormat dateFormat;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.issue_date)
        TextView mIssueDate;
        @BindView(R.id.expire_date)
        TextView mExpireDate;
        @BindView(R.id.licence_no)
        TextView mLicenceNo;
        @BindView(R.id.licence_type)
        TextView mLicenceType;
        @BindView(R.id.company_type)
        TextView mCompanyType;

        @SuppressLint("SimpleDateFormat")
        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            dateFormat = new SimpleDateFormat("MMM d, yyyy");

        }
    }
}
