package com.djxg.silence.quickrecord.editor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.djxg.silence.quickrecord.R;

public class EditActivity extends AppCompatActivity {

    byte[] editImage;
    String editText;
    Bitmap bitmap;
    ImageView imageView;
    EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
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
}
