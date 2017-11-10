package com.bobo.normalman.themuseandme.view.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public abstract class BaseListFragment extends Fragment {
    public static int COUNT_PER_PAGE = 20;

    protected @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        Fresco.initialize(view.getContext());
        return view;
    }
}
