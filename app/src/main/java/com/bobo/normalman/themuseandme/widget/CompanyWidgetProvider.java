package com.bobo.normalman.themuseandme.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Company;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Implementation of App Widget functionality.
 */
public class CompanyWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.company_widget);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    static void updateCompanyWidget(Context context, AppWidgetManager appWidgetManager,
                                    int appWidgetId, Company company) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.company_widget);
        if (company != null) {
            views.setTextViewText(R.id.widget_company_name, company.name);
            views.setTextViewText(R.id.widget_company_info, company.description);
            try {
                Bitmap bitmap = Picasso.with(context).load(company.getLogoImageUrl()).get();
                views.setImageViewBitmap(R.id.widget_company_logo, bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Company company) {
        for (int appWidgetId : appWidgetIds) {
            updateCompanyWidget(context, appWidgetManager, appWidgetId, company);
        }
    }
}

