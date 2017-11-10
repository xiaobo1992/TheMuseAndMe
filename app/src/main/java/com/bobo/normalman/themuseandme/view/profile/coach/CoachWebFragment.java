package com.bobo.normalman.themuseandme.view.profile.coach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.bobo.normalman.themuseandme.view.profile.CoachProfileActivity;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozhang on 11/7/17.
 */

public class CoachWebFragment extends Fragment {
    @BindView(R.id.webView)
    WebView webView;
    private Coach coach;
    public static CoachWebFragment newInstance(Coach coach) {
        Bundle args = new Bundle();
        CoachWebFragment fragment = new CoachWebFragment();
        args.putString(CoachProfileActivity.KEY_COACH,
                ModelUtil.toString(coach, new TypeToken<Coach>() {
                }));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        coach = ModelUtil.toObject(getArguments().getString(CoachProfileActivity.KEY_COACH),
                new TypeToken<Coach>() {
                });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(coach.getLink());
    }
}
