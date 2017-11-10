package com.bobo.normalman.themuseandme.view.profile;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Post;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/7/17.
 */

public class PostProfileActivity extends BaseProfileActivity<Post> {

    public static final String KEY_POST = "KEY_POST";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LANDING_PAGE = "landing_page";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.post_profile_avator)
    SimpleDraweeView avator;
    @BindView(R.id.post_profile_like)
    ImageView like;
    @BindView(R.id.post_profile_name)
    TextView name;
    DatabaseReference mRef;

    @Override
    protected void bindView() {
        name.setText(data.name);
        avator.setImageURI(data.author.refs.get(KEY_IMAGE));
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.liked) {
                    DBUtil.savePost(data);
                } else {
                    DBUtil.removePost(data);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(data.getRefs().get(KEY_LANDING_PAGE));

    }

    @Override
    public Post getData() {
        return ModelUtil.toObject(getIntent().getStringExtra(KEY_POST), new TypeToken<Post>() {
        });
    }

    @Override
    public void setupLayout() {
        setContentView(R.layout.activity_post_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void setupHomeButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(DBUtil.POST)
                .child(String.valueOf(data.id));
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    data.liked = true;
                } else {
                    data.liked = false;
                }

                Drawable drawable = data.liked ?
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_pink_24px) :
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24px);
                like.setImageDrawable(drawable);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
