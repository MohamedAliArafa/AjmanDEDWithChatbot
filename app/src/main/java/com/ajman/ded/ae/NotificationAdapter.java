package com.ajman.ded.ae;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajman.ded.ae.models.notification.ResponseContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private final Context mContext;
    Resources resources;
    private List<ResponseContent> mList;

    public NotificationAdapter(Context context) {
        mContext = context;
        resources = context.getResources();
        this.mList = new ArrayList<>();
    }

    public void setData(List<ResponseContent> list) {
        mList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ResponseContent model = mList.get(position);
        holder.requestNo.setText(model.getRequestNumber());
        if (model.getRequestDate() != null)
            holder.date.setText(holder.simpleDateFormat.format(model.getRequestDate()));
        holder.desc.setText(model.getEstablishmentNameAR());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.request_no)
        TextView requestNo;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.desc)
        TextView desc;
        SimpleDateFormat simpleDateFormat;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd - hh:mm a");
        }
    }
}
