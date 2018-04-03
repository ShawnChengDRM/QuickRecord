package com.djxg.silence.quickrecord.editor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.bean.Record;
import com.djxg.silence.quickrecord.database.DataBaseTool;

import org.litepal.tablemanager.Connector;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    byte[] editImage;
    String editText;
    Bitmap bitmap;
    ImageView imageView;
    EditText editText1;
    Button editExit;
    Button editSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();

        editExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建数据库
                Connector.getDatabase();
                //打包保存数据
                DataBaseTool.Bale(bitmap,editText);

                finish();
            }
        });

    }

/*    public static void actionStart(Context context, byte[] editImage, String editText) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("resultImage", editImage);
        intent.putExtra("resultText", editText);
        context.startActivity(intent);
    }*/

    private void initView() {
        imageView = findViewById(R.id.edit_image);
        editText1 = findViewById(R.id.edit_text);
        editExit = findViewById(R.id.edit_exit);
        editSave = findViewById(R.id.edit_save);

        initData();

        imageView.setImageBitmap(bitmap);
        editText1.setText(editText);
    }

    private void initData() {
        Intent intent = getIntent();
        editImage = intent.getByteArrayExtra("resultImage");
        editText = intent.getStringExtra("resultText");
        //Toast.makeText(EditActivity.this, editText, Toast.LENGTH_LONG).show();

        bitmap = BitmapFactory.decodeByteArray(editImage, 0, editImage.length);
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
