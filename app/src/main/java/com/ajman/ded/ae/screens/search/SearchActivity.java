package com.ajman.ded.ae.screens.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.adapters.ServiceAdapter;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.ServiceModel;
import com.ajman.ded.ae.screens.base.BaseActivity;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.rv_service)
    RecyclerView mRecycleService;

    @BindView(R.id.et_toolbar_search)
    EditText ToolbarSearchEditText;

    ServiceAdapter mAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        List<ServiceModel> models = Realm.getDefaultInstance().where(ServiceModel.class).findAll();

        mAdapter = new ServiceAdapter(this, models, R.layout.list_item_service_search);
        mRecycleService.setAdapter(mAdapter);

        mRecycleService.setLayoutManager(new LinearLayoutManager(this));
        mRecycleService.setItemAnimator(new DefaultItemAnimator());
        mRecycleService.setNestedScrollingEnabled(false);
        mRecycleService.setHasFixedSize(true);
        ToolbarSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void triggerByInternet() {

    }

    private void search(String keyword) {
        String likeForm = String.format(Locale.getDefault(), keyword).replaceAll("[\\u0635\\u06c1\\u06c3\\u06be\\u06d5\\u0625\\u0623\\u0622\\u0621\\u0626\\u0620\\u063d\\u0649\\u064a\\u0624\\u0648\\u0627\\u0647\\u0629]", "*")
                .replaceAll("\\s{2,}", " ").trim()
                .replaceAll(".$", "*");
        RealmResults<ServiceModel> models;
        if (Objects.equals(LocaleManager.getLanguage(this), LocaleManager.LANGUAGE_ARABIC))
            models = Realm.getDefaultInstance()
                    .where(ServiceModel.class)
                    .like("nameAr", removeAccents(likeForm), Case.INSENSITIVE)
                    .or()
                    .contains("nameAr", removeAccents(keyword), Case.INSENSITIVE)
                    .findAll();
        else
            models = Realm.getDefaultInstance()
                    .where(ServiceModel.class)
                    .contains("name", keyword, Case.INSENSITIVE)
                    .findAll();
        mAdapter.updateData(models);
        Log.d("SEARCH: ", likeForm);

    }


    public String removeAccents(String text) {
        return text == null ? null :
                Normalizer.normalize(text, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
