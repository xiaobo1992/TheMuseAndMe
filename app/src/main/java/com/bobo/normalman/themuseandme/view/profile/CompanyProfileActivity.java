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
import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.list.job.JobListFragment;
import com.bobo.normalman.themuseandme.view.profile.company.CompanyLocationFragment;
import com.bobo.normalman.themuseandme.view.profile.company.CompanyProfileFragment;
import com.bobo.normalman.themuseandme.widget.CompanyWidgetService;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 10/25/17.
 */

public class CompanyProfileActivity extends AppCompatActivity {

    public static final String KEY_COMPANY = "KEY_COMPANY";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottomNavView)
    BottomNavigationView bottomNavigationView;

    Company company;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        ButterKnife.bind(this);
        company = getData();
        setupAppBar();
        setupWidget();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, CompanyProfileFragment.newInstance(company))
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.profile:
                        fragment = CompanyProfileFragment.newInstance(company);
                        break;
                    case R.id.job:
                        fragment = JobListFragment.newInstance(company.name);
                        break;
                    case R.id.location:
                        fragment = CompanyLocationFragment.newInstance(company);
                        break;
                    default:
                        fragment = null;
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

    public Company getData() {
        return ModelUtil.toObject(getIntent().getStringExtra(KEY_COMPANY), new TypeToken<Company>() {
        });
    }

    public void setupAppBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(company.name);
    }

    public void setupWidget() {
        CompanyWidgetService.startUpdateCompany(this, company);
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
