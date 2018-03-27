package com.djxg.silence.quickrecord.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.djxg.silence.quickrecord.BusinessC.MainEngineActivity;
import com.djxg.silence.quickrecord.BusinessC.SplashActivity;
import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.Recognition.SortManager;
import com.djxg.silence.quickrecord.ScannerActivity;
import com.djxg.silence.quickrecord.bean.Features;

import java.util.List;

/**
 * Created by silence on 2017/11/23.
 */

public class FeaturesAdapter extends RecyclerView.Adapter<FeaturesAdapter.ViewHolder> {

    private Context mContext;

    private List<Features> mFeatures;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView featuresImage;
        TextView featuresName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            featuresImage = view.findViewById(R.id.features_image);
            featuresName = view.findViewById(R.id.features_name);
        }
    }

    public FeaturesAdapter(Context context, List<Features> features) {
        mContext = context;
        mFeatures = features;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.features_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mFeatures.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Features features = mFeatures.get(position);
        holder.featuresName.setText(features.getName());
        Glide.with(mContext).load(features.getImageId()).into(holder.featuresImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SortManager sortManager = SortManager.Generate(mContext, position);
                sortManager.choose();

            }
        });
    }
}
