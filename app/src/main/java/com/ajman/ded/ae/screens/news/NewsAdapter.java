package com.ajman.ded.ae.screens.news;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.News;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ajman.ded.ae.data.ApiBuilder.NEWS_IMAGE_BASE_URL;
import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ENGLISH;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    private Context context;
    private List<News> list;

    NewsAdapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    void updateData(List<News> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(NEWS_IMAGE_BASE_URL + list.get(position).getNewsImage()).into(holder.imageView);
        holder.date.setText(list.get(position).getNewsDate());
        if (Objects.equals(LocaleManager.getLanguage(context), LANGUAGE_ENGLISH)) {
            holder.caption.setText(list.get(position).getNewsTitleEn());
            holder.details.setText(list.get(position).getNewsDetailsEn());
        } else {
            holder.caption.setText(list.get(position).getNewsTitle());
            holder.details.setText(list.get(position).getNewsDetails());
        }

        holder.itemView.setOnClickListener(view -> {
            Intent in = new Intent(context, NewsDetailActivity.class);
            in.putExtra("new_for_details", list.get(position));
            context.startActivity(in);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dot_1)
        ImageView imageView;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.caption)
        TextView caption;

        @BindView(R.id.details)
        TextView details;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
