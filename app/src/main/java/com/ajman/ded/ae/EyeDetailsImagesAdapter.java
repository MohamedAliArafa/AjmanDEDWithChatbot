package com.ajman.ded.ae;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
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

public class EyeDetailsImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    Resources resources;
    private List<Bitmap> mList;
    private AdapterCallback adapterCallback;

    public EyeDetailsImagesAdapter(Context context, AdapterCallback adapterCallback) {
        mContext = context;
        resources = context.getResources();
        this.mList = new ArrayList<>();
        this.adapterCallback = adapterCallback;
    }


    public void setImage(List<Bitmap> list) {
        mList.clear();
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addImage(Bitmap image) {
        mList.add(image);
        this.notifyDataSetChanged();
    }

    public interface AdapterCallback {
        void onOpenCallback(Bitmap integer);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eye_details_image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        Bitmap model = mList.get(position);
        Glide.with(viewHolder.mImage.getContext())
                .load(model)
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.mImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image)
        ImageView mImage;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterCallback.onOpenCallback(mList.get(getAdapterPosition()));
        }
    }
}
