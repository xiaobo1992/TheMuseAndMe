package com.bobo.normalman.themuseandme.view.application;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Application;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.bobo.normalman.themuseandme.util.DateUtil;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class ViewApplicationActivity extends ApplicationActivity {

    @BindView(R.id.application_view_comment)
    TextView comment;
    @BindView(R.id.application_view_company)
    TextView company;
    @BindView(R.id.application_view_date)
    TextView date;
    @BindView(R.id.application_view_position)
    TextView position;
    @BindView(R.id.application_view_status)
    TextView status;
    DatabaseReference ref;

    @Override
    protected void setLayout() {
        setContentView(R.layout.acticity_view_job_application);
        ButterKnife.bind(this);
    }

    @Override
    public void bindData() {
        comment.setText(application.comment);
        company.setText(application.jobCompany);
        date.setText(DateUtil.toString(application.jobApplicationDate));
        position.setText(application.jobPosition);
        status.setText(application.jobStatus);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_application, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(DBUtil.APPLICATION)
                .child(application.jobId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                application = dataSnapshot.getValue(Application.class);
                if (application != null) {
                    bindData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.edit:
                Intent intent = new Intent(this, EditApplicationActivity.class);
                intent.putExtra(KEY_APPLICATION, ModelUtil.toString(application,
                        new TypeToken<Application>() {
                        }));
                startActivity(intent);
                return true;
            case R.id.delete:
                ref.removeValue();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
