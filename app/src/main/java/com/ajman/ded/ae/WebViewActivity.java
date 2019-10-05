package com.ajman.ded.ae;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.models.uaepass.AuthTokenModel;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.utility.SharedTool.UserData;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import retrofit2.Call;

import static com.ajman.ded.ae.utility.Constants.CODE_RESULT_KEY;
import static com.ajman.ded.ae.utility.Constants.REDIRECT_URL_INTENT_KEY;
import static com.ajman.ded.ae.utility.Constants.URL_INTENT_KEY;

public class WebViewActivity extends BaseActivity {
    private static final String TAG = WebViewActivity.class.getSimpleName();
    private final static int FCR = 1;
    WebView mWebView;
    private ContentLoadingProgressBar mProgressBar;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private SwipeRefreshLayout swipeContainer;
    String redirect_url = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_web_view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = findViewById(R.id.webView);
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(() -> mWebView.loadUrl("javascript:window.location.reload( true )"));
        swipeContainer.setColorSchemeResources(R.color.colorAccent);

        swipeContainer.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (mWebView.getScrollY() == 0)
                swipeContainer.setEnabled(true);
            else
                swipeContainer.setEnabled(false);

        });

        mProgressBar = findViewById(R.id.progressbar);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(1);
//        mWebView.clearCache(true);
//        mWebView.clearFormData();
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setGeolocationEnabled(true);
        String url = getIntent().getStringExtra(URL_INTENT_KEY);
        redirect_url = getIntent().getStringExtra(REDIRECT_URL_INTENT_KEY);
        mWebView.loadUrl(url, getHeaders());
        mWebView.setWebViewClient(new Callback());
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                mProgressBar.setProgress(progress);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            //For Android 5.0+
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    WebChromeClient.FileChooserParams fileChooserParams) {
                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }
                mUMA = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(WebViewActivity.this.getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    } catch (IOException ex) {
                        Log.e(TAG, "Image file creation failed", ex);
                    }
                    if (photoFile != null) {
                        mCM = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FCR);
                return true;
            }
        });

    }


//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mWebView.saveState(outState);
//    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("ZASOUL_CLIENT", "MobileAPP");
        UserModel userModel = UserData.getUserObject(this);
        if (userModel != null) {
            mWebView.clearCache(true);
            headers.put("ZASOUL_Username", userModel.getUserName());
            headers.put("ZASOUL_Password", userModel.getPassword());
        } else {
            mWebView.clearCache(true);
            headers.put("ZASOUL_Username", null);
            headers.put("ZASOUL_Password", null);
        }
        return headers;
    }

//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {   // replaced in onCreate
//        super.onRestoreInstanceState(savedInstanceState);
//        mWebView.restoreState(savedInstanceState);
//    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onPause() {
        mWebView.onPause();
        mWebView.pauseTimers();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.resumeTimers();
        mWebView.onResume();
    }

    @Override
    public void triggerByInternet() {

    }


    @Override
    protected void onDestroy() {
        mWebView.destroy();
        mWebView = null;
        super.onDestroy();
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null || intent.getData() == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }


    public class Callback extends WebViewClient {


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                                    WebResourceError error) {
            super.onReceivedError(view, request, error);
            // Do something
            //Toast.makeText(WebViewActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();

            //finish();
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url, getHeaders());
            try {
                URI current_uri = new URI(url);
                if (redirect_url != null && redirect_url.length() != 0) {
                    URI redirect_uri = new URI(redirect_url);
//                    D/UAE_PASS_QUERY: code=b931ab5130372c50fc9d043175e6928577fb027a1fa63fd0b2738530e7d81de5&state=ShNP22hyl1jUU2RGjTRkpg==
                    if (current_uri.getScheme().equals("uaepass")) {
                        Log.d("UAE_PASS_URL", current_uri.toString());
                    }
                    if (current_uri.getScheme().equals(redirect_uri.getScheme())) {
                        Log.d("UAE_PASS_QUERY", current_uri.getQuery());
                        if (current_uri.getPath().equals(redirect_uri.getPath())) {
                            UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(current_uri.toString());
                            String code = sanitizer.getValue("code");
                            Intent intent = new Intent();
                            intent.putExtra(CODE_RESULT_KEY, code);
                            setResult(RESULT_OK, intent);
                        }
                        finish();
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mProgressBar.show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressBar.hide();
            swipeContainer.setRefreshing(false);
            super.onPageFinished(view, url);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }
    }
}
