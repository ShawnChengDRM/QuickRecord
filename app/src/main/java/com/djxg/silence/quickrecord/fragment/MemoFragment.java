package com.djxg.silence.quickrecord.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.activity.LoginActivity;
import com.djxg.silence.quickrecord.adapter.MemoAdapter;
import com.djxg.silence.quickrecord.base.BaseFragment;
import com.djxg.silence.quickrecord.bean.Features;
import com.djxg.silence.quickrecord.bean.MemoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by silence on 2017/11/22.
 */

public class MemoFragment extends BaseFragment {

    private Toolbar toolbar;
    private String title = "Memo";

    ImageView imageView;
    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView;

    private List<MemoItem> memoItemList = new ArrayList<>();

    private MemoAdapter memoAdapter = new MemoAdapter(getContext(), memoItemList);

    private boolean init_list = true;

    @Override
    protected int attachLayoutId() {
            return R.layout.m_memo_fragment;
    }

    @Override
    protected void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        recyclerView = view.findViewById(R.id.memo_recycler_view);
        floatingActionButton = view.findViewById(R.id.memo_fab);



        SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");
        //Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();


        imageView = view.findViewById(R.id.login_memo_image);

        if (Objects.equals(username, "")) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    @Override
    protected void initData() {

        //用网格显示，一排有2个格子
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memoAdapter);

        if (init_list) {
            MemoItem memoItem1 = new MemoItem("文字", R.drawable.memo);
            memoItemList.add(memoItem1);
            MemoItem memoItem2 = new MemoItem("名片", R.drawable.memo);
            memoItemList.add(memoItem2);
            MemoItem memoItem3 = new MemoItem("图片", R.drawable.memo);
            memoItemList.add(memoItem3);
            MemoItem memoItem4 = new MemoItem("二维码", R.drawable.memo);
            memoItemList.add(memoItem4);
            MemoItem memoItem5 = new MemoItem("手机号", R.drawable.memo);
            memoItemList.add(memoItem5);

            init_list = false;
        }


    }

    /**
     *
     * 这里有好几种情况要考虑：
     *第一种，在四个 Fragment 都已经显示过的情况下，不同 Tab 切换时，当前 Fragment 的 onResume() 方法不会被调用，需要在 onHiddenChanged() 方法中请求服务器；
     *第二种，从其他 Activity 返回这个 Activity 时，当前 Fragment 不会调用 onHiddenChanged() 方法，需要在 onResume() 方法中请求服务器；
     *第三种，类似第二种场景，不同的是其他 Activity 返回时，还指定切换至处于隐藏状态的另一个 Fragment，对于这个 Fragment 来说，onResume() 和 onHiddenChanged() 方法都会被调用；
     *
     * 除了 isHidden() 方法，还有一个 isVisible() 方法，也用于判断 Fragment 的状态，表明 Fragment 是否对用户可见，如果为 true，必须满足三点条件：1，Fragment 已经被 add 至 Activity 中；2，视图内容已经被关联到 window 上；3. 没有被隐藏，即 isHidden() 为 false
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        // 这里的 isResumed() 判断就是为了避免与 onResume() 方法重复发起网络请求
        if (isVisible() && isResumed()) {
            SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            String username = preferences.getString("username", "");
            //Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();

            if (Objects.equals(username, "")) {
                imageView.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
            } else {
                imageView.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            // 发起网络请求, 刷新界面数据
            SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            String username = preferences.getString("username", "");
            //Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();

            if (Objects.equals(username, "")) {
                imageView.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
            } else {
                imageView.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.VISIBLE);
            }

        }
    }


}
