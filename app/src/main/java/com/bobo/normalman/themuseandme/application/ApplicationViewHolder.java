package com.bobo.normalman.themuseandme.application;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class ApplicationViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.application_list_company)
    TextView company;
    @BindView(R.id.application_list_position)
    TextView position;
    @BindView(R.id.application_list_status)
    TextView status;
    public ApplicationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
