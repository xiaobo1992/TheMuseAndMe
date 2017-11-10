package com.bobo.normalman.themuseandme.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;

import java.util.List;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public abstract class BaseListAdapter<T> extends RecyclerView.Adapter {

    public List<T> data;
    public boolean loadMore;
    private LoadMoreListener loadMoreListener;

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    protected BaseListAdapter(List<T> data, LoadMoreListener loadMoreListener) {
        this.data = data;
        this.loadMore = true;
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            case VIEW_TYPE_ITEM:
                RecyclerView.ViewHolder viewHolder = setupViewHolder(parent);
                return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_LOADING:
                if (loadMore) {
                    loadMoreListener.loadMore();
                }
                return;
            case VIEW_TYPE_ITEM:
                T item = data.get(position);
                bindView(holder, item);
                return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return loadMore ? data.size() + 1 : data.size();
    }


    public int getDataCount() {
        return data.size();
    }

    public void appendData(List<T> newData) {
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void setData(List<T> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
        notifyDataSetChanged();
    }

    public interface LoadMoreListener {
        void loadMore();
    }

    protected abstract void bindView(RecyclerView.ViewHolder holder, T item);

    protected abstract RecyclerView.ViewHolder setupViewHolder(ViewGroup parent);
}
