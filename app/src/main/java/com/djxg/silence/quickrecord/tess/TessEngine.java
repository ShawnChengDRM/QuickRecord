package com.djxg.silence.quickrecord.tess;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class TessEngine {

    private static final String TAG = "DBG_" + TessEngine.class.getName();

    private Context mContext;
    private Bitmap mBitmap;
    private String datapath = "";

    private static final String LANGUAGE_ENG = "chi_sim";

    private TessEngine(Context context) {
        mContext = context;
    }

    public static TessEngine Generate(Context context) {
        return new TessEngine(context);
    }

    public String detectText(Bitmap bitmap) {

        Log.d(TAG, "Initialization of TessBaseApi");

        //TesseractFolder
        datapath = mContext.getFilesDir() + "/tesseract/";

        //检查训练文件是否存在
        TessDataManager.checkFile(mContext, new File(datapath + "tessdata"));

        TessBaseAPI tessBaseAPI = new TessBaseAPI();

        tessBaseAPI.setDebug(true);

        tessBaseAPI.init(datapath, LANGUAGE_ENG);
        // 白名单
        //tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        // 黑名单
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?");

        //设置页面分割模式。 默认为TessBaseAPI.PageSegMode.PSM_SINGLE_BLOCK（假设单个统一的文本块。）。 这可以控制OCR引擎在识别文本之前将执行多少处理。
        //TessBaseAPI.PageSegMode.PSM_AUTO_OSD（自动页面分割与方向和脚本检测。）
        tessBaseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
        Log.d(TAG, "Ended initialization of TessEngine");

        Log.d(TAG, "Running inspection on bitmap");
        tessBaseAPI.setImage(bitmap);
        String OCRresult = tessBaseAPI.getUTF8Text();

        Log.d(TAG, "Confidence values: " + tessBaseAPI.meanConfidence());

        tessBaseAPI.end();
        System.gc();
        return OCRresult;
    }
}
