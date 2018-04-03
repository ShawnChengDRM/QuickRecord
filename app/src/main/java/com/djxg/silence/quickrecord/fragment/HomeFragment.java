package com.djxg.silence.quickrecord.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.adapter.FeaturesAdapter;
import com.djxg.silence.quickrecord.base.BaseFragment;
import com.djxg.silence.quickrecord.bean.Features;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2017/11/22.
 */

public class HomeFragment extends BaseFragment {

    private Toolbar toolbar;
    private String title = "Home";

    RecyclerView recyclerView;

    private List<Features> featuresList = new ArrayList<>();

    private FeaturesAdapter featuresAdapter = new FeaturesAdapter(getContext(), featuresList);

    private boolean init_list = true;

    @Override
    protected int attachLayoutId() {
        return R.layout.m_home_fragment;
    }

    @Override
    protected void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);

        recyclerView = view.findViewById(R.id.features_recycler_view);

    }

    @Override
    protected void initData() {

        toolbar.setTitle(title);
        //用网格显示，一排有2个格子
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(featuresAdapter);

        if (init_list) {
            Features features1 = new Features("扫描文字识别", R.drawable.home);
            featuresList.add(features1);
            Features features2 = new Features("名片识别", R.drawable.home);
            featuresList.add(features2);
            Features features3 = new Features("图片识别", R.drawable.home);
            featuresList.add(features3);
            Features features4 = new Features("二维码识别", R.drawable.home);
            featuresList.add(features4);
            Features features5 = new Features("手机号识别", R.drawable.home);
            featuresList.add(features5);

            init_list = false;
        }

    }
}
