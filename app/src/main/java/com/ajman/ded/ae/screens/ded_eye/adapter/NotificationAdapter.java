package com.ajman.ded.ae.screens.ded_eye.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajman.ded.ae.NotificationDetailsActivity;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.models.notification.ResponseContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>implements Filterable {

    private final Context mContext;
    Resources resources;
    private String title;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            ResponseContent model = mList.get(position - 1);
            holder.requestNo.setText(model.getRequestNumber());
            if (model.getRequestDate() != null) {
                holder.date.setText(holder.sdft.format(model.getRequestDate()));
                holder.date2.setText(holder.sdfc.format(model.getRequestDate()));
            }

            if (model.getIsClosed() != null && model.getIsClosed().equals("true")) {
                holder.statusView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.closed));
                holder.statusIcon.setImageResource(R.drawable.closed);
                holder.status.setText(mContext.getString(R.string.closed));
                holder.status.setTextColor(mContext.getResources().getColor(R.color.closed));
            } else {
                holder.statusView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.ongoing));
                holder.statusIcon.setImageResource(R.drawable.ongoing);
                holder.status.setText(mContext.getString(R.string.ongoing));
                holder.status.setTextColor(mContext.getResources().getColor(R.color.ongoing));
            }

            switch (model.getPeriodInDays()) {
                case "1":
                    holder.statusTime.setText("يوم عمل");
                    break;
                case "2":
                    holder.statusTime.setText("يومين عمل");
                    break;
                case "3":
                    holder.statusTime.setText("يوم عمل");
                    break;
                default:
                    holder.statusTime.setText(model.getPeriodInDays() + "يوم عمل");
                    break;
            }

            holder.attachment.setText(model.getAttachmentsCount());
            holder.title.setText(model.getEstablishmentNameAR());
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    results.count = mList.size();
                    results.values = mList;
                } else {
                    List<ResponseContent> filteredList = new ArrayList<>();
                    for (ResponseContent row : mList) {
                        if (row.getEstablishmentNameAR().toLowerCase().startsWith(charString) || row.getEstablishmentNameEN().toLowerCase().startsWith(charString) || row.getRequestNumber().startsWith(charString)) {
                            filteredList.add(row);
                        }
                    }
                    results.count = filteredList.size();
                    results.values = filteredList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                setData((List<ResponseContent>) filterResults.values);
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.request_no)
        TextView requestNo;
        @BindView(R.id.attachment)
        TextView attachment;
        @BindView(R.id.status_time)
        TextView statusTime;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.status_icon)
        ImageView statusIcon;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.date2)
        TextView date2;
        @BindView(R.id.status_view)
        View statusView;
        @BindView(R.id.clicker)
        View clicker;
        SimpleDateFormat sdft;
        SimpleDateFormat sdfc;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            sdft = new SimpleDateFormat("yyyy/MM/dd");
            sdfc = new SimpleDateFormat("hh:mm a");
            clicker.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext, NotificationDetailsActivity.class).putExtra("id", mList.get(getAdapterPosition()).getId()));
        }
    }
}
