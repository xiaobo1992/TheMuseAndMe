package com.bobo.normalman.themuseandme.view.list.job;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Job;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.profile.JobProfileActivity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by xiaobozhang on 10/24/17.
 */

public class JobListAdapter extends BaseListAdapter<Job> {
    public JobListAdapter(List<Job> data, LoadMoreListener loadMoreListener) {
        super(data, loadMoreListener);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final Job item) {
        final JobListViewHolder viewHolder = (JobListViewHolder) holder;
        viewHolder.position.setText(item.name);
        viewHolder.location.setText(item.getLocation());
        viewHolder.company.setText(item.getCompanyName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = viewHolder.itemView.getContext();
                Intent intent = new Intent(context, JobProfileActivity.class);
                intent.putExtra(JobProfileActivity.KEY_JOB, ModelUtil.toString(item, new TypeToken<Job>() {
                }));
                context.startActivity(intent);

            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder setupViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, parent, false);
        return new JobListViewHolder(view);
    }
}
