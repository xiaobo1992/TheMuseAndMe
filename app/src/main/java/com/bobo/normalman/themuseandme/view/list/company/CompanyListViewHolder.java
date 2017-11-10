package com.bobo.normalman.themuseandme.view.list.company;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 10/24/17.
 */

public class CompanyListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.company_name)
    TextView companyName;

    @BindView(R.id.company_avator)
    ImageView companyAvator;

    @BindView(R.id.company_industries)
    TextView companyIndustries;
    public CompanyListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
