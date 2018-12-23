package com.ajman.ded.ae.screens.base;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajman.ded.ae.FaqActivity;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.ServiceCentersActivity;
import com.ajman.ded.ae.WebViewActivity;
import com.ajman.ded.ae.adapters.CustomExpandableListAdapter;
import com.ajman.ded.ae.adapters.ExpandableListDataSource;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.InsertNewHappy.RequestBody_InsertNewHappy;
import com.ajman.ded.ae.data.model.request.InsertNewHappy.RequestData_InsertNewHappy;
import com.ajman.ded.ae.data.model.request.InsertNewHappy.RequestEnvelope_InsertNewHappy;
import com.ajman.ded.ae.data.model.response.InsertNewHappy.ResponseEnvelope_InsertNewHappy;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.ServiceModuleModel;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.screens.IntroActivity;
import com.ajman.ded.ae.screens.InvestorGuide;
import com.ajman.ded.ae.screens.accountSettings.AccountActivity;
import com.ajman.ded.ae.screens.dashboard.DashBoardActivity;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.ajman.ded.ae.screens.login.LoginActivity;
import com.ajman.ded.ae.screens.news.NewsActivity;
import com.ajman.ded.ae.screens.registeration.RegisterActivity;
import com.ajman.ded.ae.screens.search.SearchActivity;
import com.ajman.ded.ae.utility.AppPreferenceManager;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.ajman.ded.ae.utility.slidingrootnav.SlideGravity;
import com.ajman.ded.ae.utility.slidingrootnav.SlidingRootNav;
import com.ajman.ded.ae.utility.slidingrootnav.SlidingRootNavBuilder;
import com.ajman.ded.ae.utility.slidingrootnav.callback.DragListener;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.roughike.bottombar.BottomBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;
import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ENGLISH;
import static com.ajman.ded.ae.utility.Constants.URL_INTENT_KEY;
import static com.ajman.ded.ae.utility.Constants.links.Customers_happiness_Pledge;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_APP_AR;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_APP_EN;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_AR;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_EN;
import static com.ajman.ded.ae.utility.Constants.links.FAQ;
import static com.ajman.ded.ae.utility.Constants.links.applayForJob;
import static com.ajman.ded.ae.utility.Constants.links.businessControl;
import static com.ajman.ded.ae.utility.Constants.links.complaints;
import static com.ajman.ded.ae.utility.Constants.links.investInAjman;
import static com.ajman.ded.ae.utility.Constants.links.suggestions;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, DragListener {

    public Merlin merlin;
    public MerlinsBeard merlinsBeard;
    ImageView sad, meh, happy;
    TextView logout;
    private BottomBar mBottomBar;
    private Dialog dialog;
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;
    private ArrayList mExpandableListTitle;
    private Map<String, List<String>> mExpandableListData;
    private SlidingRootNav mSlidingRootNav;
    private String action = "null";
    private RealmResults<ServiceModuleModel> models;
    private int gPosition, cPosition;
    private String[] items;
    private Toolbar mToolbar;
    private SweetAlertDialog sDialog;
    private Api api;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    protected abstract int getLayoutResource();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        merlin = new Merlin.Builder().withAllCallbacks().build(BaseActivity.this);
        merlinsBeard = new MerlinsBeard.Builder().build(this);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initItems();
        final String appPackageName = getPackageName();
        if (!(this instanceof SearchActivity)) {
            mBottomBar = findViewById(R.id.bottomBar);
            mBottomBar.setTabSelectionInterceptor((oldTabId, newTabId) -> {
                switch (newTabId) {
                    case R.id.tab_chat:
                        if (!(this instanceof FaqActivity))
                            startActivity(new Intent(this, FaqActivity.class));
                        break;
                    case R.id.tab_search:
                        startActivity(new Intent(this, SearchActivity.class));
                        break;
                    case R.id.tab_rate:
                        dialog = new Dialog(this);
                        dialog.setContentView(R.layout.dialog_meter);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        sad = dialog.findViewById(R.id.happy);
                        meh = dialog.findViewById(R.id.meh);
                        happy = dialog.findViewById(R.id.sad);
                        sad.setOnClickListener(this);
                        meh.setOnClickListener(this);
                        happy.setOnClickListener(this);
                        dialog.show();
                        break;
                    case R.id.tab_call:
                        final Dialog call = new Dialog(this);
                        call.setContentView(R.layout.dialog_call);
                        call.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        Button callButton = call.findViewById(R.id.call);
                        callButton.setOnClickListener(view -> {
                            dialPhoneNumber("80070");
                            call.dismiss();
                        });

                        call.show();
                }
                return false;
            });
        }

        if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ENGLISH)) {
            mSlidingRootNav = new SlidingRootNavBuilder(this)
                    .withToolbarMenuToggle(mToolbar)
                    .withMenuLayout(R.layout.sliding_root_layout)
                    .addDragListener(this)
                    .withGravity(SlideGravity.LEFT)
                    .inject();
        } else {
            mSlidingRootNav = new SlidingRootNavBuilder(this)
                    .withToolbarMenuToggle(mToolbar)
                    .withMenuLayout(R.layout.sliding_root_layout)
                    .addDragListener(this)
                    .withGravity(SlideGravity.RIGHT)
                    .inject();
        }
        if (!(this instanceof HomeActivity)) {
            findViewById(R.id.up).setVisibility(View.VISIBLE);
            findViewById(R.id.up).setOnClickListener(view -> super.onBackPressed());
        }

        mToolbar.setContentInsetsAbsolute(0, 0);
        mExpandableListView = findViewById(R.id.left_drawer);
        View listHeaderView = getLayoutInflater().inflate(R.layout.list_item_drawer_header, null, false);
        View listFooterView = getLayoutInflater().inflate(R.layout.list_item_drawer_footer, null, false);
        logout = listFooterView.findViewById(R.id.logout_text);

        if (UserData.getUserObject(this) != null) {
            listHeaderView.findViewById(R.id.dash_btn).setVisibility(View.VISIBLE);
            listHeaderView.findViewById(R.id.setting_btn).setVisibility(View.VISIBLE);
            listFooterView.findViewById(R.id.register_text).setVisibility(View.GONE);
            listHeaderView.findViewById(R.id.dash_btn).setOnClickListener(view -> {
                action = "DashBoard";
                mSlidingRootNav.closeMenu(true);
            });
            listHeaderView.findViewById(R.id.setting_btn).setOnClickListener(view -> {
                action = "AccountSettings";
                mSlidingRootNav.closeMenu(true);
            });
        } else {
            listHeaderView.findViewById(R.id.dash_btn).setVisibility(View.GONE);
            listHeaderView.findViewById(R.id.setting_btn).setVisibility(View.GONE);
            listFooterView.findViewById(R.id.register_text).setVisibility(View.VISIBLE);
            listFooterView.findViewById(R.id.register_text).setOnClickListener(view -> {
                action = "Register";
                mSlidingRootNav.closeMenu(true);
            });
        }

        listFooterView.findViewById(R.id.guide_text).setOnClickListener(v -> {
            if (!(BaseActivity.this instanceof InvestorGuide))
                startActivity(new Intent(BaseActivity.this, InvestorGuide.class));
            else
                mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.share_btn).setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + appPackageName);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });

        listFooterView.findViewById(R.id.fb).setOnClickListener(v -> {
            action = "FB";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.help_btn).setOnClickListener(v -> {
            action = "HELP";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.job_text).setOnClickListener(v -> {
            action = "JOB";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.invest_text).setOnClickListener(v -> {
            action = "INVEST";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.faq_text).setOnClickListener(v -> {
            action = "FAQ";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.pledge_text).setOnClickListener(v -> {
            action = "PLEDGE";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.business_text).setOnClickListener(v -> {
            action = "BUSINESS";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.news_text).setOnClickListener(v -> {
            action = "NEWS";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.twitter).setOnClickListener(v -> {
            action = "Twitter";
            mSlidingRootNav.closeMenu(true);
        });


        listFooterView.findViewById(R.id.instagram).setOnClickListener(v -> {
            action = "Instagram";
            mSlidingRootNav.closeMenu(true);
        });


        listHeaderView.findViewById(R.id.home_btn).setOnClickListener(view -> {
            action = "HomeScreen";
            mSlidingRootNav.closeMenu(true);
        });

        listFooterView.findViewById(R.id.lang_text).setOnClickListener(view -> {
            action = "Language";
            mSlidingRootNav.closeMenu(true);
        });

        listFooterView.findViewById(R.id.services_text).setOnClickListener(view -> {
            action = "Services";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.suggestions_text).setOnClickListener(view -> {
            action = "Suggestions";
            mSlidingRootNav.closeMenu(true);

        });

        listFooterView.findViewById(R.id.complains_text).setOnClickListener(view -> {
            action = "Complains";
            mSlidingRootNav.closeMenu(true);
        });

        if (UserData.getUserObject(this) == null) {
            logout.setText(getString(R.string.login));
            listFooterView.findViewById(R.id.logout_text).setOnClickListener(view -> {
                action = "Login";
                mSlidingRootNav.closeMenu(true);
            });
        } else {
            logout.setText(getString(R.string.logout));
            listFooterView.findViewById(R.id.logout_text).setOnClickListener(view -> {
                action = "Logout";
                mSlidingRootNav.closeMenu(true);
            });
        }

        mExpandableListView.addHeaderView(listHeaderView);
        mExpandableListView.addFooterView(listFooterView);
        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        addDrawerItems();

    }

    public Intent getFacebookPageURL(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/187999537912703"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/187999537912703"));
        }
    }

    public Intent newInstagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                intent.setData(Uri.parse("http://instagram.com/_u/" + url));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        merlin.bind();
        if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC)) {
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        } else {
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        }
    }

    @Override
    protected void onPause() {
        merlin.unbind();
        super.onPause();
        if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC)) {
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        } else {
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    private void initItems() {
        items = getResources().getStringArray(R.array.film_genre);
    }

    private void addDrawerItems() {
        models = Realm.getDefaultInstance().where(ServiceModuleModel.class).findAll();
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            action = "ExpandableList";
            gPosition = groupPosition;
            cPosition = childPosition;
            mSlidingRootNav.closeMenu(true);
            return false;
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.happy:
                dialog.dismiss();
                InsertNewHappy(1);
                break;
            case R.id.sad:
                InsertNewHappy(2);
                dialog.dismiss();
            case R.id.meh:
                dialog.dismiss();
                InsertNewHappy(3);
                break;
        }
    }


    public void Success() {
        sDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.thanks_rate))
                .setConfirmText(getString(R.string.done));
        sDialog.show();
        sDialog.setCancelable(true);
    }

    public void Failure() {
        sDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.went_wrong))
                .setConfirmText(getString(R.string.done));
        sDialog.show();
        sDialog.setCancelable(true);
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    public void onDrag(float progress) {
        if (progress == 0) {
            switch (action) {
                case "HomeScreen":
                    action = "null";
                    if (!(this instanceof HomeActivity))
                        startActivity(new Intent(this, HomeActivity.class));
                    break;
                case "AccountSettings":
                    action = "null";
                    if (!(this instanceof AccountActivity))
                        startActivity(new Intent(this, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                    break;
                case "OnlineHelp":
                    action = "null";
                    if (!(this instanceof FaqActivity))
                        startActivity(new Intent(this, FaqActivity.class));
                    break;
                case "Language":
                    action = "null";
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC)) {
                        LocaleManager.setNewLocale(this, LANGUAGE_ENGLISH);
                    } else {
                        LocaleManager.setNewLocale(this, LANGUAGE_ARABIC);
                    }
                    restartActivity();
                    break;
                case "Complains":
                    action = "null";
                    Intent complains = new Intent(this, WebViewActivity.class);
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC))
                        complains.putExtra(URL_INTENT_KEY, DOMAIN_APP_AR + complaints);
                    else
                        complains.putExtra(URL_INTENT_KEY, DOMAIN_APP_EN + complaints);
                    this.startActivity(complains);
                    break;
                case "Services":
                    action = "null";
                    Intent service = new Intent(this, ServiceCentersActivity.class);
                    this.startActivity(service);
                    break;
                case "Suggestions":
                    action = "null";
                    Intent Suggestions = new Intent(this, WebViewActivity.class);
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC))
                        Suggestions.putExtra(URL_INTENT_KEY, DOMAIN_APP_AR + suggestions);
                    else
                        Suggestions.putExtra(URL_INTENT_KEY, DOMAIN_APP_EN + suggestions);
                    this.startActivity(Suggestions);
                    break;
                case "JOB":
                    action = "null";
                    Intent job = new Intent(this, WebViewActivity.class);
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC))
                        job.putExtra(URL_INTENT_KEY, DOMAIN_APP_AR + applayForJob);
                    else
                        job.putExtra(URL_INTENT_KEY, DOMAIN_APP_EN + applayForJob);
                    this.startActivity(job);
                    break;
                case "INVEST":
                    action = "null";
                    Intent invest = new Intent(this, WebViewActivity.class);
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC))
                        invest.putExtra(URL_INTENT_KEY, DOMAIN_APP_AR + investInAjman);
                    else
                        invest.putExtra(URL_INTENT_KEY, DOMAIN_APP_EN + investInAjman);
                    this.startActivity(invest);
                    break;
                case "FAQ":
                    action = "null";
                    Intent faq = new Intent(this, WebViewActivity.class);
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC))
                        faq.putExtra(URL_INTENT_KEY, DOMAIN_APP_AR + FAQ);
                    else
                        faq.putExtra(URL_INTENT_KEY, DOMAIN_APP_EN + FAQ);
                    this.startActivity(faq);
                    break;
                case "BUSINESS":
                    action = "null";
                    Intent business = new Intent(this, WebViewActivity.class);
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC))
                        business.putExtra(URL_INTENT_KEY, DOMAIN_APP_AR + businessControl);
                    else
                        business.putExtra(URL_INTENT_KEY, DOMAIN_APP_EN + businessControl);
                    this.startActivity(business);
                    break;
                case "PLEDGE":
                    action = "null";
                    Intent pledge = new Intent(this, WebViewActivity.class);
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC))
                        pledge.putExtra(URL_INTENT_KEY, DOMAIN_APP_AR + Customers_happiness_Pledge);
                    else
                        pledge.putExtra(URL_INTENT_KEY, DOMAIN_APP_EN + Customers_happiness_Pledge);
                    this.startActivity(pledge);
                    break;
                case "DashBoard":
                    action = "null";
                    UserModel userModel = UserData.getUserObject(this);
                    if (null != userModel) {
                        if (!(this instanceof DashBoardActivity))
                            startActivity(new Intent(this, DashBoardActivity.class));
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                    break;
                case "ExpandableList":
                    action = "null";
                    Intent expandableList = new Intent(this, WebViewActivity.class);
                    if (Objects.equals(LocaleManager.getLanguage(this), LANGUAGE_ARABIC)) {
                        if (gPosition == 5)
                            expandableList.putExtra(URL_INTENT_KEY, DOMAIN_APP_AR + models.get(gPosition).getList().get(cPosition).getLink());
                        else
                            expandableList.putExtra(URL_INTENT_KEY, DOMAIN_AR + models.get(gPosition).getList().get(cPosition).getLink());
                    } else {
                        if (gPosition == 5)
                            expandableList.putExtra(URL_INTENT_KEY, DOMAIN_APP_EN + models.get(gPosition).getList().get(cPosition).getLink());
                        else
                            expandableList.putExtra(URL_INTENT_KEY, DOMAIN_EN + models.get(gPosition).getList().get(cPosition).getLink());
                    }
                    startActivity(expandableList);
                    break;
                case "Logout":
                    action = "null";
                    UserData.clearUser(this);
                    getApplicationContext().deleteDatabase("webview.db");
                    getApplicationContext().deleteDatabase("webviewCache.db");
                    File webviewCacheDir = new File(getApplicationContext().getCacheDir().getAbsolutePath() + "/webviewCache");
                    if (webviewCacheDir.exists()) {
                        getApplicationContext().deleteFile(webviewCacheDir.getAbsolutePath());
                    }

                    File appCacheDir = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/webcache");
                    if (appCacheDir.exists()) {
                        getApplicationContext().deleteFile(appCacheDir.getAbsolutePath());
                    }

                    String[] databases = getApplicationContext().databaseList();
                    if (databases != null) {
                        for (String database : databases) {
                            getApplicationContext().deleteDatabase(database);
                        }
                    }
                    AppPreferenceManager.putBool(this, AppPreferenceManager.KEY_IS_JUST_LOGIN, true);
                    ActivityCompat.finishAffinity(this);
                    startActivity(new Intent(this, HomeActivity.class));
                    break;
                case "Login":
                    action = "null";
                    startActivity(new Intent(this, LoginActivity.class));
                    break;
                case "Register":
                    action = "null";
                    startActivity(new Intent(this, RegisterActivity.class));
                    break;
                case "Instagram":
                    action = "null";
                    startActivity(newInstagramProfileIntent(BaseActivity.this.getPackageManager(), "ajmanded"));
                    break;
                case "Twitter":
                    action = "null";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/AjmanDED")));
                    break;
                case "FB":
                    action = "null";
                    startActivity(getFacebookPageURL(BaseActivity.this));
                    break;
                case "HELP":
                    action = "null";
                    startActivity(new Intent(this, IntroActivity.class));
                    break;
                case "NEWS":
                    action = "null";
                    startActivity(new Intent(this, NewsActivity.class));
                    break;
//                case "CHANGE_URL":
//                    action = "null";
//                    EditText myEditText = new EditText(this);
//                    myEditText.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    sDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
//                            .setTitleText(getString(R.string.change_url))
//                            .setCustomView(myEditText)
//                            .setConfirmClickListener(sweetAlertDialog -> ChangeUrl(myEditText.getText().toString()))
//                            .setConfirmText(getString(R.string.done));
//                    sDialog.show();
//                    sDialog.setCancelable(true);
//                    break;

            }
        }
    }

    public void ChangeUrl(String url) {
        if (!url.isEmpty()) {
            AppPreferenceManager.putString(getApplicationContext(), AppPreferenceManager.KEY_IS_BASE_URL, url);
            Intent mStartActivity = new Intent(getApplicationContext(), HomeActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        }
        if (sDialog != null)
            sDialog.dismiss();
    }


    private void InsertNewHappy(int result) {
        RequestEnvelope_InsertNewHappy envelope = new RequestEnvelope_InsertNewHappy();

        RequestBody_InsertNewHappy body = new RequestBody_InsertNewHappy();

        RequestData_InsertNewHappy data = new RequestData_InsertNewHappy();

        switch (result) {
            case 1:
                data.setHappy(1);
                data.setUnHappy(0);
                data.setNeutral(0);
                break;
            case 2:
                data.setHappy(0);
                data.setUnHappy(1);
                data.setNeutral(0);
                break;
            case 3:
                data.setHappy(0);
                data.setUnHappy(0);
                data.setNeutral(1);
                break;
        }
        body.setRequestData(data);

        envelope.setBody(body);

        api = ApiBuilder.providesApi();

        Call<ResponseEnvelope_InsertNewHappy> call = api.requestNewHappyCall("InsertNewHappy", envelope);

        call.enqueue(new retrofit2.Callback<ResponseEnvelope_InsertNewHappy>() {

            @Override
            public void onResponse(@NonNull Call<ResponseEnvelope_InsertNewHappy> call, @NonNull final Response<ResponseEnvelope_InsertNewHappy> response) {
                if (response.isSuccessful()) {
                    int result = response.body().getBody().getData().getInsertNewHappyResult();
                    Log.d("HAPPY PASS:", String.valueOf(result));
                    Success();
                } else {
                    Failure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_InsertNewHappy> call, @NonNull Throwable t) {
                Log.d("HAPPY ERROR:", String.valueOf(t));
                Failure();
            }
        });
    }

}