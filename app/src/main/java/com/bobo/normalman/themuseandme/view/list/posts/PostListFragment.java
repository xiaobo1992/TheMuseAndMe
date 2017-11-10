package com.bobo.normalman.themuseandme.view.list.posts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bobo.normalman.themuseandme.model.Post;
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

public class PostListFragment extends BaseListFragment {

    public static final String KEY_POSTS = "KEY_POSTS";
    public static final String KEY_LOADMORE = "KEY_LOADMORE";
    public static final String KEY_STATE = "KEY_STATE";

    public PostListAdapter adapter;

    public static PostListFragment newInstance() {
        Bundle args = new Bundle();
        PostListFragment fragment = new PostListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostListAdapter(new ArrayList<Post>(), new BaseListAdapter.LoadMoreListener() {
            @Override
            public void loadMore() {
                AsyncTaskCompat.executeParallel(new LoadPostTask(adapter, adapter.getDataCount() / COUNT_PER_PAGE));
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecoration);
    }

    class LoadPostTask extends LoadListTask<Post> {

        public LoadPostTask(BaseListAdapter adapter, int page) {
            super(adapter, page);
        }

        @Override
        protected List<Post> doInBackground(Void... voids) {
            try {
                return TheMuse.getPosts(page);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_POSTS, ModelUtil.toString(adapter.data, new TypeToken<List<Post>>() {
        }));
        outState.putBoolean(KEY_LOADMORE, adapter.loadMore);
        outState.putParcelable(KEY_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            adapter.setLoadMore(savedInstanceState.getBoolean(KEY_LOADMORE));
            adapter.setData(ModelUtil.toObject(savedInstanceState.getString(KEY_POSTS)
                    , new TypeToken<List<Post>>() {
                    }));
            recyclerView.getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState.getParcelable(KEY_STATE));
        }
    }
}
