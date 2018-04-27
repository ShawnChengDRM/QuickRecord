package com.djxg.silence.quickrecord.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.adapter.FeaturesAdapter;
import com.djxg.silence.quickrecord.adapter.RecordAdapter;
import com.djxg.silence.quickrecord.base.BaseFragment;
import com.djxg.silence.quickrecord.bean.Features;
import com.djxg.silence.quickrecord.bean.Record;
import com.djxg.silence.quickrecord.database.DataBaseTool;
import com.djxg.silence.quickrecord.editor.InfoEditActivity;
import com.djxg.silence.quickrecord.editor.InfoShowActivity;
import com.djxg.silence.quickrecord.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2017/11/22.
 */

public class RecordFragment extends BaseFragment {

    private Toolbar toolbar;
    private String title = "Record";

    RecyclerView recyclerView;
    List<Record> recordList;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int attachLayoutId() {
        return R.layout.m_record_fragment;
    }

    @Override
    protected void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);

        recyclerView = view.findViewById(R.id.record_recycler_view);

        toolbar.setTitle(title);


        //在Activity中注册需要上下文菜单的View
        registerForContextMenu(recyclerView);



 /*       swipeRefreshLayout = view.findViewById(R.id.record_refresh);


*//*        // 设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        swipeRefreshLayout.setProgressViewOffset(true, 50, 200);

        // 设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // 通过 setEnabled(false) 禁用下拉刷新
        swipeRefreshLayout.setEnabled(false);*//*


     *//*
      * 设置手势下拉刷新的监听
      *//*
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // 刷新动画开始后回调到此方法

                        recordList = DataBaseTool.InquireRecordALL();

                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
        );*/





    }

    @Override
    protected void initData() {


        recordList = DataBaseTool.InquireRecordALL();
        RecordAdapter recordAdapter = new RecordAdapter(getContext(), recordList);
        //用线性显示 类似于listView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //给RecyclerView画分割线
        recyclerView.addItemDecoration(new SpaceItemDecoration(getContext(), SpaceItemDecoration.HORIZONTAL_LIST));

        recyclerView.setAdapter(recordAdapter);



    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = -1;

        try {
            position = ((RecordAdapter) recyclerView.getAdapter()).getPosition();
            //position = ((ContactAdapter)getAdapter()).getPosition();
        } catch (Exception e) {
            //Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }

        switch (item.getItemId()) {
            case 1:
                String delete = recordList.get(position).getRecord_time();
                //删除数据库字段
                DataBaseTool.DeleteRecord(delete);
                //删除list里的元素
                recordList.remove(position);
                //更新recyclerView
                recyclerView.getAdapter().notifyDataSetChanged();
                //Toast.makeText(getContext(), position + " 删除", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                String update = recordList.get(position).getRecord_time();
                Intent intentAlter = new Intent(getContext(), InfoEditActivity.class);
                intentAlter.putExtra("time", update);
                startActivity(intentAlter);
                //Toast.makeText(getContext(), position + " 修改", Toast.LENGTH_SHORT).show();
                break;

            case 3:
                showSortDialog();
                break;
        }

        return super.onContextItemSelected(item);

    }

    private void showSortDialog(){
        final String radioItems[] = new String[]{"radioItem1","radioItem1","radioItem1","radioItem1"};

        AlertDialog.Builder radioDialog = new AlertDialog.Builder(getContext());
        radioDialog.setTitle("类型");
        radioDialog.setIcon(R.mipmap.ic_launcher_round);

    /*
        设置item 不能用setMessage()
        用setSingleChoiceItems
        items : radioItems[] -> 单选选项数组
        checkItem : 0 -> 默认选中的item
        listener -> 回调接口
    */
        radioDialog.setSingleChoiceItems(radioItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),radioItems[which],Toast.LENGTH_SHORT).show();
            }
        });

        //设置按钮
        radioDialog.setPositiveButton("确定"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showLevelDialog();
                        dialog.dismiss();
                    }
                });

        radioDialog.create().show();
    }

    private void showLevelDialog(){
        final String radioItems[] = new String[]{"radioItem1","radioItem1","radioItem1","radioItem1"};

        AlertDialog.Builder radioDialog = new AlertDialog.Builder(getContext());
        radioDialog.setTitle("类型");
        radioDialog.setIcon(R.mipmap.ic_launcher_round);

    /*
        设置item 不能用setMessage()
        用setSingleChoiceItems
        items : radioItems[] -> 单选选项数组
        checkItem : 0 -> 默认选中的item
        listener -> 回调接口
    */
        radioDialog.setSingleChoiceItems(radioItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),radioItems[which],Toast.LENGTH_SHORT).show();
            }
        });

        //设置按钮
        radioDialog.setPositiveButton("确定"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        radioDialog.create().show();
    }


}
