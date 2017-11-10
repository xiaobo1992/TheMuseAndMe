package com.bobo.normalman.themuseandme.view.list.coach;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 10/24/17.
 */

class CoachListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.coach_avator)
    SimpleDraweeView imageView;
    @BindView(R.id.coach_name)
    TextView coachName;
    @BindView(R.id.coach_specializations)
    TextView specializations;

    public CoachListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
