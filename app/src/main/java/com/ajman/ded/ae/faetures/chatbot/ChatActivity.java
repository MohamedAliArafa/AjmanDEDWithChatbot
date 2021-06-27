package com.ajman.ded.ae.faetures.chatbot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.faetures.chatbot.ChatSplash.Message;
import com.ajman.ded.ae.faetures.chatbot.ChatSplash.Option;
import com.ajman.ded.ae.faetures.chatbot.ChatSplash.ResultModel;
import com.ajman.ded.ae.faetures.chatbot.ChatSplash.SplashChatAdapter;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.NotificationResponse;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.security.BasicAuthenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.service.exception.ForbiddenException;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric;
import com.ibm.watson.assistant.v2.model.SessionResponse;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class ChatActivity extends BaseActivity {

    RecyclerView rv;
    EditText messageInput;
    ImageButton sendBtn;
    FlexboxLayout flexboxLayout;
    SplashChatAdapter adapter;
    ResultModel[] mModel;
    String assistant_id;
    Assistant assistant;
    TextToSpeech textToSpeech;
    GetResponseTask myTask;
    GetSynthesizeTask mSynthesizeTask;
    SessionResponse sessionResponse;
    private Api api;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_faq;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Objects.equals(LocaleManager.getLanguage(ChatActivity.this), LocaleManager.LANGUAGE_ARABIC)) {
            assistant_id = "19ecc32c-01dd-45d1-9a41-07659fe6b870";
        } else {
            assistant_id = "a824c2b9-45f4-4713-959c-2685aaaabb02";
        }

        IamAuthenticator assistant_authenticator = new IamAuthenticator("xPUwAeXgLNhD0ALCoHiQ2PwBey8TY6o2u_CJRD-aUM49");
        assistant = new Assistant("2020-04-01", assistant_authenticator);
        assistant.setServiceUrl("https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/2c0adcd6-7937-4827-a1bd-461b9a083760");

        BasicAuthenticator authenticator = new BasicAuthenticator("ghostarafa@gmail.com", "Fantom@2016");

        IamAuthenticator textToSpeech_authenticator = new IamAuthenticator("uc6sQh4pHG6rbPVCKC6ddAuApgHOgVpYB-UYBrKGFMVl");
        textToSpeech = new TextToSpeech(textToSpeech_authenticator);
        textToSpeech.setServiceUrl("https://api.eu-gb.text-to-speech.watson.cloud.ibm.com/instances/ee39ecb8-469f-43c2-9d1e-ca3a77a43d50");

        api = ApiBuilder.arafaApi();

        messageInput = findViewById(R.id.editTextView3);
        sendBtn = findViewById(R.id.send_btn);

        rv = findViewById(R.id.recycler_view);
        flexboxLayout = findViewById(R.id.option_container);
        adapter = new SplashChatAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new SlideInUpAnimator());
        rv.setAdapter(adapter);

        myTask = new GetResponseTask();
        if (Objects.equals(LocaleManager.getLanguage(ChatActivity.this), LocaleManager.LANGUAGE_ARABIC)) {
            myTask.execute("مرحب");
        } else {
            myTask.execute("Hello");
        }

        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //do here your stuff f
                if (!messageInput.getText().toString().isEmpty()) {
                    adapter.add(adapter.getItemCount(), messageInput.getText().toString(), null, true);
                    rv.scrollToPosition(adapter.getItemCount() - 1);
                    if (myTask.getStatus() != AsyncTask.Status.FINISHED) {
                        myTask.cancel(true);
                        if (mSynthesizeTask != null && mSynthesizeTask.getStatus() != AsyncTask.Status.FINISHED) {
                            mSynthesizeTask.cancel(true);
                        }
                    }
                    myTask = new GetResponseTask();
                    myTask.execute(messageInput.getText().toString());
                    messageInput.setText("");
                }
                return true;
            }
            return false;
        });

        sendBtn.setOnClickListener(v -> {
            if (!messageInput.getText().toString().isEmpty()) {
                adapter.add(adapter.getItemCount(), messageInput.getText().toString(), null, true);
                rv.scrollToPosition(adapter.getItemCount() - 1);
                if (myTask.getStatus() != AsyncTask.Status.FINISHED) {
                    myTask.cancel(true);
                    if (mSynthesizeTask != null && mSynthesizeTask.getStatus() != AsyncTask.Status.FINISHED) {
                        mSynthesizeTask.cancel(true);
                    }
                }
                myTask = new GetResponseTask();
                myTask.execute(messageInput.getText().toString());
                messageInput.setText("");
            }
        });

