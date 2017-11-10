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

import com.bobo.normalman.themuseandme.view.favourite.FavouriteCoachFragment;
import com.bobo.normalman.themuseandme.view.favourite.FavouriteCompanyFragment;
import com.bobo.normalman.themuseandme.view.favourite.FavouriteJobFragment;
import com.bobo.normalman.themuseandme.view.favourite.FavouritePostFragment;
import com.hisham.jazzyviewpagerlib.JazzyViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/7/17.
 */

public class FavouriteFragment extends Fragment {

    @BindView(R.id.viewPager)
    JazzyViewPager viewPager;

    @BindView(R.id.tab)
    TabLayout tab;

    public static FavouriteFragment newInstance() {
        Bundle args = new Bundle();
        FavouriteFragment fragment = new FavouriteFragment();
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
        viewPager.setAdapter(new FavouriteFragment.PageAdapter(getChildFragmentManager()));
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
                    return FavouriteCompanyFragment.newInstance();
                case 1:
                    return FavouriteJobFragment.newInstance();
                case 2:
                    return FavouriteCoachFragment.newInstance();
                case 3:
                    return FavouritePostFragment.newInstance();
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
