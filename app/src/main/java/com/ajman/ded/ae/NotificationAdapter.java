package com.ajman.ded.ae;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajman.ded.ae.models.notification.ResponseContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    Resources resources;
    private String title;
    private List<ResponseContent> mList;

    public NotificationAdapter(Context context, String string) {
        mContext = context;
        resources = context.getResources();
        this.title = string;
        this.mList = new ArrayList<>();
    }

    public void setData(List<ResponseContent> list) {
        mList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_notification, parent, false);
            return new MyHeaderViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new MyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position == 0) {
            MyHeaderViewHolder holder = (MyHeaderViewHolder) viewHolder;
            holder.title.setText(title);
        } else {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            ResponseContent model = mList.get(position - 1);
            holder.requestNo.setText(model.getRequestNumber());
            if (model.getRequestDate() != null)
                holder.date.setText(holder.simpleDateFormat.format(model.getRequestDate()));
            holder.desc.setText(model.getEstablishmentNameAR());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else return position;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.request_no)
        TextView requestNo;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.clicker)
        View clicker;
        SimpleDateFormat simpleDateFormat;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd  hh:mm a");
            clicker.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext, NotificationDetailsActivity.class).putExtra("id", mList.get(getAdapterPosition() - 1).getID()));
        }
    }

    class MyHeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        MyHeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
