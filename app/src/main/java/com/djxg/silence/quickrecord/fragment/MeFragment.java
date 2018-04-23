package com.djxg.silence.quickrecord.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.activity.LoginActivity;
import com.djxg.silence.quickrecord.activity.MeImfActivity;
import com.djxg.silence.quickrecord.adapter.MeAdapter;
import com.djxg.silence.quickrecord.base.BaseFragment;
import com.djxg.silence.quickrecord.bean.MeItem;
import com.djxg.silence.quickrecord.bean.Movie;
import com.djxg.silence.quickrecord.bean.Subjects;
import com.djxg.silence.quickrecord.bean.User;
import com.djxg.silence.quickrecord.bean.UserSubjects;
import com.djxg.silence.quickrecord.net.ApiMethods;
import com.djxg.silence.quickrecord.net.MyObserver;
import com.djxg.silence.quickrecord.net.ObserverOnNextListener;
import com.djxg.silence.quickrecord.net.RetrofitTest;
import com.djxg.silence.quickrecord.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by silence on 2017/11/22.
 */

public class MeFragment extends BaseFragment {

    private Toolbar toolbar;
    private String title = "Me";

    private boolean init_list = true;

    CardView cardView;
    ImageView imageView;
    TextView nameView;
    TextView introduceView;

    RecyclerView recyclerView;
    private List<MeItem> meItemList = new ArrayList<>();
    private MeAdapter meAdapter = new MeAdapter(getContext(), meItemList);

    @Override
    protected int attachLayoutId() {
        return R.layout.m_me_fragment;
    }

    @Override
    protected void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        recyclerView = view.findViewById(R.id.me_recycler_view);

        cardView = view.findViewById(R.id.me_card_view);
        nameView = view.findViewById(R.id.me_username);
        introduceView = view.findViewById(R.id.me_introduce);

        //用线性显示 类似于listView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //给RecyclerView画分割线
        recyclerView.addItemDecoration(new SpaceItemDecoration(getContext(), SpaceItemDecoration.HORIZONTAL_LIST));

        recyclerView.setAdapter(meAdapter);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), MeImfActivity.class);
                startActivity(intent);


                /*SharedPreferences.Editor editor = getContext().getSharedPreferences("user", Context.MODE_PRIVATE)
                        .edit();
                editor.putString("username", "");
                editor.apply();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);*/


            }
        });

        SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");
        //Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();

        imageView = view.findViewById(R.id.login_me_image);

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



    }

    @Override
    protected void initData() {

        if (init_list) {
            MeItem meItem1 = new MeItem(R.drawable.qrcode, "二维码名片");
            meItemList.add(meItem1);
            MeItem meItem2 = new MeItem(R.drawable.ic_settings_24dp, "设置");
            meItemList.add(meItem2);

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
            String introduce = preferences.getString("introduce", "");
            //Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();

            if (Objects.equals(username, "")) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }

            nameView.setText(username);
            introduceView.setText(introduce);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            // 发起网络请求, 刷新界面数据
            SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            String username = preferences.getString("username", "");
            String introduce = preferences.getString("introduce", "");
            //Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();

            if (Objects.equals(username, "")) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }

            nameView.setText(username);
            introduceView.setText(introduce);

        }
    }
}
