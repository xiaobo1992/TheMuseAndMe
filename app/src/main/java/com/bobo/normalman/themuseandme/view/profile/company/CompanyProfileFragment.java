package com.bobo.normalman.themuseandme.view.profile.company;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.bobo.normalman.themuseandme.util.ImageUtil;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.profile.CompanyProfileActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/7/17.
 */

public class CompanyProfileFragment extends Fragment {
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.company_description)
    TextView description;
    @BindView(R.id.company_image_f1)
    SimpleDraweeView f1;
    @BindView(R.id.company_image_f2)
    SimpleDraweeView f2;
    @BindView(R.id.company_image_f3)
    SimpleDraweeView f3;

    @BindView(R.id.company_logo)
    ImageView logo;
    @BindView(R.id.company_like)
    ImageView like;
    DatabaseReference mRef;
    Company company;
    View view;

    public static CompanyProfileFragment newInstance(Company company) {
        Bundle args = new Bundle();
        CompanyProfileFragment fragment = new CompanyProfileFragment();
        args.putString(CompanyProfileActivity.KEY_COMPANY,
                ModelUtil.toString(company, new TypeToken<Company>() {
                }));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_company_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        company = ModelUtil.toObject(getArguments().getString(CompanyProfileActivity.KEY_COMPANY),
                new TypeToken<Company>() {
                });

        companyName.setText(company.name);
        description.setText(company.description);
        f2.setImageURI(company.getF2ImageUrl());
        f3.setImageURI(company.getF3ImageUrl());
        f1.setImageURI(company.getF1ImageUrl());
        ImageUtil.loadImage(getContext(), logo, company.getLogoImageUrl());
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!company.liked) {
                    DBUtil.saveCompany(company);
                } else {
                    DBUtil.removeCompany(company);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mRef = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(DBUtil.COMPANY)
                .child(String.valueOf(company.id));
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                company.liked = dataSnapshot.getValue() != null;
                Log.d("context", (getContext() == null) + "");
                Drawable drawable = company.liked ?
                        ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_pink_24px) :
                        ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_black_24px);
                like.setImageDrawable(drawable);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
