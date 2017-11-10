package com.bobo.normalman.themuseandme;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.util.ImageUtil;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    FirebaseUser user = null;
    ActionBarDrawerToggle drawerToggle;
    @BindView(R.id.navView)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, MainFragment.newInstance())
                    .commit();
        }
        verify();
        setToolbar();
        setupNavDrawer();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle(getString(R.string.app_name));
    }

    public void verify() {
        user = MyAuth.getFireBaseUser();
    }

    public void setupNavDrawer() {
        setNavHeader();
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.main:
                        fragment = MainFragment.newInstance();
                        setTitle(getString(R.string.app_name));
                        break;
                    case R.id.favourite:
                        fragment = FavouriteFragment.newInstance();
                        setTitle(getString(R.string.favourite));
                        break;
                    case R.id.application:
                        fragment = ApplicationFragment.newInstance();
                        setTitle(getString(R.string.application));
                        break;
                }
                drawerLayout.closeDrawers();

                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });

        drawerLayout.setDrawerListener(drawerToggle);

    }

    public void setNavHeader() {
        View headerLayout = navigationView.getHeaderView(0);

        ImageUtil.loadImage(getApplicationContext(),
                (ImageView) headerLayout.findViewById(R.id.avator), user.getPhotoUrl().toString());

        TextView acctName = headerLayout.findViewById(R.id.account_name);
        acctName.setText(user.getEmail());

        TextView logout = headerLayout.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAuth.signOut();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
