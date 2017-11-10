package com.bobo.normalman.themuseandme.view.application;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.dialog.DateDialogFragment;
import com.bobo.normalman.themuseandme.dialog.StatusDialogFragment;
import com.bobo.normalman.themuseandme.model.Application;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.bobo.normalman.themuseandme.util.DateUtil;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class EditApplicationActivity extends ApplicationActivity implements StatusDialogFragment.onStatusSelectListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.application_edit_comment)
    EditText comment;
    @BindView(R.id.application_edit_company)
    EditText company;
    @BindView(R.id.application_edit_date)
    EditText date;
    @BindView(R.id.application_edit_position)
    EditText position;
    @BindView(R.id.application_edit_status)
    EditText status;

    public static final String TAG_STATUS = "STATUS_PICKER";
    public static final String TAG_DATE = "DATE_PICKER";

    @Override
    protected void setLayout() {
        setContentView(R.layout.acticity_edit_job_application);
        ButterKnife.bind(this);
    }

    @Override
    public void bindData() {
        comment.setText(application.comment);
        company.setText(application.jobCompany);
        position.setText(application.jobPosition);
        status.setText(application.jobStatus);
        date.setText(DateUtil.toString(application.jobApplicationDate));
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatusDialogFragment fragment = new StatusDialogFragment();
                fragment.show(getFragmentManager(), TAG_STATUS);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialogFragment fragment = new DateDialogFragment();
                fragment.show(getFragmentManager(), TAG_DATE);
            }
        });
    }

    @Override
    public Application getData() {
        return ModelUtil.toObject(getIntent().getStringExtra(KEY_APPLICATION),
                new TypeToken<Application>() {
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save:
                updateApplication();
                DBUtil.saveApplication(application);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateApplication() {
        application.jobPosition = position.getText().toString();
        application.jobCompany = company.getText().toString();
        application.comment = comment.getText().toString();
        application.jobStatus = status.getText().toString();
        try {
            application.jobApplicationDate = DateUtil.toLong(date.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusSelectListener(String item) {
        status.setText(item);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = sdf.format(c.getTime());
        date.setText(formattedDate);
    }
}
