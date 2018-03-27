package com.djxg.silence.quickrecord.BusinessC;

/**
 * Created by silence on 2018/1/31.
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.djxg.silence.quickrecord.MyApplication;
import com.djxg.silence.quickrecord.R;

public class SplashActivity extends Activity {

    private int SPLASH_DISPLAY_LENGHT; // 延迟六秒
    private int mFileExist;

    //private Context mContext = MyApplication.sAppContext;

    //private String mstrFilePathForDatSave = "";
    //private String mstrFilePathForDat = "";

    public static final String mstrFilePathForDatSave = Environment.getExternalStorageDirectory().toString() + "/tianrui/TianruiWorkroomOCR.dat";
    public static final String mstrFilePathForDat = Environment.getExternalStorageDirectory().toString() + "/tianrui";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.splash);

        //mstrFilePathForDatSave = mContext.getFilesDir().toString() + "/tesseract/bcdata/TianruiWorkroomOCR.dat";
        //mstrFilePathForDat = mContext.getFilesDir().toString() + "/tesseract/bcdata";

        SPLASH_DISPLAY_LENGHT = 1000;
        mFileExist = 0;
        boolean bf1 = fileIsExists(mstrFilePathForDatSave);

        //bf1 = false;
        if (bf1 == false)
        {
            mFileExist = 0;
        }
        else
        {
            mFileExist = 1;
            SPLASH_DISPLAY_LENGHT = 150;
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent mainIntent = new Intent(SplashActivity.this,
                        MainEngineActivity.class);

                if(mFileExist == 0)
                {
                    try {
                        Thread trimmingThread = new Thread( new Runnable(){
                            public void run(){
                                CopyAssets("", mstrFilePathForDat);
                            }});
                        trimmingThread.setName("savingThread");
                        trimmingThread.start();

                        Thread.sleep(SPLASH_DISPLAY_LENGHT);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);

    }

    public boolean fileIsExists(String filePath){
        long fLength = 0;
        try{
            File f = new File(filePath);

            if(!f.exists())
            {
                return false;
            }

            fLength = f.length();
        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        if (fLength != 11140123)
        {
            return false;
        }

        return true;
    }

    private void CopyAssets(String assetDir, String dir) {
        String[] files;
        try {
            files = this.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        // if this directory does not exists, make one.
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {

            }
        }
        for (int i = 0; i < files.length; i++) {
            try {
                String fileName = files[i];
                // we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        CopyAssets(fileName, dir + fileName + "/");
                    } else {
                        CopyAssets(assetDir + "/" + fileName, dir + fileName
                                + "/");
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists())
                    outFile.delete();
                InputStream in = null;
                if (0 != assetDir.length()) {
                    in = getAssets().open(assetDir + "/" + fileName);
                } else {
                    in = getAssets().open(fileName);
                }
                OutputStream out = new FileOutputStream(outFile);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}