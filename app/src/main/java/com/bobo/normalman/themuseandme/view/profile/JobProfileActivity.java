package com.bobo.normalman.themuseandme.view.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Application;
import com.bobo.normalman.themuseandme.model.Job;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.bobo.normalman.themuseandme.util.ImageUtil;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.application.ApplicationActivity;
import com.bobo.normalman.themuseandme.view.application.EditApplicationActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 10/25/17.
 */

public class JobProfileActivity extends BaseProfileActivity<Job> {
    public static String KEY_JOB = "KEY_JOB";

    public static int KEY_ACTIVITY_EDIT = 100;

    @BindView(R.id.job_level)
    TextView level;
    @BindView(R.id.job_description)
    TextView description;
    @BindView(R.id.job_title)
    TextView title;
    @BindView(R.id.job_location)
    TextView location;
    @BindView(R.id.job_company_logo)
    SimpleDraweeView logo;
    @BindView(R.id.job_like)
    ImageView like;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private DatabaseReference mRef;

    @Override
    protected void bindView() {
        level.setText(data.getLevelName());
        description.setText(Html.fromHtml(data.contents));
        title.setText(data.name);
        location.setText(data.getLocation());
        ImageUtil.loadCompanyImage(getApplicationContext(), logo, data.getCompanyID());
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.liked) {
                    DBUtil.saveJob(data);
                } else {
                    DBUtil.removeJob(data);
                }
            }
        });
    }

    @Override
    public Job getData() {
        return ModelUtil.toObject(getIntent().getStringExtra(KEY_JOB), new TypeToken<Job>() {
        });
    }

    @Override
    public void setupLayout() {
        setContentView(R.layout.activity_job_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void setupHomeButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(data.name);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mRef = FirebaseDatabase.getInstance()
                .getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(DBUtil.JOB)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KEY_ACTIVITY_EDIT && requestCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.add:
                Intent intent = new Intent(this, EditApplicationActivity.class);
                Application application = new Application();
                application.jobId = String.valueOf(data.id);
                application.jobPosition = data.name;
                application.jobCompany = data.getCompanyName();
                application.jobStatus = Application.Status.Applied.name();
                application.jobApplicationDate = new Date().getTime();
                application.comment = "";
                intent.putExtra(ApplicationActivity.KEY_APPLICATION,
                        ModelUtil.toString(application, new TypeToken<Application>() {
                        }));
                startActivityForResult(intent, KEY_ACTIVITY_EDIT);

        }
        return super.onOptionsItemSelected(item);
    }
}
