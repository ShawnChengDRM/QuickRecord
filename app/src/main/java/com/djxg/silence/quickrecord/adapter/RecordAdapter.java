package com.djxg.silence.quickrecord.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.bean.Record;

import java.util.List;

/**
 * Created by silence on 2018/1/5.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private Context mContext;

    private List<Record> mRecords;

    static class ViewHolder extends RecyclerView.ViewHolder {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Record records = mRecords.get(position);
        Glide.with(mContext).load(records.getRecord_image()).into(holder.recordImage);
        holder.recordText.setText(records.getRecord_text());
        holder.recordTime.setText(records.getRecord_time());

        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
