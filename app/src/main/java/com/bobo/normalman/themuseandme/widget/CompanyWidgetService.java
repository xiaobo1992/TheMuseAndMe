package com.bobo.normalman.themuseandme.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.google.gson.reflect.TypeToken;

/**
 * Created by xiaobozhang on 10/31/17.
 */

public class CompanyWidgetService extends IntentService {
    public static final String ACTION_UPDATE_COMPANY = "UPDATE_COMPANY";
    public static final String KEY_COMPANY = "KEY_COMPANY";

    public CompanyWidgetService() {
        super("Company Widget");
    }

    public CompanyWidgetService(String name) {
        super(name);
    }

    public static void startUpdateCompany(Context context, Company company) {
        Intent intent = new Intent(context, CompanyWidgetService.class);
        intent.putExtra(KEY_COMPANY, ModelUtil.toString(company, new TypeToken<Company>() {
        }));
        intent.setAction(ACTION_UPDATE_COMPANY);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            switch (intent.getAction()) {
                case ACTION_UPDATE_COMPANY:
                    updateCompany(intent);
            }
        }
    }

    public void updateCompany(Intent intent) {
        Company company = ModelUtil.toObject(intent.getStringExtra(KEY_COMPANY), new TypeToken<Company>() {
        });
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CompanyWidgetProvider.class));
        CompanyWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds, company);
    }
}
