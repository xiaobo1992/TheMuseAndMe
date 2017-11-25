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

import com.bobo.normalman.themuseandme.view.list.coach.CoachListFragment;
import com.bobo.normalman.themuseandme.view.list.company.CompanyListFragment;
import com.bobo.normalman.themuseandme.view.list.job.JobListFragment;
import com.bobo.normalman.themuseandme.view.list.posts.PostListFragment;
import com.hisham.jazzyviewpagerlib.JazzyViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class MainFragment extends Fragment {

    @BindView(R.id.viewPager)
    JazzyViewPager viewPager;

    @BindView(R.id.tab)
    TabLayout tab;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
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
                    return CompanyListFragment.newInstance();
                case 1:
                    return JobListFragment.newInstance();
                case 2:
                    return CoachListFragment.newInstance();
                case 3:
                    return PostListFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.company);
                case 1:
                    return getString(R.string.job);
                case 2:
                    return getString(R.string.coach);
                case 3:
                    return getString(R.string.posts);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            viewPager.setObjectForPosition(obj, position);
            return obj;
        }
    }

}
