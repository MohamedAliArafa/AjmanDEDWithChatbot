package com.ajman.ded.ae;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajman.ded.ae.models.service.ServiceList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExpandableRecyclerAdapter extends RecyclerView.Adapter<ExpandableRecyclerAdapter.ViewHolder> {

    private List<ServiceList> repos;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Context context;

    public ExpandableRecyclerAdapter(List<ServiceList> repos) {
        this.repos = repos;
        //set initial expanded state to false
        for (int i = 0; i < repos.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ExpandableRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service_group, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExpandableRecyclerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(repos.get(i).getName());
        viewHolder.building.setText(repos.get(i).getBuilding().getName());
        viewHolder.time.setText(repos.get(i).getWorkinghours().get(0));
        if (repos.get(i).getWorkinghours().size() > 1)
            viewHolder.time2.setText(repos.get(i).getWorkinghours().get(1));
        else
            viewHolder.time2.setVisibility(View.GONE);
        viewHolder.phone.setText(repos.get(i).getPhone().get(0));
        if (repos.get(i).getPhone().size() > 1)
            viewHolder.phone2.setText(repos.get(i).getPhone().get(1));
        else
            viewHolder.phone2.setVisibility(View.GONE);
        if (repos.get(i).getFax() != null)
            viewHolder.fax.setText(repos.get(i).getFax());
        else
            viewHolder.fax.setVisibility(View.GONE);
        viewHolder.email.setText(repos.get(i).getEmail());
        viewHolder.website.setText(repos.get(i).getWebsite().get(0));
        if (repos.get(i).getWebsite().size() > 1)
            viewHolder.website2.setText(repos.get(i).getWebsite().get(1));
        else
            viewHolder.website2.setVisibility(View.GONE);
        viewHolder.postalcode.setText(repos.get(i).getPostalcode());
        if (repos.get(i).getCustomerservice() != null) {
            viewHolder.custome_service.setText(repos.get(i).getCustomerservice());
        } else {
            viewHolder.custome_service.setVisibility(View.GONE);
        }
        viewHolder.whatsapp.setText(repos.get(i).getWhatsapp());
        if (repos.get(i).getFacebook() != null) {
            if (repos.get(i).getFacebook().getName() != null)
                viewHolder.facebook.setText(repos.get(i).getFacebook().getName());
            else
                viewHolder.facebook.setVisibility(View.GONE);
        }
        if (repos.get(i).getYoutube() != null) {
            if (repos.get(i).getYoutube().getName() != null)
                viewHolder.youtube.setText(repos.get(i).getYoutube().getName());
            else
                viewHolder.youtube.setVisibility(View.GONE);
        }
        if (repos.get(i).getInstgram() != null) {
            if (repos.get(i).getInstgram().getName() != null)
                viewHolder.instagram.setText(repos.get(i).getInstgram().getName());
            else
                viewHolder.instagram.setVisibility(View.GONE);
        }
        viewHolder.location.setText(repos.get(i).getBuilding().getLat() + ", " + repos.get(i).getLocation().getLng());
        viewHolder.makani.setText(repos.get(i).getMakaninumber());

        viewHolder.serviceListAdapter.updateData(repos.get(i).getServicesList());

        //check if view is expanded
        final boolean isExpanded = expandState.get(i);
        viewHolder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        viewHolder.buttonLayout.setRotation(expandState.get(i) ? 180f : 0f);
        viewHolder.clicker.setOnClickListener(v -> onClickButton(viewHolder.expandableLayout, viewHolder.buttonLayout, viewHolder.title, i));
        viewHolder.facebook.setOnClickListener(v -> context.startActivity(getFacebookPageURL(context, repos.get(i).getFacebook().getLink())));
        viewHolder.instagram.setOnClickListener(v -> context.startActivity(newInstagramProfileIntent(context.getPackageManager(), repos.get(i).getInstgram().getLink())));
        viewHolder.location.setOnClickListener(v -> context.startActivity(getGoogleMap(repos.get(i).getLocation().getLat(), repos.get(i).getLocation().getLng())));
        viewHolder.youtube.setOnClickListener(v -> context.startActivity(youtube(repos.get(i).getYoutube().getLink())));
        viewHolder.whatsapp.setOnClickListener(v -> context.startActivity(openWhatsApp(repos.get(i).getWhatsapp())));
        viewHolder.makani.setOnClickListener(v -> context.startActivity(opnMakani(repos.get(i).getMakaninumber())));
        viewHolder.website.setOnClickListener(v -> context.startActivity(website(repos.get(i).getWebsite().get(0))));
        viewHolder.website2.setOnClickListener(v -> context.startActivity(website(repos.get(i).getWebsite().get(1))));
        viewHolder.phone.setOnClickListener(v -> context.startActivity(dialPhoneNumber(repos.get(i).getPhone().get(0))));
        viewHolder.phone2.setOnClickListener(v -> context.startActivity(dialPhoneNumber(repos.get(i).getPhone().get(1))));
        viewHolder.email.setOnClickListener(v -> context.startActivity(email(repos.get(i).getEmail())));
        viewHolder.custome_service.setOnClickListener(v -> context.startActivity(dialPhoneNumber(repos.get(i).getCustomerservice())));

        viewHolder.map.getMapAsync(mMap -> {
            mMap.setPadding(0,convertDpToPx(75),0,0);
            viewHolder.googleMap = mMap;

            viewHolder.googleMap.setOnMapLoadedCallback(() -> {
                viewHolder.builder = new LatLngBounds.Builder();
                LatLng location = new LatLng(repos.get(i).getLocation().getLat(), repos.get(i).getLocation().getLng());
                viewHolder.googleMap.addMarker(new MarkerOptions().position(location).title(repos.get(i).getName()).snippet(repos.get(i).getBuilding().getName())).showInfoWindow();
                viewHolder.builder.include(location);
                LatLngBounds bounds = viewHolder.builder.build();
                viewHolder.googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 8));
            });
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private GoogleMap googleMap;
        private LatLngBounds.Builder builder;
        private RecyclerView recyclerView;
        private ServiceListAdapter serviceListAdapter;
        private TextView title, building, time, time2, phone, phone2, fax, email, website, website2, postalcode, custome_service, whatsapp, facebook, youtube, instagram, location, makani;
        ImageView buttonLayout;
        ConstraintLayout clicker, expandableLayout;
        MapView map;

        ViewHolder(View view) {
            super(view);

            clicker = view.findViewById(R.id.clicker);
            title = view.findViewById(R.id.title);
            building = view.findViewById(R.id.building);
            time = view.findViewById(R.id.time);
            time2 = view.findViewById(R.id.time2);
            phone = view.findViewById(R.id.phone);
            phone2 = view.findViewById(R.id.phone2);
            fax = view.findViewById(R.id.fax);
            email = view.findViewById(R.id.email);
            website = view.findViewById(R.id.website);
            website2 = view.findViewById(R.id.website2);
            postalcode = view.findViewById(R.id.postalcode);
            custome_service = view.findViewById(R.id.customerservice);
            whatsapp = view.findViewById(R.id.whatsapp);
            facebook = view.findViewById(R.id.facebook);
            youtube = view.findViewById(R.id.youtube);
            instagram = view.findViewById(R.id.instagram);
            location = view.findViewById(R.id.location);
            makani = view.findViewById(R.id.mkani);
            recyclerView = view.findViewById(R.id.list);
            recyclerView.setNestedScrollingEnabled(false);

            buttonLayout = view.findViewById(R.id.button);
            expandableLayout = view.findViewById(R.id.expandableLayout);
            serviceListAdapter = new ServiceListAdapter(context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(serviceListAdapter);

            map = view.findViewById(R.id.map);
            if (map != null) {
                map.onCreate(null);
                map.onResume();
                MapsInitializer.initialize(context);
            }
        }
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        // Cleanup MapView here?
        if (holder.googleMap != null) {
            holder.googleMap.clear();
            holder.googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
    }

    private void onClickButton(final ConstraintLayout expandableLayout, final ImageView buttonLayout, TextView title, final int i) {

        //Simply set View to Gone if not expanded
        //Not necessary but I put simple rotation on button layout
        if (expandableLayout.getVisibility() == View.VISIBLE) {
            createRotateAnimator(buttonLayout, 180f, 0f).start();
            expandableLayout.setVisibility(View.GONE);
            title.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            expandState.put(i, false);
        } else {
            createRotateAnimator(buttonLayout, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            expandState.put(i, true);
        }
    }

    //Code to rotate button
    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    public Intent getFacebookPageURL(Context context, String url) {
        try {
            String short_name = null;
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        }
    }

    public Intent newInstagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                intent.setData(Uri.parse(url));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }


    public Intent getGoogleMap(Double latitude, Double lng) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, lng);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        return intent;
    }

    private Intent openWhatsApp(String number) {
        String url = "https://api.whatsapp.com/send?phone=" + "+971" + number;
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse(url));
        return sendIntent;
    }

    public Intent youtube(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        return intent;
    }

    public Intent email(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        return emailIntent;
    }

    public Intent website(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(url));
        return browserIntent;
    }

    public Intent opnMakani(String args) {
        String url;
        if (args.toLowerCase().contains("rcp")) {
            url = "http://www.makani.ae/geomob/?lang=E&UAENG=" + args;
        } else {
            url = "http://www.makani.ae/geomob/?lang=E&makani=" + args;
        }
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    public Intent dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        return intent;
    }

    private int convertDpToPx(int dp){
        return Math.round(dp*(context.getResources().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT));
    }

}