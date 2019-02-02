package com.ajman.ded.ae.screens.news;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.News;
import com.bumptech.glide.Glide;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ajman.ded.ae.data.ApiBuilder.NEWS_IMAGE_BASE_URL;
import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ENGLISH;

public class NewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.image5)
    ImageView imageView;

    @BindView(R.id.date2)
    TextView date;

    @BindView(R.id.caption2)
    TextView caption;

    @BindView(R.id.details2)
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        News news = getIntent().getParcelableExtra("new_for_details");
        if (news != null) {
            Glide.with(this).load(NEWS_IMAGE_BASE_URL + news.getNewsImage()).into(imageView);
            date.setText(news.getNewsDate());
            if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ENGLISH)) {
                caption.setText(news.getNewsTitleEn());
                details.setText(news.getNewsDetailsEn());
            } else {
                caption.setText(news.getNewsTitle());
                details.setText(news.getNewsDetails());
            }
        } else {
            Toast.makeText(this, "NO Data!1", Toast.LENGTH_SHORT).show();
        }
    }
}
