package com.bobo.normalman.themuseandme.view.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.profile.coach.CoachProductFragment;
import com.bobo.normalman.themuseandme.view.profile.coach.CoachProfileFragment;
import com.bobo.normalman.themuseandme.view.profile.coach.CoachWebFragment;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 10/28/17.
 */

public class CoachProfileActivity extends AppCompatActivity {

    public static final String KEY_COACH = "KEY_COACH";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomNavView)
    BottomNavigationView bottomNavigationView;

    private Coach coach;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);
        ButterKnife.bind(this);
        coach = getData();
        setupHomeButton();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, CoachProfileFragment.newInstance(coach))
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.profile:
                        fragment = CoachProfileFragment.newInstance(coach);
                        break;
                    case R.id.product:
                        fragment = CoachProductFragment.newInstance(coach);
                        break;
                    case R.id.web:
                        fragment = CoachWebFragment.newInstance(coach);
                }
                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();

                }
                return true;
            }
        });
    }

    private Coach getData() {
        return ModelUtil.toObject(getIntent().getStringExtra(KEY_COACH), new TypeToken<Coach>() {
        });
    }


    private void setupHomeButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(coach.name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
