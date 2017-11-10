package com.bobo.normalman.themuseandme.view.profile.coach;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.profile.CoachProfileActivity;
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

public class CoachProfileFragment extends Fragment {
    @BindView(R.id.coach_avator)
    SimpleDraweeView avator;
    @BindView(R.id.coach_name)
    TextView coachName;
    @BindView(R.id.coach_bio)
    TextView bio;
    @BindView(R.id.coach_rating)
    TextView rating;
    @BindView(R.id.coach_reviews)
    TextView reviews;
    @BindView(R.id.coach_specializations)
    TextView specialization;
    @BindView(R.id.coach_like)
    ImageView like;
    private DatabaseReference mRef;
    private Coach coach;
    private View view;

    public static CoachProfileFragment newInstance(Coach coach) {
        Bundle args = new Bundle();
        CoachProfileFragment fragment = new CoachProfileFragment();
        args.putString(CoachProfileActivity.KEY_COACH,
                ModelUtil.toString(coach, new TypeToken<Coach>() {
                }));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coach_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        coach = ModelUtil.toObject(getArguments().getString(CoachProfileActivity.KEY_COACH),
                new TypeToken<Coach>() {
                });
        avator.setImageURI(coach.getProfileImage());
        coachName.setText(coach.name);
        bio.setText(coach.bio);
        rating.setText(String.valueOf(coach.getRatingString()));
        reviews.setText(String.valueOf(coach.reviews));
        specialization.setText(coach.getSpecializationsContent());
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!coach.liked) {
                    DBUtil.saveCoach(coach);
                } else {
                    DBUtil.removeCoach(coach);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mRef = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid()).child(DBUtil.COACH)
                .child(String.valueOf(coach.id));
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    coach.liked = true;
                } else {
                    coach.liked = false;
                }
                Drawable drawable = coach.liked ?
                        ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_pink_24px) :
                        ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_24px);
                like.setImageDrawable(drawable);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
