package com.bobo.normalman.themuseandme.view.favourite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.list.company.CompanyListAdapter;
import com.bobo.normalman.themuseandme.view.list.company.CompanyListFragment;
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

public class FavouriteCompanyFragment extends CompanyListFragment {

    DatabaseReference ref;

    public static FavouriteCompanyFragment newInstance() {
        Bundle args = new Bundle();
        FavouriteCompanyFragment fragment = new FavouriteCompanyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CompanyListAdapter(new ArrayList<Company>(), new BaseListAdapter.LoadMoreListener() {
            @Override
            public void loadMore() {
                return;
            }
        });
        refreshLayout.setEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    public void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid()).child(DBUtil.COMPANY);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Company> companys = new ArrayList<Company>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Company company = postSnapshot.getValue(Company.class);
                    companys.add(company);
                }
                adapter.setData(companys);
                adapter.loadMore = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
