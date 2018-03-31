package com.djxg.silence.quickrecord.Recognition;

import android.content.Context;
import android.content.Intent;

import com.djxg.silence.quickrecord.BusinessC.CameraAlbumActivity;
import com.djxg.silence.quickrecord.BusinessC.MainEngineActivity;
import com.djxg.silence.quickrecord.ScannerActivity;

/**
 * Created by silence on 2018/3/26.
 */

public class SortManager {

    private Context mContext;
    private int mPosition;

    private String choose;


    private SortManager(Context context, int position) {
        mContext = context;
        mPosition = position;
    }

    public static SortManager Generate(Context context, int position) {
        return new SortManager(context, position);
    }


    public void choose() {

        switch (mPosition) {

            case 0:
                Intent intent_zero = new Intent(mContext, ScannerActivity.class);
                choose = "N";
                intent_zero.putExtra("choose", choose);
                mContext.startActivity(intent_zero);
                break;

            case 1:
                Intent intent_one = new Intent(mContext, MainEngineActivity.class);
                mContext.startActivity(intent_one);
                break;

            case 2:
                Intent intent_two = new Intent(mContext, CameraAlbumActivity.class);
                choose = "N";
                intent_two.putExtra("choose", choose);
                mContext.startActivity(intent_two);
                break;

            case 3:
                Intent intent_three = new Intent(mContext, ScannerActivity.class);
                choose = "Y";
                intent_three.putExtra("choose", choose);
                mContext.startActivity(intent_three);
                break;

            case 4:
                Intent intent_four = new Intent(mContext, CameraAlbumActivity.class);
                choose = "Y";
                intent_four.putExtra("choose", choose);
                mContext.startActivity(intent_four);
                break;

        }
    }


}
