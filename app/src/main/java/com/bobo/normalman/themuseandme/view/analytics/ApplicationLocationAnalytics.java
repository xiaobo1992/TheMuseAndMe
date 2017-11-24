package com.bobo.normalman.themuseandme.view.analytics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Job;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaobozhang on 11/23/17.
 */

public class ApplicationLocationAnalytics extends PieChartAnalyticsFragment {

    public static final String KEY_JOB = "job";
    public static final String KEY_TITLE = "title";

    String title;

    public static ApplicationLocationAnalytics newInstance(String title) {
        Bundle args = new Bundle();
        ApplicationLocationAnalytics fragment = new ApplicationLocationAnalytics();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = getArguments().getString(KEY_TITLE);
    }

    @Override
    protected List<PieEntry> countData(DataSnapshot dataSnapshot) {
        List<PieEntry> entries = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Job job = snapshot.child(KEY_JOB).getValue(Job.class);
            String[] locations = job.getLocation().split("&");
            for (int i = 0; i < locations.length; i++) {
                String location = locations[i].trim();
                if (!counter.containsKey(location)) {
                    counter.put(location, 0);
                }
                counter.put(location, counter.get(location) + 1);
            }
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
        PieDataSet dataset = setupPieDataSet(entries, getString(R.string.location));
        PieData data = new PieData((dataset));
        data.setValueTextSize(15f);
        setupConfiguration();
        pieChart.setData(data);
        pieChart.invalidate();
    }


}
