package com.bobo.normalman.themuseandme.view.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by xiaobozhang on 10/25/17.
 */

public abstract class BaseProfileActivity<T> extends AppCompatActivity {
    final String KEY_DATA = "KEY_DATA";
    T data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLayout();
        data = getData();
        bindView();
        setupHomeButton();
    }

    protected abstract void bindView();

    protected abstract T getData();

    protected abstract void setupLayout();

    protected abstract void setupHomeButton();

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
