package com.djxg.silence.quickrecord.fragment;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.base.BaseFragment;

/**
 * Created by silence on 2017/11/22.
 */

public class MemoFragment extends BaseFragment {

    private Toolbar toolbar;
    private String title = "Memo";

    @Override
    protected int attachLayoutId() {
        return R.layout.m_memo_fragment;
    }

    @Override
    protected void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);
    }

    @Override
    protected void initData() {
        toolbar.setTitle(title);

    }
}
