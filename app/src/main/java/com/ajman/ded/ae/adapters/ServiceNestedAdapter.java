package com.ajman.ded.ae.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.ServiceModuleModel;
import com.ajman.ded.ae.utility.Helper;
import com.ajman.ded.ae.utility.SharedTool.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;

public class ServiceNestedAdapter extends RecyclerView.Adapter<ServiceNestedAdapter.MyViewHolder> {

    private final Context mContext;
    private List<ServiceModuleModel> mList = new ArrayList<>();
    private int mLayout;

    public ServiceNestedAdapter(Context context, List<ServiceModuleModel> list, int layout) {
        mContext = context;
        mList = list;
        mLayout = layout;
    }

    public void updateData(List<ServiceModuleModel> list) {
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
        if (UserData.getUserObject(mContext) != null) {
            ServiceModuleModel model = mList.get(position);
            ServiceAdapter adapter = new ServiceAdapter(mContext, model.getList(), R.layout.list_item_service_child);
            int spanCount = Helper.calculateNoOfColumns(mContext, 75);
            holder.mRecycler.setLayoutManager(new GridLayoutManager(mContext, spanCount));
            holder.mRecycler.setAdapter(adapter);
            if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ARABIC))
                holder.mTitle.setText(model.getNameAr());
            else
                holder.mTitle.setText(model.getName());
        } else {
            ServiceModuleModel model = mList.get(4);
            ServiceAdapter adapter = new ServiceAdapter(mContext, model.getList(), R.layout.list_item_service_child);
            int spanCount = Helper.calculateNoOfColumns(mContext, 75);
            holder.mRecycler.setLayoutManager(new GridLayoutManager(mContext, spanCount));
            holder.mRecycler.setAdapter(adapter);
            if (Objects.equals(LocaleManager.getLanguage(mContext), LANGUAGE_ARABIC))
                holder.mTitle.setText(model.getNameAr());
            else
                holder.mTitle.setText(model.getName());
        }

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            if (UserData.getUserObject(mContext) != null)
                return mList.size();
            else return 1;
        } else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.service_recycler)
        RecyclerView mRecycler;

        @BindView(R.id.service_title_tv)
        TextView mTitle;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
