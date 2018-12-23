package com.ajman.ded.ae.screens.home;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.libs.AnimatedFragment;

public class AboutDEDFragment extends AnimatedFragment {

    CardView cardView;
    CardView cardView1;
    CardView cardView2;
    ImageView header;

    public AboutDEDFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_ded, container, false);
        header = view.findViewById(R.id.header_view);
        cardView = view.findViewById(R.id.card);
        cardView1 = view.findViewById(R.id.card1);
        cardView2 = view.findViewById(R.id.card2);

        cardView.setVisibility(View.INVISIBLE);
        cardView1.setVisibility(View.INVISIBLE);
        cardView2.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startAnimation();
    }

    @Override
    public void startAnimation() {
        new AnimatedFragment.animate(new View[]{header, cardView, cardView1, cardView2}).execute();
    }
}
