package com.bobo.normalman.themuseandme.view.list.job;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 10/24/17.
 */

public class JobListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.job_location)
    TextView location;
    @BindView(R.id.job_position)
    TextView position;
    @BindView(R.id.job_company)
    TextView company;

    public JobListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
