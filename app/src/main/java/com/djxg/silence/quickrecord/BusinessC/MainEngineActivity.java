package com.djxg.silence.quickrecord.BusinessC;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;


import com.djxg.silence.quickrecord.MyApplication;
import com.tianruiworkroombcr.Native;
import com.djxg.silence.quickrecord.tess.BcardDataManager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.djxg.silence.quickrecord.R;

public class MainEngineActivity extends AppCompatActivity {

    private static final int DECODEING_PROCESS_FINISH = 2001;
    private static final int DECODEING_PROCESS_FAILD = 2002;
    private static final int INITLANGUAGE_PROCESS_FAILD = 2003;

    public static Bitmap mBmppp;
    ProgressDialog mypDialog;
    AlertDialog.Builder failedDialog2;
    AlertDialog.Builder failedDialog3;
    AlertDialog.Builder failedDialog1;
    AlertDialog.Builder setLanguageDialog;
    AlertDialog.Builder myDialog2;
    public static int[] mpix;
    public static  int mpicw;
    public static  int mpich;
    public  static String[] mwholeWord;//
    public  static String[] mwholeTextLine;//
    public  static int[] mwholeWordRect;//

    public  static int[] mwholdTextLineRect;//
    public  static int[] mwholdTextLineType;//

    public  static  String[] strLanguage = new String[70];//
    public  int mSelectedItem;
    public  static int mStaticSelectedItem;

    public  static  int mOpenSetLangFlg;
    public  static String mImgFilePath;//
    public  String tempImagePathDir;

    private String datapath = "";
    private Context mContext;

    private static final String TAG = "Bcard";

    //public static final String mstrFilePathForDat = Environment.getExternalStorageDirectory().toString() + "/tianrui";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_engine);
        mOpenSetLangFlg = 0;
        mStaticSelectedItem = 0;



        mContext = MyApplication.sAppContext;
        //BcardFolder
        datapath = mContext.getFilesDir().getPath() + "/tesseract/bcdata";

        //检查训练文件是否存在
        BcardDataManager.checkFile(mContext, new File(datapath));

        int rlt = Native.openBcrEngine(datapath); // step 1: open OCR engine
        Log.d(TAG, rlt + "AAA");
        int rlt1 = Native.setBcrLanguage(Native.TIANRUI_LANGUAGE_CHINESE_MIXED); // step 2: set recognition language
        if (rlt == 1 && rlt1 == 1)
        {
            mOpenSetLangFlg = 1;
        }

        mSelectedItem = 1;

        createDialog();
        initLangugeString();

