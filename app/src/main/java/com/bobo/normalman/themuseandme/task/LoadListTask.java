package com.bobo.normalman.themuseandme.task;

import android.os.AsyncTask;

import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.base.BaseListFragment;

import java.util.List;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public abstract class LoadListTask<T> extends AsyncTask<Void, Void, List<T>> {
    protected BaseListAdapter adapter;
    protected int page;

    public LoadListTask(BaseListAdapter adapter, int page) {
        this.adapter = adapter;
        this.page = page;
    }

    @Override
    protected abstract List<T> doInBackground(Void... voids);

    @Override
    protected void onPostExecute(List<T> results) {
        if (results != null) {
            adapter.appendData(results);
            adapter.setLoadMore(results.size() == BaseListFragment.COUNT_PER_PAGE);
        }
    }
}
