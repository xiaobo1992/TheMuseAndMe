package com.bobo.normalman.themuseandme.view.list.posts;

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

public class PostListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.post_author)
    TextView author;
    @BindView(R.id.post_author_image)
    SimpleDraweeView authorImage;
    @BindView(R.id.post_title)
    TextView title;

    public PostListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
