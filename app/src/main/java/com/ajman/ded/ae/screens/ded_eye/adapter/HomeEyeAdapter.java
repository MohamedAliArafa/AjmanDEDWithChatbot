package com.ajman.ded.ae.screens.ded_eye.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajman.ded.ae.NotificationsActivity;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.screens.ded_eye.model.ItemHome;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeEyeAdapter extends RecyclerView.Adapter<HomeEyeAdapter.MyViewHolder> {

    private Context mContext;
    private List<ItemHome> data;

    public HomeEyeAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_eye_home, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setData(List<ItemHome> list) {
        data = list;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.count.setText(String.valueOf(data.get(position).getCount()));
        holder.status.setText(data.get(position).getTitle());
        switch (position) {
            case 0:
                holder.status_view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.radius_one_side_closed));
                break;
            case 1:
                holder.status_view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.radius_one_side_ongoing));
                break;
            case 2:
                holder.status_view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.radius_one_side));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.status_view)
        View status_view;
        @BindView(R.id.clicker)
        View clicker;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            clicker.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int status = 3;
            switch (getAdapterPosition()) {
                case 0:
                    status = 1;
                    break;
                case 1:
                    status = 0;
                    break;
                case 2:
                    status = 3;
                    break;
                default:
                    status = 3;
                    break;

            }
            mContext.startActivity(new Intent(mContext, NotificationsActivity.class).putExtra("status", status));
        }
    }
}
