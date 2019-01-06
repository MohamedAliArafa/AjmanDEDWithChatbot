package com.ajman.ded.ae;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class EyeImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    Resources resources;
    private List<String> mList;
    private AdapterCallback adapterCallback;

    public EyeImagesAdapter(Context context, AdapterCallback adapterCallback) {
        mContext = context;
        resources = context.getResources();
        this.mList = new ArrayList<>();
        this.adapterCallback = adapterCallback;
    }

    public void addImage(List<String> list) {
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    public void setImage(List<String> list) {
        mList.clear();
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    public interface AdapterCallback {
        void onAddCallback();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eye_image, parent, false);
            return new MyViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eye_add, parent, false);
            return new MyAddViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            String model = mList.get(position);
            Glide.with(viewHolder.mImage.getContext())
                    .load(model)
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.mImage);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            if (mList.size() >= 6) {
                return 6;
            } else {
                return mList.size() + 1;
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() > 0) {
            if (mList.size() >= 6) {
                return 0;
            } else {
                if (position == mList.size())
                    return 1;
                else
                    return 0;
            }
        }else {
            return 3;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.dot_1)
        ImageView mImage;
        @BindView(R.id.delete)
        ImageView mDelete;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mList.remove(getAdapterPosition());
            notifyDataSetChanged();
        }
    }

    class MyAddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.add)
        ImageView mAdd;

        MyAddViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterCallback.onAddCallback();
        }
    }
}
