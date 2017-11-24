package com.bobo.normalman.themuseandme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.normalman.themuseandme.view.analytics.ApplicationLevelAnalytics;
import com.bobo.normalman.themuseandme.view.analytics.ApplicationLocationAnalytics;
import com.bobo.normalman.themuseandme.view.analytics.ApplicationStatusAnalytics;
import com.hisham.jazzyviewpagerlib.JazzyViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/22/17.
 */

public class AnalyticFragment extends Fragment {

    @BindView(R.id.viewPager)
    JazzyViewPager viewPager;

    @BindView(R.id.tab)
    TabLayout tab;
    public static AnalyticFragment newInstance() {
        Bundle args = new Bundle();
        AnalyticFragment fragment = new AnalyticFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewPager.setAdapter(new PageAdapter(getChildFragmentManager()));
        viewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.ZoomIn);
        tab.setupWithViewPager(viewPager);
    }

    class PageAdapter extends FragmentStatePagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ApplicationLevelAnalytics.newInstance("Job Application Level Analytics");
                case 1:
                    return ApplicationLocationAnalytics.newInstance("Job Application Location Analytics");
                case 2:
                    return ApplicationStatusAnalytics.newInstance("Job Application Stattus Analytics");
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.level);
                case 1:
                    return getString(R.string.location);
                case 2:
                    return getString(R.string.status);
            }
            return super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            viewPager.setObjectForPosition(obj, position);
            return obj;
        }
    }

}
