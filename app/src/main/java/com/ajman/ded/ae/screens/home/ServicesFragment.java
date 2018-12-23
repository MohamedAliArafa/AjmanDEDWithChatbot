package com.ajman.ded.ae.screens.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.adapters.ServiceNestedAdapter;
import com.ajman.ded.ae.models.ServiceModuleModel;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {

    @BindView(R.id.service_title_recycler)
    RecyclerView mRecycler;
    private ServiceNestedAdapter mAdapter;

    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        ButterKnife.bind(this, view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ServiceModuleModel> models = Realm.getDefaultInstance().where(ServiceModuleModel.class).findAll();
        mAdapter = new ServiceNestedAdapter(getActivity(), models, R.layout.list_item_service_parent);
        mRecycler.setAdapter(mAdapter);
        return view;
    }
}
