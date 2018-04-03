package com.djxg.silence.quickrecord.BusinessC;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.bean.Record;
import com.djxg.silence.quickrecord.database.DataBaseTool;
import com.tianruiworkroombcr.Native;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import org.litepal.tablemanager.Connector;


public class WebViewActivity extends Activity {
    private WebView picView;
    private EditText bCard;
    private Button bcardExit;
    private Button bcardSave;

    Bitmap bitmap_r;

    String textRlt;

    private final int IMG_PICK = 1;

    public  static String[] mwholeTextLine;//
    public  static int[] mwholdTextLineType;//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        picView = findViewById(R.id.pic_view);
        picView.setBackgroundColor(0);
        bCard = findViewById(R.id.b_card_result);
        bcardExit = findViewById(R.id.b_card_exit);
        bcardSave = findViewById(R.id.b_card_save);


        WebSettings webSettings = picView.getSettings();
        webSettings.setBuiltInZoomControls(true);//support zoom
        webSettings.setUseWideViewPort(true);//
        mwholeTextLine = MainEngineActivity.mwholeTextLine;
        mwholdTextLineType = MainEngineActivity.mwholdTextLineType;//
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        bitmap_r = BitmapFactory.decodeFile(MainEngineActivity.mImgFilePath);
/*        try {
            bitmap_r = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(MainEngineActivity.mImgFilePath));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/






        int mDensity = metrics.densityDpi;
        webSettings.setDefaultZoom(ZoomDensity.MEDIUM);

        //获取系统的换行符
        String Newline = System.getProperty("line.separator");

        String textStr = "<HTML><IMG style = width:50% src=\"" + MainEngineActivity.mImgFilePath +"\"" + "/>";

        textRlt = "";

        textRlt += Newline + "当前选择的识别语言是：[" + MainEngineActivity.strLanguage[MainEngineActivity.mStaticSelectedItem] + "]" + Newline;
        for (int i = 0; i < mwholeTextLine.length; i++) {
            if (mwholdTextLineType[i] == Native.TIANRUI_CARD_NAME) {
                mwholeTextLine[i] = "[姓名] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_ADDRESS) {
                mwholeTextLine[i] = "[地址] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_CJK_SECOND_NAME) {
                mwholeTextLine[i] = "[其它姓名] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_COMPANY_NAME) {
                mwholeTextLine[i] = "[公司名] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_DEPT) {
                mwholeTextLine[i] = "[部门名] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_EMAIL) {
                mwholeTextLine[i] = "[电子邮件] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_FAX) {
                mwholeTextLine[i] = "[传真] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_FIRST_NAME) {
                mwholeTextLine[i] = "[名] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_HOMETEL) {
                mwholeTextLine[i] = "[家庭电话] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_ICQ) {
                mwholeTextLine[i] = "[ICQ] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_JOBTITLE) {
                mwholeTextLine[i] = "[职位] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_LAST_NAME) {
                mwholeTextLine[i] = "[姓] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_MOBILE) {
                mwholeTextLine[i] = "[移动电话] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_MSN) {
                mwholeTextLine[i] = "[MSN] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_NOTE) {
                mwholeTextLine[i] = "[其他] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_POSTCODE) {
                mwholeTextLine[i] = "[邮编] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_QQ) {
                mwholeTextLine[i] = "[QQ] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_WEBSITE) {
                mwholeTextLine[i] = "[网址] : " + mwholeTextLine[i];
            } else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_WORKTEL) {
                mwholeTextLine[i] = "[办公电话] : " + mwholeTextLine[i];
            } else {
                mwholeTextLine[i] = "[其他] : " + mwholeTextLine[i];
            }

            textRlt += Newline + mwholeTextLine[i] + Newline;
        }

        bCard.setText(textRlt);

        bcardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bcardSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //创建数据库
                Connector.getDatabase();
                //打包保存数据
                DataBaseTool.Bale(bitmap_r,textRlt);
                finish();

            }
        });


/*        //textRlt += "<br>" + "注意：测试开发包，当文本行字符数多于4个时会少输出一个字符！" + "<br/>";

        textRlt += "<br>" + "当前选择的识别语言是：[" + MainEngineActivity.strLanguage[MainEngineActivity.mStaticSelectedItem] + "]" + "<br/>";
        for (int i = 0; i < mwholeTextLine.length; i++)
        {
            if (mwholdTextLineType[i] == Native.TIANRUI_CARD_NAME)
            {
                mwholeTextLine[i] = "[姓名] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_ADDRESS)
            {
                mwholeTextLine[i] = "[地址] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_CJK_SECOND_NAME)
            {
                mwholeTextLine[i] = "[其它姓名] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_COMPANY_NAME)
            {
                mwholeTextLine[i] = "[公司名] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_DEPT)
            {
                mwholeTextLine[i] = "[部门名] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_EMAIL)
            {
                mwholeTextLine[i] = "[电子邮件] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_FAX)
            {
                mwholeTextLine[i] = "[传真] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_FIRST_NAME)
            {
                mwholeTextLine[i] = "[名] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_HOMETEL)
            {
                mwholeTextLine[i] = "[家庭电话] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_ICQ)
            {
                mwholeTextLine[i] = "[ICQ] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_JOBTITLE)
            {
                mwholeTextLine[i] = "[职位] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_LAST_NAME)
            {
                mwholeTextLine[i] = "[姓] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_MOBILE)
            {
                mwholeTextLine[i] = "[移动电话] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_MSN)
            {
                mwholeTextLine[i] = "[MSN] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_NOTE)
            {
                mwholeTextLine[i] = "[其他] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_POSTCODE)
            {
                mwholeTextLine[i] = "[邮编] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_QQ)
            {
                mwholeTextLine[i] = "[QQ] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_WEBSITE)
            {
                mwholeTextLine[i] = "[网址] : " + mwholeTextLine[i];
            }
            else if (mwholdTextLineType[i] == Native.TIANRUI_CARD_WORKTEL)
            {
                mwholeTextLine[i] = "[办公电话] : " + mwholeTextLine[i];
            }
            else
            {
                mwholeTextLine[i] = "[其他] : " + mwholeTextLine[i];
            }

            textRlt += "<br>" + mwholeTextLine[i] + "<br/>";
        }*/

        picView.loadDataWithBaseURL("file:///",
                "<html>" +
                        "<body>" + textStr + "</body>" +
                        "</html>"
                , "text/html", "utf-8", null);

    }

/*    public void Bale(Bitmap bitmapB, String textB) {

        Record recordB = new Record();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapB.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        //options为100表示不压缩
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            bitmapB.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }

        byte[] bytesB = baos.toByteArray();

        String time = getNowDateKeyStr();

        String text = textB;

        recordB.setRecord_image(bytesB);
        recordB.setRecord_text(text);
        recordB.setRecord_time(time);

        recordB.save();

    }

    public static String getNowDateKeyStr(){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd-HH:mm:ss");// 设置日期格式
        String nowdate = df.format(new Date());// new Date()为获取当前系统时间
        return nowdate;
    }*/
}
