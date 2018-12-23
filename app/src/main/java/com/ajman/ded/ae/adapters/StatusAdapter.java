package com.ajman.ded.ae.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.model.response.GetRequestStatus.Item_GetRequestStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed on 12/1/2017.
 */

public class StatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final int TYPE_WHITE = 13;
    private final int TYPE_GREY = 14;
    private int mLayout;
    private int VIEW_TYPE_HEADER = 0;
    private int VIEW_TYPE_DEFAULT = 1;
    private int VIEW_TYPE_FOOTER = 2;
    private List<Item_GetRequestStatus> data;

    public StatusAdapter(Context context, List<Item_GetRequestStatus> data) {
        mContext = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_FOOTER) {
            View v1 = inflater.inflate(R.layout.item_status_footer, parent, false);
            viewHolder = new Footer(v1);
        } else {
            View v2 = inflater.inflate(R.layout.item_status, parent, false);
            viewHolder = new StatusAdapter.Default(v2);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0) {
            Item_GetRequestStatus obj = data.get(position - 1);
            if (getItemViewType(position) == VIEW_TYPE_FOOTER) {
                final Footer VH = (Footer) holder;
                if (position % 2 == 0)
                    VH.background.setBackgroundColor(mContext.getResources().getColor(R.color.prim_color));
                VH.end.setText(VH.dateFormat.format(new Date(Long.parseLong(obj.getLastUpdate().replace("/Date(", "").replace(")/", "")))));
                VH.middle.setText(obj.getTaskTitle());
                VH.start.setText(obj.getTaskStatus());
            } else {
                final Default VH = (Default) holder;
                if (position % 2 == 0)
                    VH.background.setBackgroundColor(mContext.getResources().getColor(R.color.prim_color));
                VH.end.setText(VH.dateFormat.format(new Date(Long.parseLong(obj.getLastUpdate().replace("/Date(", "").replace(")/", "")))));
                VH.middle.setText(obj.getTaskTitle());
                VH.start.setText(obj.getTaskStatus());

            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) {
            return VIEW_TYPE_FOOTER;
        } else if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    class Default extends RecyclerView.ViewHolder {
        private final SimpleDateFormat dateFormat;
        @BindView(R.id.text_start)
        TextView start;
        @BindView(R.id.text_middle)
        TextView middle;
        @BindView(R.id.text_end)
        TextView end;
        @BindView(R.id.linearLayout2)
        LinearLayout background;
        SimpleDateFormat userFormat;

        Default(View view) {
            super(view);
            ButterKnife.bind(this, view);
            userFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        }
    }

    class Footer extends RecyclerView.ViewHolder {
        private final SimpleDateFormat dateFormat;
        @BindView(R.id.text_start)
        TextView start;

        @BindView(R.id.text_middle)
        TextView middle;

        @BindView(R.id.text_end)
        TextView end;

        @BindView(R.id.linearLayout2)
        LinearLayout background;

        SimpleDateFormat userFormat;

        Footer(View view) {
            super(view);
            ButterKnife.bind(this, view);
            userFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        }
    }


}
