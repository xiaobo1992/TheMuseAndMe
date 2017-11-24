package com.bobo.normalman.themuseandme.view.analytics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.util.DBUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/23/17.
 */

public abstract class PieChartAnalyticsFragment extends Fragment {

    @BindView(R.id.pie_chart)
    PieChart pieChart;

    DatabaseReference ref;
    HashMap<String, Integer> counter = new HashMap<>();

    @Override
    public void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(DBUtil.APPLICATION);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PieEntry> entries = countData(dataSnapshot);
                setupChart(entries);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    protected abstract List<PieEntry> countData(DataSnapshot dataSnapshot);

    public void setupConfiguration() {
        pieChart.setUsePercentValues(true);
        pieChart.setCenterTextSize(20f);
        pieChart.getDescription().setEnabled(false);
    }

    public PieDataSet setupPieDataSet(List<PieEntry> entries, String name) {
        PieDataSet dataset = new PieDataSet(entries, name);
        dataset.setSelectionShift(3f);
        dataset.setSliceSpace(5f);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        return dataset;
    }

    public abstract void setupChart(List<PieEntry> entries);

}
