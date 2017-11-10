package com.bobo.normalman.themuseandme.view.list.coach;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.profile.CoachProfileActivity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by xiaobozhang on 10/24/17.
 */

public class CoachListAdapter extends BaseListAdapter<Coach> {

    public CoachListAdapter(List<Coach> data, LoadMoreListener loadMoreListener) {
        super(data, loadMoreListener);
    }

    @Override
    protected void bindView(final RecyclerView.ViewHolder holder, final Coach item) {
        final CoachListViewHolder viewHolder = (CoachListViewHolder) holder;
        viewHolder.coachName.setText(item.name);
        viewHolder.specializations.setText(item.getSpecializationsContent());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = viewHolder.itemView.getContext();
                Intent intent = new Intent(context, CoachProfileActivity.class);
                intent.putExtra(CoachProfileActivity.KEY_COACH,
                        ModelUtil.toString(item, new TypeToken<Coach>() {
                }));
                context.startActivity(intent);
            }
        });
        viewHolder.imageView.setImageURI(item.getProfileImage());
        //ImageUtil.loadImage(holder.itemView.getContext(), viewHolder.imageView, item.getProfileImage());
    }

    @Override
    protected RecyclerView.ViewHolder setupViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_coach, parent, false);
        return new CoachListViewHolder(view);
    }
}