//        InputStream raw = getResources().openRawResource(R.raw.chat_json);
//        Reader rd = new BufferedReader(new InputStreamReader(raw));
//        Gson gson = new Gson();
//        mModel = gson.fromJson(rd, ResultModel[].class);
//        loadModel(0, false);


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

    private class GetResponseTask extends AsyncTask<String, MessageResponse, Void> {
        MessageResponse response;
        ArrayList<InputStream> responseAudioStream;
        ArrayList<Response<InputStream>> synthesizeAudioStream;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mSynthesizeTask != null && mSynthesizeTask.getStatus() != Status.FINISHED) {
                mSynthesizeTask.cancel(true);
            }
            adapter.add(adapter.getItemCount(), "...", null, false);
            rv.scrollToPosition(adapter.getItemCount() - 1);
        }

        @Override
        protected Void doInBackground(String... message) {
            if (sessionResponse == null) {
                CreateSessionOptions sessionOptions = new CreateSessionOptions.Builder(assistant_id).build();
                sessionResponse = assistant.createSession(sessionOptions).execute().getResult();
            }
            MessageInput input;
            input = new MessageInput.Builder().messageType("text").text(message[0]).build();
            MessageOptions options = new MessageOptions.Builder(assistant_id, sessionResponse.getSessionId())
                    .input(input)
                    .build();
            responseAudioStream = new ArrayList<>();
            synthesizeAudioStream = new ArrayList<>();
            response = assistant.message(options).execute().getResult();
            publishProgress(response);

            return null;
        }

        @Override
        protected void onProgressUpdate(MessageResponse... values) {
            super.onProgressUpdate(values);
            adapter.delete(adapter.getItemCount() - 1);
            Handler handler = new Handler();
            Log.d("Watson Response", response.toString());
            for (RuntimeResponseGeneric res : response.getOutput().getGeneric()) {
                switch (res.responseType()) {
                    case "text":
                        adapter.add(adapter.getItemCount(), res.text(), null, false);
                        rv.scrollToPosition(adapter.getItemCount() - 1);
                        break;
                    case "option":
                        adapter.add(adapter.getItemCount(), res.title(), null, false);
                        rv.scrollToPosition(adapter.getItemCount() - 1);

                        final TextView[] textViews = new TextView[res.options().size()];
                        for (int i = 0; i < res.options().size(); i++) {
                            TextView newTextView = new TextView(ChatActivity.this);
                            newTextView.setText(res.options().get(i).getLabel());
                            newTextView.setId(View.generateViewId());
                            newTextView.setLayoutParams(new FlexboxLayout.LayoutParams(
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT));
                            if (Objects.equals(LocaleManager.getLanguage(ChatActivity.this), LocaleManager.LANGUAGE_ARABIC))
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
                                adapter.add(adapter.getItemCount(), res.options().get(finalI).getValue().getInput().text(), null, true);
                                rv.scrollToPosition(adapter.getItemCount() - 1);
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fad_in_out);
                                view.startAnimation(animation);
                                flexboxLayout.removeAllViews();
                                if (myTask != null && myTask.getStatus() != Status.FINISHED) {
                                    myTask.cancel(true);
                                }
                                if (mSynthesizeTask != null && mSynthesizeTask.getStatus() != Status.FINISHED) {
                                    mSynthesizeTask.cancel(true);
                                }
                                myTask = new GetResponseTask();
                                myTask.execute(res.options().get(finalI).getValue().getInput().text());
                            });

                            textViews[i] = newTextView;
                        }
                        handler.postDelayed(() -> {
                            for (int i = 0; i < res.options().size(); i++) {
                                flexboxLayout.addView(textViews[i]);
                            }
                        }, 1000);
                        break;
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mSynthesizeTask != null && mSynthesizeTask.getStatus() != Status.FINISHED) {
                mSynthesizeTask.cancel(true);
            }
            mSynthesizeTask = new GetSynthesizeTask();
            mSynthesizeTask.execute(response);
        }
    }


    private class GetSynthesizeTask extends AsyncTask<MessageResponse, Void, Void> {
        ArrayList<InputStream> responseAudioStream;
        SynthesizeOptions synthesizeOptions;
        StreamPlayer streamPlayer = new StreamPlayer();

        @Override
        protected void onCancelled() {
            super.onCancelled();
            streamPlayer.interrupt();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(MessageResponse... message) {
            responseAudioStream = new ArrayList<>();
            for (RuntimeResponseGeneric res : message[0].getOutput().getGeneric()) {
                if (res.responseType().equals("text")) {
                    try {

                        Call<ResponseBody> call = api.getSoundFile("ara", res.text());
                        retrofit2.Response<ResponseBody> response = call.execute();
                        assert response.body() != null;
                        InputStream targetStream = new ByteArrayInputStream(response.body().bytes());
                        responseAudioStream.add(targetStream);

                    } catch (ForbiddenException | NullPointerException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (InputStream inputStream : responseAudioStream)
                try {
                    streamPlayer.playStream(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return null;
        }
    }
}
