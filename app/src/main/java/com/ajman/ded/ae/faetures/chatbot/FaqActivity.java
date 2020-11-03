package com.ajman.ded.ae.faetures.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.faetures.chatbot.ChatSplash.Message;
import com.ajman.ded.ae.faetures.chatbot.ChatSplash.Option;
import com.ajman.ded.ae.faetures.chatbot.ChatSplash.ResultModel;
import com.ajman.ded.ae.faetures.chatbot.ChatSplash.SplashChatAdapter;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class FaqActivity extends BaseActivity {

    RecyclerView rv;
    FlexboxLayout flexboxLayout;
    SplashChatAdapter adapter;
    ResultModel[] mModel;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_faq;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rv = findViewById(R.id.recycler_view);
        flexboxLayout = findViewById(R.id.option_container);
        adapter = new SplashChatAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new SlideInUpAnimator());
        rv.setAdapter(adapter);

        InputStream raw = getResources().openRawResource(R.raw.chat_json);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        mModel = gson.fromJson(rd, ResultModel[].class);
        loadModel(0, false);


    }

    @Override
    public void triggerByInternet() {

    }

    void loadModel(int modelID, boolean replay) {

        if (modelID == -1 || modelID == -2) {
            startActivity(new Intent(this, HomeActivity.class).putExtra("param", modelID));
            return;
        }

        Handler handler = new Handler();
        final List<Message> messages = mModel[modelID].getMessages();
        if (!replay) {
            for (int i = 0; i < messages.size() * 2; i++) {
                final int finalI = i;
                handler.postDelayed(() -> {
                    if (finalI % 2 == 0) {
                        adapter.add(adapter.getItemCount(), "...", null, false);
                    } else {
                        adapter.delete(adapter.getItemCount() - 1);
                        adapter.add(adapter.getItemCount(), messages.get(finalI / 2).getText(), null, false);
                    }
                    rv.scrollToPosition(adapter.getItemCount() - 1);
                }, 1000 * i);
            }
        }
        List<Option> options = mModel[modelID].getOptions();
        List<Message> replies = mModel[modelID].getReplies();
        final TextView[] textViews = new TextView[options.size()];
        for (int i = 0; i < options.size(); i++) {
            TextView newTextView = new TextView(this);
            newTextView.setText(options.get(i).getMessage());
            newTextView.setId(View.generateViewId());
            newTextView.setLayoutParams(new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT));
            if (Objects.equals(LocaleManager.getLanguage(this), LocaleManager.LANGUAGE_ARABIC))
                newTextView.setBackgroundResource(R.drawable.bg_rounded_right);
            else newTextView.setBackgroundResource(R.drawable.bg_rounded_left);

            newTextView.setTextColor(getResources().getColor(android.R.color.black));
            float scale = getResources().getDisplayMetrics().density;
            int dp = (int) (10 * scale + 0.5f);
            newTextView.setPadding(dp, dp, dp, dp);

            flexboxLayout.setFlexDirection(FlexDirection.ROW);
            FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) newTextView.getLayoutParams();
            lp.setMargins(dp, dp / 2, dp, dp / 2);
            newTextView.setLayoutParams(lp);
            final int finalI = i;
            newTextView.setOnClickListener(view -> {
                adapter.add(adapter.getItemCount(), options.get(finalI).getMessage(), null, true);
                rv.scrollToPosition(adapter.getItemCount() - 1);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fad_in_out);
                view.startAnimation(animation);
                flexboxLayout.removeAllViews();
                if (options.get(finalI).getCode() == 0)
                    loadModel(options.get(finalI).getCode(), true);
                else
                    loadModel(options.get(finalI).getCode(), false);
            });

            textViews[i] = newTextView;
        }

        if (replay) {
            handler.postDelayed(() -> {
                for (int i = 0; i < options.size(); i++) {
                    flexboxLayout.addView(textViews[i]);
                }
            }, 1000);
        } else {
            handler.postDelayed(() -> {
                for (int i = 0; i < options.size(); i++) {
                    flexboxLayout.addView(textViews[i]);
                }
            }, 1000 * messages.size() * 2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        return true;
    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_call:
//                startActivity(new Intent(DashBoardActivity.this, FaqActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
