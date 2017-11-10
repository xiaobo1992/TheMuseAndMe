package com.bobo.normalman.themuseandme.view.list.posts;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Post;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.base.BaseListAdapter;
import com.bobo.normalman.themuseandme.view.profile.PostProfileActivity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by xiaobozhang on 10/24/17.
 */

public class PostListAdapter extends BaseListAdapter<Post> {
    public String KEY_IMAGE = "image";

    public PostListAdapter(List<Post> data, LoadMoreListener loadMoreListener) {
        super(data, loadMoreListener);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final Post item) {
        final PostListViewHolder viewHolder = (PostListViewHolder) holder;
        viewHolder.author.setText(item.author.name);
        viewHolder.title.setText(item.name);
        viewHolder.authorImage.setImageURI(item.author.refs.get(KEY_IMAGE));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Context context = viewHolder.itemView.getContext();
                Intent intent = new Intent(context, PostProfileActivity.class);
                intent.putExtra(PostProfileActivity.KEY_POST, ModelUtil.toString(item, new TypeToken<Post>() {
                }));
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder setupViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_post, parent, false);
        return new PostListViewHolder(view);
    }
}
