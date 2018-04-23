package com.djxg.silence.quickrecord.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.adapter.MainActivityViewPagerAdapter;
import com.djxg.silence.quickrecord.base.BaseActivity;
import com.djxg.silence.quickrecord.fragment.HomeFragment;
import com.djxg.silence.quickrecord.fragment.MeFragment;
import com.djxg.silence.quickrecord.fragment.MemoFragment;
import com.djxg.silence.quickrecord.fragment.RecordFragment;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationMain;
    private ViewPager viewPager;
    private MainActivityViewPagerAdapter viewPagerAdapter;

    private HomeFragment homeFragment;
    private RecordFragment recordFragment;
    private MemoFragment memoFragment;
    private MeFragment meFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //image = BitmapFactory.decodeResource(getResources(), R.drawable.tt);
        initView();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              //获取到菜单项的Id
            int itemId = item.getItemId();
              //当第一项被选择时m_home_fragment显示，以此类推
            switch (itemId) {
                case R.id.tab_menu_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.tab_menu_record:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.tab_menu_memo:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.tab_menu_me:
                    viewPager.setCurrentItem(3);

            }
            // true 会显示这个Item被选中的效果 false 则不会
            return true;
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //当 ViewPager 滑动后设置BottomNavigationView 选中相应选项
            bottomNavigationMain.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    protected void initView() {

        homeFragment = new HomeFragment();
        recordFragment = new RecordFragment();
        memoFragment = new MemoFragment();
        meFragment = new MeFragment();

        viewPager = findViewById(R.id.viewPager);
        //为ViewPager设置Adapter
        viewPagerAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
        //为Adapter添加Fragment
        viewPagerAdapter.addFragment(homeFragment);
        viewPagerAdapter.addFragment(recordFragment);
        viewPagerAdapter.addFragment(memoFragment);
        viewPagerAdapter.addFragment(meFragment);
        viewPager.setAdapter(viewPagerAdapter);
        //添加viewPager事件监听
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        bottomNavigationMain = findViewById(R.id.bottom_navigation_main);
        //添加BottomNavigationView点击监听
        bottomNavigationMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    protected void initData() {

    }

}