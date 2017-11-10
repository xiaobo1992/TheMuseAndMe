package com.bobo.normalman.themuseandme.application;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Application;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.application.ApplicationActivity;
import com.bobo.normalman.themuseandme.view.application.ViewApplicationActivity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class ApplicationAdapter extends RecyclerView.Adapter {
    List<Application> applications;

    public ApplicationAdapter(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_application, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Application application = applications.get(position);
        final ApplicationViewHolder viewHolder = (ApplicationViewHolder) holder;
        viewHolder.position.setText(application.jobPosition);
        viewHolder.company.setText(application.jobCompany);
        viewHolder.status.setText(application.jobStatus);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = viewHolder.itemView.getContext();
                Intent intent = new Intent(context, ViewApplicationActivity.class);
                intent.putExtra(ApplicationActivity.KEY_APPLICATION, ModelUtil.toString(application,
                        new TypeToken<Application>() {
                        }));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public void setData(List<Application> data) {
        applications.clear();
        applications.addAll(data);
        notifyDataSetChanged();
    }

}
