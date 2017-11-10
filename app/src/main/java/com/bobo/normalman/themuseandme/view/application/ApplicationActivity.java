package com.bobo.normalman.themuseandme.view.application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Application;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public abstract class ApplicationActivity extends AppCompatActivity {

    public static final String KEY_EDIT_ACTIVITY = "EDIT";
    public static final String KEY_VIEW_ACTIVITY = "VIEW";
    public static final String KEY_APPLICATION = "APPLICATION";


    public String type;
    public Application application;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        setToolBar();
        application = getData();
        if (application != null) {
            bindData();
        }
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    protected abstract void setLayout();

    public abstract void bindData();

    public Application getData() {
        return ModelUtil.toObject(getIntent().getStringExtra(KEY_APPLICATION),
                new TypeToken<Application>() {
                });
    }

}
