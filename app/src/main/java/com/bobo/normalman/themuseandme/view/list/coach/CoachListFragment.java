package com.bobo.normalman.themuseandme.view.list.coach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.model.Company;
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
 * Created by xiaobozhang on 10/24/17.
 */

public class CoachListFragment extends BaseListFragment {

    private static final String KEY_COACHES = "KEY_COACHES";
    private static final String KEY_LOADMORE = "KEY_LOADMORE";
    private static final String KEY_STATE = "KEY_STATE";
    protected CoachListAdapter adapter;

    public static CoachListFragment newInstance() {
        Bundle args = new Bundle();
        CoachListFragment fragment = new CoachListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CoachListAdapter(new ArrayList<Coach>(), new BaseListAdapter.LoadMoreListener() {
            @Override
            public void loadMore() {
                AsyncTaskCompat.executeParallel(new LoadCoachListTask(adapter, adapter.getDataCount() / COUNT_PER_PAGE));
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadCoachListTask(adapter, true).execute();
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecoration);
    }

    class LoadCoachListTask extends LoadListTask<Coach> {


        public LoadCoachListTask(BaseListAdapter adapter, int page) {
            super(adapter, page);
        }

        public LoadCoachListTask(BaseListAdapter adapter, boolean refreshing) {
            super(adapter, 0);
            this.refreshing = refreshing;
        }

        @Override
        protected List<Coach> doInBackground(Void... voids) {
            try {
                return TheMuse.getCoaches(page);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Coach> coaches) {
            super.onPostExecute(coaches);
            if (refreshing) {
                refreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_COACHES, ModelUtil.toString(adapter.data, new TypeToken<List<Coach>>() {
        }));
        outState.putBoolean(KEY_LOADMORE, adapter.loadMore);
        outState.putParcelable(KEY_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            adapter.setLoadMore(savedInstanceState.getBoolean(KEY_LOADMORE));
            adapter.setData(ModelUtil.toObject(savedInstanceState.getString(KEY_COACHES)
                    , new TypeToken<List<Coach>>() {
                    }));
            recyclerView.getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState.getParcelable(KEY_STATE));
        }
        super.onViewStateRestored(savedInstanceState);
    }
}
