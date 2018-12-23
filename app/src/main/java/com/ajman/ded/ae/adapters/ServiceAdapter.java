package com.ajman.ded.ae.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.WebViewActivity;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.ServiceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;
import static com.ajman.ded.ae.utility.Constants.URL_INTENT_KEY;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_AR;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_EN;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private final Context mContext;
    Resources resources;
    private List<ServiceModel> mList = new ArrayList<>();
    private int mLayout;

    public ServiceAdapter(Context context, List<ServiceModel> list, int layout) {
        mContext = context;
        mList = list;
        mLayout = layout;
        resources = context.getResources();
    }

    public void updateData(List<ServiceModel> list) {
        mList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ServiceModel model = mList.get(position);
        if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ARABIC))
            holder.mTitleTextView.setText(model.getNameAr());
        else
            holder.mTitleTextView.setText(model.getName());
        switch (model.getColor()) {
            case 1:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_dark_beige);
                break;
            case 2:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_dark_blue);
                break;
            case 3:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_light_red);
                break;
            case 4:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_light_blue);
                break;
            case 5:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_light_brown);
                break;


            case 6:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_light_brown);
                break;
            case 7:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_light_royal);
                break;
            case 8:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_dark_brown);
                break;
            case 9:
                holder.mImage.setBackgroundResource(R.drawable.bg_shape_light_royal);
                break;
        }

        try {
            final int resourceId = resources.getIdentifier(model.getIcon(), "drawable",
                    mContext.getPackageName());
            holder.mIcon.setImageResource(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(view -> {
            Intent in = new Intent(mContext, WebViewActivity.class);
            if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ARABIC))
                in.putExtra(URL_INTENT_KEY, DOMAIN_AR + model.getLink());
            else
                in.putExtra(URL_INTENT_KEY, DOMAIN_EN + model.getLink());
            mContext.startActivity(in);
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.service_name_tv)
        TextView mTitleTextView;

        @BindView(R.id.service_image)
        ImageView mImage;

        @BindView(R.id.service_icon)
        ImageView mIcon;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
