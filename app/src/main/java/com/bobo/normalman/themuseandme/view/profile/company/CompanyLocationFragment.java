package com.bobo.normalman.themuseandme.view.profile.company;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.profile.CompanyProfileActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/7/17.
 */

public class CompanyLocationFragment extends Fragment implements OnMapReadyCallback {
    final String KEY_NAME = "name";
    final int PADDING = 10;
    Company company;


    public static CompanyLocationFragment newInstance(Company company) {
        Bundle args = new Bundle();
        CompanyLocationFragment fragment = new CompanyLocationFragment();
        args.putString(CompanyProfileActivity.KEY_COMPANY,
                ModelUtil.toString(company, new TypeToken<Company>() {
                }));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_location, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        company = ModelUtil.toObject(getArguments().getString(CompanyProfileActivity.KEY_COMPANY),
                new TypeToken<Company>() {
                });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(getContext());
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        try {
            for (HashMap<String, String> location : company.locations) {
                String name = location.get(KEY_NAME);
                List<Address> addresses = geocoder.getFromLocationName(name, 1);
                for (Address address : addresses) {
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    builder.include(latLng);
                    googleMap.addMarker(new MarkerOptions().position(latLng)).setTitle(name);
                }
            }
            if (company.locations.size() > 0) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), PADDING));
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(1.0f));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
