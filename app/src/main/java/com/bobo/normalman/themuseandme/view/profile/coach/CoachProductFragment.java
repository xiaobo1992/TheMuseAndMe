package com.bobo.normalman.themuseandme.view.profile.coach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.profile.CoachProfileActivity;
import com.bobo.normalman.themuseandme.view.profile.ProductAdapter;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/7/17.
 */

public class CoachProductFragment extends Fragment {

    private Coach coach;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static CoachProductFragment newInstance(Coach coach) {

        Bundle args = new Bundle();
        CoachProductFragment fragment = new CoachProductFragment();
        args.putString(CoachProfileActivity.KEY_COACH,
                ModelUtil.toString(coach, new TypeToken<Coach>() {
                }));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        coach = ModelUtil.toObject(getArguments().getString(CoachProfileActivity.KEY_COACH),
                new TypeToken<Coach>() {
                });
        ProductAdapter adapter = new ProductAdapter(coach);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
