package com.bobo.normalman.themuseandme.util;

import android.content.Context;
import android.support.v4.os.AsyncTaskCompat;
import android.widget.ImageView;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.task.LoadCompanyImageTask;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

/**
 * Created by xiaobozhang on 10/24/17.
 */

public class ImageUtil {


    public static void loadCompanyImage(final Context context, SimpleDraweeView imageView, final String companyId) {
        AsyncTaskCompat.executeParallel(new LoadCompanyImageTask(imageView, companyId));
    }

    public static void loadWidgetImage(Context context, ImageView imageView, String url) {
        Picasso.with(context).load(url).into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_add_a_photo_black_24px)
                .error(R.drawable.ic_add_a_photo_black_24px)
                .into(imageView);
    }
}
