package com.djxg.silence.quickrecord.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by silence on 2017/11/22.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutId();

    /**
     * 初始化视图控件
     */
    protected abstract void initView(View view);

    /**
     * 初始化数据
     */
    protected abstract void initData() throws NullPointerException;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutId(), container, false);
        initView(view);
        initData();
        return view;
    }
}
