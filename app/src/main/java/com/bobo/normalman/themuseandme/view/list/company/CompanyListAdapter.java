package com.bobo.normalman.themuseandme.view.list.company;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.util.ImageUtil;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.profile.CompanyProfileActivity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by xiaobozhang on 10/24/17.
 */

public class CompanyListAdapter extends BaseListAdapter<Company> {
    public CompanyListAdapter(List<Company> data, LoadMoreListener loadMoreListener) {
        super(data, loadMoreListener);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final Company item) {
        final CompanyListViewHolder viewHolder = (CompanyListViewHolder) holder;
        viewHolder.companyName.setText(item.name);
        viewHolder.companyIndustries.setText(item.getIndustriesContent());
        ImageUtil.loadImage(viewHolder.itemView.getContext(), viewHolder.companyAvator, item.getLogoImageUrl());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Context context = viewHolder.itemView.getContext();
                Intent intent = new Intent(context, CompanyProfileActivity.class);
                intent.putExtra(CompanyProfileActivity.KEY_COMPANY,
                        ModelUtil.toString(item, new TypeToken<Company>() {
                        }));
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) context, viewHolder.companyAvator,
                                context.getString(R.string.logo_transition));
                context.startActivity(intent, options.toBundle());
                //context.startActivity(intent);
            }
        });
    }


    @Override
    protected RecyclerView.ViewHolder setupViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_company, parent, false);
        return new CompanyListViewHolder(view);

    }
}
