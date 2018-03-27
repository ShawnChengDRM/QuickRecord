package com.djxg.silence.quickrecord.tess;

/**
 * Created by silence on 2018/1/4.
 */
import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BcardDataManager {

    private static final String TAG = "DBG_" + BcardDataManager.class.getName();

/*    private static String bcardDataPath = "";

    public static String getBcardFolder() {
        return bcardDataPath;
    }*/

    private static void copyFiles(Context context, File dir) {
        try {
            //你想复试的本地文件位置
            String filepath = dir + "/TianruiWorkroomOCR.dat";

            //访问AssetManager
            AssetManager assetManager = context.getAssets();

            //通过比特流进行读写操作
            InputStream inputStream = assetManager.open("bcdata/TianruiWorkroomOCR.dat");
            OutputStream outputStream = new FileOutputStream(filepath);

            //将文件复制到文件路径指定的位置
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkFile(Context context, File dir) {

        //如果目标目录不存在则创建它
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(context, dir);
        }

        //如果目录存在，但是没有数据文件
        if (dir.exists()) {
            String datafilepath = dir + "/TianruiWorkroomOCR.dat";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles(context, dir);
                //tesseractDataPath = context.getFilesDir() + "/tesseract/";
            }
        }
    }

}

