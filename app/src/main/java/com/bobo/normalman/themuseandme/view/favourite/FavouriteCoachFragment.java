package com.bobo.normalman.themuseandme.view.favourite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.list.coach.CoachListAdapter;
import com.bobo.normalman.themuseandme.view.list.coach.CoachListFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaobozhang on 11/7/17.
 */

public class FavouriteCoachFragment extends CoachListFragment {

    DatabaseReference ref;

    public static FavouriteCoachFragment newInstance() {
        Bundle args = new Bundle();
        FavouriteCoachFragment fragment = new FavouriteCoachFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CoachListAdapter(new ArrayList<Coach>(), new BaseListAdapter.LoadMoreListener() {
            @Override
            public void loadMore() {
                return;
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid()).child(DBUtil.COACH);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Coach> coaches = new ArrayList<Coach>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Coach coach = postSnapshot.getValue(Coach.class);
                    coaches.add(coach);
                }
                adapter.setData(coaches);
                adapter.loadMore = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
