package com.bobo.normalman.themuseandme.view.list.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.task.LoadCompanyImageTask;
import com.bobo.normalman.themuseandme.task.LoadListTask;
import com.bobo.normalman.themuseandme.themuse.TheMuse;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.base.BaseListFragment;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class CompanyListFragment extends BaseListFragment {
    public static final String KEY_COMPANIES = "KEY_COMPANIES";
    public static final String KEY_LOADMORE = "KEY_LOADMORE";
    public static final String KEY_STATE = "KEY_STATE";
    public CompanyListAdapter adapter;

    public static CompanyListFragment newInstance() {
        Bundle args = new Bundle();
        CompanyListFragment fragment = new CompanyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CompanyListAdapter(new ArrayList<Company>(), new BaseListAdapter.LoadMoreListener() {
            @Override
            public void loadMore() {
                AsyncTaskCompat.executeParallel(new LoadCompanyListTask(adapter, adapter.getDataCount() / COUNT_PER_PAGE));
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadCompanyListTask(adapter, true).execute();
            }

        });

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecoration);

    }

    class LoadCompanyListTask extends LoadListTask<Company> {

        public LoadCompanyListTask(BaseListAdapter adapter, int page) {
            super(adapter, page);
        }

        public LoadCompanyListTask(BaseListAdapter adapter, boolean refresh) {
            super(adapter, 0);
            this.refreshing = refresh;
        }

        @Override
        protected List doInBackground(Void... voids) {
            try {
                return TheMuse.getCompanies(page);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Company> results) {
            super.onPostExecute(results);
            if (refreshing) {
                refreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_COMPANIES, ModelUtil.toString(adapter.data, new TypeToken<List<Company>>() {
        }));
        outState.putBoolean(KEY_LOADMORE, adapter.loadMore);
        outState.putParcelable(KEY_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            adapter.setLoadMore(savedInstanceState.getBoolean(KEY_LOADMORE));
            adapter.setData(ModelUtil.toObject(savedInstanceState.getString(KEY_COMPANIES)
                    , new TypeToken<List<Company>>() {
                    }));
            recyclerView.getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState.getParcelable(KEY_STATE));
        }
    }
}
