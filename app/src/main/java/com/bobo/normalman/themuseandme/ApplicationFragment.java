package com.bobo.normalman.themuseandme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.application.ApplicationAdapter;
import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Application;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class ApplicationFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    DatabaseReference ref;
    ApplicationAdapter adapter;

    public static ApplicationFragment newInstance() {
        Bundle args = new Bundle();
        ApplicationFragment fragment = new ApplicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adapter = new ApplicationAdapter(new ArrayList<Application>());
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid()).child(DBUtil.APPLICATION);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Application> applications = new ArrayList<Application>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Application application = postSnapshot.getValue(Application.class);
                    applications.add(application);
                }
                adapter.setData(applications);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