        Button btn_xiangpiana = findViewById(R.id.bxiangpiana);
        btn_xiangpiana.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 0xF003);
            }
        });

        Button btn_xuanzhuan = findViewById(R.id.bxuanzhuan);
        btn_xuanzhuan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                if (mSelectedItem < 1)
                    mSelectedItem = 1;
                setLanguageDialog.setSingleChoiceItems(strLanguage, (mSelectedItem - 1),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mSelectedItem = which;
                                mStaticSelectedItem = which;
                                mSelectedItem += 1;
                                mOpenSetLangFlg = 0;
                                int rlt = Native.setBcrLanguage(mSelectedItem);
                                if (rlt == 1)
                                {
                                    mOpenSetLangFlg = 1;
                                }
                            }
                        });
                setLanguageDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
                setLanguageDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                setLanguageDialog.create();
                setLanguageDialog.show();
            }
        });
    }

    public void initLangugeString()
    {
        strLanguage[0] = "中文简体繁体混合";
        strLanguage[1] = "中文简体";
        strLanguage[2] = "日语";
        strLanguage[3] = "韩语";
        strLanguage[4] = "俄罗斯语";
        strLanguage[5] = "英语";
        strLanguage[6] = "法语";
        strLanguage[7] = "德语";
        strLanguage[8] = "意大利语";
        strLanguage[9] = "葡萄牙语";
        strLanguage[10] = "西班牙语";

        strLanguage[11] = "南非语";
        strLanguage[12] = "阿尔巴尼亚语";
        strLanguage[13] = "阿塞拜疆语";
        strLanguage[14] = "巴斯克语";
        strLanguage[15] = "波斯语";
        strLanguage[16] = "保加利亚语";
        strLanguage[17] = "加泰罗尼亚语";
        strLanguage[18] = "宿雾语";
        strLanguage[19] = "克罗地亚语";
        strLanguage[20] = "捷克语";

        strLanguage[21] = "丹麦语";
        strLanguage[22] = "荷兰语";
        strLanguage[23] = "爱沙尼亚语";
        strLanguage[24] = "斐济语";
        strLanguage[25] = "芬兰语";
        strLanguage[26] = "加利西亚语";
        strLanguage[27] = "干达语";
        strLanguage[28] = "瓜拉尼语";
        strLanguage[29] = "匈牙利语";
        strLanguage[30] = "冰岛语";

        strLanguage[31] = "印度尼西亚语";
        strLanguage[32] = "爱尔兰语";
        strLanguage[33] = "景颇语";
        strLanguage[34] = "卡舒布语";
        strLanguage[35] = "哈萨克语";
        strLanguage[36] = "吉尔吉斯语";
        strLanguage[37] = "拉丁语";
        strLanguage[38] = "拉脱维亚语";
        strLanguage[39] = "立陶宛语";
        strLanguage[40] = "卢森堡语";

        strLanguage[41] = "马来语";
        strLanguage[42] = "马耳他语";
        strLanguage[43] = "毛利语";
        strLanguage[44] = "马其顿语";
        strLanguage[45] = "蒙古语";
        strLanguage[46] = "挪威语";
        strLanguage[47] = "波兰语";
        strLanguage[48] = "盖丘亚语";
        strLanguage[49] = "罗马尼亚语";
        strLanguage[50] = "卢旺达语";

        strLanguage[51] = "塞尔维亚语（拉丁语字符）";
        strLanguage[52] = "塞尔维亚语（西里尔语字符）";
        strLanguage[53] = "斯洛文尼亚语";
        strLanguage[54] = "索马里语";
        strLanguage[55] = "斯瓦希里语";
        strLanguage[56] = "瑞典语";
        strLanguage[57] = "斯洛伐克语";
        strLanguage[58] = "塔加拉族语";
        strLanguage[59] = "塔吉克语";
        strLanguage[60] = "鞑靼语";

        strLanguage[61] = "土耳其语";
        strLanguage[62] = "土库曼语（拉丁语字符）";
        strLanguage[63] = "乌兹别克语（拉丁语字符）";
        strLanguage[64] = "乌兹别克语（西里尔语字符）";
        strLanguage[65] = "乌克兰语";
        strLanguage[66] = "威尔士语";
        strLanguage[67] = "科萨语";
        strLanguage[68] = "壮语";
        strLanguage[69] = "祖鲁语";
    }

    public void createDialog()
    {
        mypDialog = new ProgressDialog(this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //mypDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mypDialog.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));
        mypDialog.setMessage("识别引擎工作中...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(true);

        myDialog2 = new AlertDialog.Builder(this);
        myDialog2.setTitle("About");
        myDialog2.setMessage("版本号1.01.01,识别核心技术支持by,www.ocrspace.com!");
        myDialog2.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        myDialog2.create();

        failedDialog2 = new AlertDialog.Builder(this);
        failedDialog2.setTitle("对不起！");
        failedDialog2.setMessage("识别图片失败！");
        failedDialog2.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        failedDialog2.create();

        failedDialog3 = new AlertDialog.Builder(this);
        failedDialog3.setTitle("对不起！");
        failedDialog3.setMessage("设置语言失败（可能是您的.dat文件没有放到正确的位置）！");
        failedDialog3.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        failedDialog3.create();


        failedDialog1 = new AlertDialog.Builder(this);
        failedDialog1.setTitle("对不起！");
        failedDialog1.setMessage("您没有选择图片文件！");
        failedDialog1.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        failedDialog1.create();

        setLanguageDialog = new AlertDialog.Builder(this);
        setLanguageDialog.setTitle("请选择识别语言");
        setLanguageDialog.setIcon(android.R.drawable.ic_dialog_alert);

        //return;
    }

    public Handler mHandler = new MainHandler();

    private class MainHandler extends Handler{

        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case DECODEING_PROCESS_FAILD:
                    if (mypDialog != null && mypDialog.isShowing())
                    {
                        mypDialog.dismiss();
                    }

                    failedDialog2.show();
                    break;
                case INITLANGUAGE_PROCESS_FAILD:

                    if (mypDialog != null && mypDialog.isShowing())
                    {
                        mypDialog.dismiss();
                    }

                    failedDialog3.show();

                    break;
                case DECODEING_PROCESS_FINISH:
                    if (mypDialog != null && mypDialog.isShowing())
                    {
                        mypDialog.dismiss();
                    }

                    Intent mainIntent = new Intent(MainEngineActivity.this,
                            WebViewActivity.class);

                    MainEngineActivity.this.startActivity(mainIntent);

                    break;

                default:
                    break;

            }
        }
    }

    public void startDecodeingThread()
    {
        mypDialog.show();
        Thread decodeingThread = new Thread( new Runnable(){
            public void run(){
        		/*
        		 int picw = mBmppp.getWidth();
		     	 int pich = mBmppp.getHeight();
		     	 int[] pix = new int[picw * pich];

		     	 mBmppp.getPixels(pix, 0, picw, 0, 0, picw, pich);*/
                int rlt = 0;// Native.setBcrLanguage(Native.TIANRUI_LANGUAGE_CHINESE_MIXED);
                //rlt = Native.recognizeImage(pix, picw, pich); // step 3: recognize one image
                //String path = "file:///android_asset/myKeyedDatNew.dat";
                if (mOpenSetLangFlg == 1)
                {
                    rlt = Native.recognizeImage(mpix, mpicw, mpich); // step 3: recognize one image
                    if(rlt != 1)
                    {
                        mHandler.sendMessage(mHandler.obtainMessage(DECODEING_PROCESS_FAILD, rlt, 0));
                    }
                    else
                    {//mwholeWord = Native.getWholeWordResult();
                        mwholeTextLine = Native.getWholeTextLineResult();
                        //mwholeWordRect = Native.getWholeWordRect();
                        mwholdTextLineRect = Native.getWholeTextLineRect();
                        mwholdTextLineType = Native.getWholeTextLineType();
                        drawTextlineRectAndSaveNewBitmap();
                        mHandler.sendMessage(mHandler.obtainMessage(DECODEING_PROCESS_FINISH, rlt, 0));
                    }
                }
                else
                {
                    mHandler.sendMessage(mHandler.obtainMessage(INITLANGUAGE_PROCESS_FAILD, rlt, 0));
                }

            }});
        decodeingThread.setName("decodeingThread");
        decodeingThread.start();
    }




    public void drawTextlineRectAndSaveNewBitmap()
    {
        int iRatio  = 1;

        int picw = mBmppp.getWidth();
        int pich = mBmppp.getHeight();
        int oldPich = pich;
	  /*BitmapFactory.Options opts2 = new BitmapFactory.Options();
      opts2.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(mImgFilePath, opts2);
      if (picw > 1000 || pich > 1000)
      {
      	if (picw > pich)
			{
				iRatio = picw / 1000 + 1;
			}
			else
			{
				iRatio = pich / 1000 + 1;
			}
      }

       opts2.inSampleSize = iRatio;
       opts2.inPreferredConfig = Bitmap.Config.ARGB_8888;
       opts2.inJustDecodeBounds = false;
       mBmppp = BitmapFactory.decodeFile(mImgFilePath, opts2);
       picw = mBmppp.getWidth();
	    pich = mBmppp.getHeight();
      */
        double dRatio = (oldPich * 1.0) / (pich * 1.0);
        dRatio = 1.0;
        int[] pix = new int[picw * pich];
        mBmppp.getPixels(pix, 0, picw, 0, 0, picw, pich);


        Bitmap dstBitmap = Bitmap.createBitmap(picw, pich, Config.ARGB_8888);
        dstBitmap.setPixels(pix, 0, picw, 0, 0, picw, pich);

        //Bitmap dstBitmap = Bitmap.createBitmap(picw, pich, Config.ARGB_8888);
        //dstBitmap.setPixels(pix, 0, picw, 0, 0, picw, pich);

        Canvas canvas = new Canvas(dstBitmap);

        Paint p = new Paint();
        p.setColor(Color.GREEN);//
        p.setAntiAlias(true);//
        p.setStyle(Style.STROKE);//
        //dRatio = 2.0;
        int i = 0;
        if (mwholdTextLineRect != null)
        {
            for (i = 0; i < mwholdTextLineRect.length; i += 4)
            {
                int left, right, top, bottom;

                left = (int) (mwholdTextLineRect[i] / dRatio);
                top = (int) (mwholdTextLineRect[i + 1] / dRatio);
                right = (int) (mwholdTextLineRect[i + 2] / dRatio);
                bottom = (int) (mwholdTextLineRect[i + 3] / dRatio);

                canvas.drawRect(left, top, right, bottom, p);
            }
        }

        tempImagePathDir = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/data/myOCRTempData/";

        File mWorkingPath = new File(tempImagePathDir);
        // if this directory does not exists, make one.
        int mCreatFolderFlg = 1;
        if (!mWorkingPath.exists())
        {
            if (!mWorkingPath.mkdirs())
            {
                mCreatFolderFlg = 0;
            }
        }

        if (mCreatFolderFlg == 1)
        {
            long dateTaken = System.currentTimeMillis();
            String datetime = DateFormat.format("yyyyMMddkkmmss", dateTaken).toString();
            try {
                FileOutputStream out = new FileOutputStream(tempImagePathDir + datetime);
                dstBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

                mImgFilePath = tempImagePathDir + datetime;

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public boolean DeleteFolder(String sPath)
    {
        boolean flag = false;
        File file = new File(sPath);
        if (!file.exists()) {
            return flag;
        } else {
            if (file.isFile()) {
                return deleteFile(sPath);
            } else {
                return deleteDirectory(sPath);
            }
        }
    }

    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);

        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public boolean deleteDirectory(String sPath) {

        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);

        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;

        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            }
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;

        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            switch(requestCode){
                case 0xF003:
                    if (true)
                    {
                        Cursor imageCursor = MediaStore.Images.Media.query(getContentResolver(), data.getData(), null);
                        if (imageCursor == null)
                        {
                            return;
                        }
                        else
                        {
                            imageCursor.moveToFirst();
                        }

                        int columnIndex = imageCursor.getColumnIndex(ImageColumns.DATA);
                        mImgFilePath = imageCursor.getString(columnIndex);
                        if (mImgFilePath == null)
                        {
                            return;
                        }

                        int rotateDegree = readPicDegree(mImgFilePath);

                        if (true)
                        {
                            int iRatio  = 1;
                            BitmapFactory.Options opts2 = new BitmapFactory.Options();
                            opts2.inJustDecodeBounds = true;
                            mBmppp = BitmapFactory.decodeFile(mImgFilePath, opts2);
                            int picw = opts2.outWidth;
                            int pich = opts2.outHeight;
                            int oldPich = pich;

                            if (picw > 2000 && pich > 2000)
                            {
                                if (picw > pich)
                                {
                                    iRatio = picw / 2000 + 1;
                                }
                                else
                                {
                                    iRatio = pich / 2000 + 1;
                                }
                            }

                            opts2.inSampleSize = iRatio;
                            opts2.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            opts2.inJustDecodeBounds = false;
                            mBmppp = BitmapFactory.decodeFile(mImgFilePath, opts2);
                        }
                        else
                        {
                            mBmppp = BitmapFactory.decodeFile(mImgFilePath);
                        }
                        //

                        if (rotateDegree != 0)
                        {
                            mBmppp = rotateBitmap(rotateDegree, mBmppp);
                        }

                        if (mBmppp != null)
                        {
                            mpicw = mBmppp.getWidth();
                            mpich = mBmppp.getHeight();
                            mpix = new int[mpicw * mpich];
                            mBmppp.getPixels(mpix, 0, mpicw, 0, 0, mpicw, mpich);
                            startDecodeingThread();
                        }
                        else
                        {
                            failedDialog1.create();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static int readPicDegree(String path) {
        int degree = 0;

        // 读取图片文件信息的类ExifInterface
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }

        return degree;
    }

    public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }

    protected void onDestroy() {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Native.closeBcrEngine();// step 4: close OCR engine
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_engine, menu);
        return true;
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            myDialog2.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

}
