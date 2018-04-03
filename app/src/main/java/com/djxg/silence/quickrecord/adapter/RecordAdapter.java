package com.djxg.silence.quickrecord.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.bean.Record;
import com.djxg.silence.quickrecord.database.DataBaseTool;
import com.djxg.silence.quickrecord.editor.InfoShowActivity;

import java.util.List;

/**
 * Created by silence on 2018/1/5.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private Context mContext;

    private List<Record> mRecords;

    private int position;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        LinearLayout View;
        ImageView recordImage;
        TextView recordText;
        TextView recordTime;

        public ViewHolder(View view) {
            super(view);
            View = (LinearLayout) view;
            recordImage = view.findViewById(R.id.record_avatar);
            recordText = view.findViewById(R.id.record_text);
            recordTime = view.findViewById(R.id.record_time);

            view.setOnCreateContextMenuListener(this);
        }

        //添加上下文菜单
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.add(0, 1, 0, "删除");//groupId, itemId, order, title
            menu.add(0, 2, 0, "修改");
        }

    }

    public RecordAdapter(Context context, List<Record> records) {
        mContext = context;
        mRecords = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.record_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Record records = mRecords.get(position);

        //把二进制数据转换成bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(records.getRecord_image(),
                0, records.getRecord_image().length);

        //把bitmap裁剪成圆形
        Bitmap bitmap1 = DataBaseTool.toRoundBitmap(bitmap);
        holder.recordImage.setImageBitmap(bitmap1);

        //Glide.with(mContext).load(records.getRecord_image()).into(holder.recordImage);
        holder.recordText.setText(records.getRecord_text());
        holder.recordTime.setText(records.getRecord_time());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把时间当作标识，不妥，请勿模仿
                Intent intent_show = new Intent(mContext, InfoShowActivity.class);
                String id_time = records.getRecord_time();
                intent_show.putExtra("time", id_time);
                mContext.startActivity(intent_show);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(holder.getAdapterPosition());

                return false;
            }
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
