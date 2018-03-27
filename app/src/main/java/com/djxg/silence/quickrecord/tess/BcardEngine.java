package com.djxg.silence.quickrecord.tess;

/**
 * Created by silence on 2018/1/4.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tianruiworkroombcr.Native;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class BcardEngine {

    private static final String TAG = "DBG_" + BcardEngine.class.getName();

    private Context mContext;
    private Bitmap mBitmap;
    private String datapath = "";

    private static final String LANGUAGE_ENG = "Native.TIANRUI_LANGUAGE_CHINESE_MIXED";

    private BcardEngine(Context context) {
        mContext = context;
    }

    public static BcardEngine Generate(Context context) {
        return new BcardEngine(context);
    }

    public String detectText(Bitmap bitmap) {

        Log.d(TAG, "Initialization of BcardApi");

        //BcardFolder
        datapath = mContext.getFilesDir() + "/tesseract/";

        //检查训练文件是否存在
        BcardDataManager.checkFile(mContext, new File(datapath + "bcdata"));

        //打开引擎
        int open = Native.openBcrEngine(datapath);

        if (open == 1) {
            //设置语言
            int lag = Native.setBcrLanguage(0);

            if (lag == 1) {
                //Bitmap mBmppp = BitmapFactory.decodeFile(mImgFilePath);
            } else {
                Log.d(TAG, "设置语言失败");
            }
        } else {
            Log.d(TAG, "打开引擎失败");
        }







        System.gc();
        return "111";
    }
}


