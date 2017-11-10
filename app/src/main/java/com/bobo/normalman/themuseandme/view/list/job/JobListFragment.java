package com.bobo.normalman.themuseandme.view.list.job;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bobo.normalman.themuseandme.model.Job;
import com.bobo.normalman.themuseandme.task.LoadListTask;
import com.bobo.normalman.themuseandme.themuse.TheMuse;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.base.BaseListFragment;
import com.bobo.normalman.themuseandme.view.profile.CompanyProfileActivity;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class JobListFragment extends BaseListFragment {
    public static final String KEY_JOBS = "KEY_JOBS";
    public static final String KEY_LOADMORE = "KEY_LOADMORE";
    public static final String KEY_STATE = "KEY_STATE";
    public JobListAdapter adapter;

    public static JobListFragment newInstance() {
        Bundle args = new Bundle();
        JobListFragment fragment = new JobListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JobListFragment newInstance(String company) {
        Bundle args = new Bundle();
        JobListFragment fragment = new JobListFragment();
        args.putString(CompanyProfileActivity.KEY_COMPANY, company);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final String companyName = getArguments().getString(CompanyProfileActivity.KEY_COMPANY, null);
        adapter = new JobListAdapter(new ArrayList<Job>(), new BaseListAdapter.LoadMoreListener() {
            @Override
            public void loadMore() {
                AsyncTaskCompat.executeParallel(new LoadJobListTask(adapter,
                        adapter.getDataCount() / COUNT_PER_PAGE, companyName));
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecoration);
    }

    class LoadJobListTask extends LoadListTask<Job> {
        String company;

        public LoadJobListTask(BaseListAdapter adapter, int page) {
            super(adapter, page);
        }


        public LoadJobListTask(BaseListAdapter adapter, int page, String company) {
            super(adapter, page);
            this.company = company;
        }

        @Override
        protected List<Job> doInBackground(Void... voids) {
            try {
                if (company == null) {
                    return TheMuse.getJobs(page);
                } else {
                    return TheMuse.getJobsByCompany(company, page);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_JOBS, ModelUtil.toString(adapter.data, new TypeToken<List<Job>>() {
        }));
        outState.putBoolean(KEY_LOADMORE, adapter.loadMore);
        outState.putParcelable(KEY_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            adapter.setLoadMore(savedInstanceState.getBoolean(KEY_LOADMORE));
            adapter.setData(ModelUtil.toObject(savedInstanceState.getString(KEY_JOBS)
                    , new TypeToken<List<Job>>() {
                    }));
            recyclerView.getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState.getParcelable(KEY_STATE));
        }
    }
}
