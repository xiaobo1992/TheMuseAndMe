package com.bobo.normalman.themuseandme.view.analytics;

import android.os.Bundle;

import com.bobo.normalman.themuseandme.model.Application;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaobozhang on 11/23/17.
 */

public class ApplicationStatusAnalytics extends PieChartAnalyticsFragment {
    public static final String KEY_TITLE = "title";
    String title;

    public static ApplicationStatusAnalytics newInstance(String title) {
        Bundle args = new Bundle();
        ApplicationStatusAnalytics fragment = new ApplicationStatusAnalytics();
        args.putString(KEY_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected List<PieEntry> countData(DataSnapshot dataSnapshot) {
        List<PieEntry> entries = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Application application = snapshot.getValue(Application.class);
            String key = application.jobStatus;
            if (!counter.containsKey(key)) {
                counter.put(key, 0);
            }
            counter.put(key, counter.get(key) + 1);
        }

        for (String key : counter.keySet()) {
            entries.add(new PieEntry(counter.get(key), key));
        }
        return entries;
    }

    public void setupConfiguration() {
        super.setupConfiguration();
        pieChart.setCenterText(title);
    }

    @Override
    public void setupChart(List<PieEntry> entries) {
        PieDataSet dataset = setupPieDataSet(entries, "Industry");
        PieData data = new PieData((dataset));
        data.setValueTextSize(15f);
        setupConfiguration();
        pieChart.setData(data);
        pieChart.invalidate();
    }
}
