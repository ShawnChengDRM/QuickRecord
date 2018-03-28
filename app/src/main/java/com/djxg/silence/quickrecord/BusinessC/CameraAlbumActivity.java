package com.djxg.silence.quickrecord.BusinessC;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.djxg.silence.quickrecord.MyApplication;
import com.djxg.silence.quickrecord.R;
import com.djxg.silence.quickrecord.ScannerActivity;
import com.djxg.silence.quickrecord.editor.EditActivity;
import com.djxg.silence.quickrecord.tess.TessEngine;
import com.djxg.silence.quickrecord.utils.ProcessUtils;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class CameraAlbumActivity extends AppCompatActivity {

    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_ALBUM = 2;
    public static final int REQUEST_CROP = 3;
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private Button mPictureIb;
    private Button mCameraIb;
    private ImageView mImageShow;

    private File mImageFile;

    private Uri mImageUri;

    private byte[] mRotatedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_album);

        mPictureIb =  findViewById(R.id.ib_picture);
        mCameraIb = findViewById(R.id.ib_camera);
        mImageShow = findViewById(R.id.image_show);

        mCameraIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCamera();
            }
        });

        mPictureIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(CameraAlbumActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CameraAlbumActivity.this,
                            new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    selectAlbum();
                }

            }
        });
    }


    private void selectCamera() {

        createImageFile();
        //Toast.makeText(this, mImageUri+"AAA", Toast.LENGTH_SHORT).show();

        //启动相机程序
        //Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }
    private void selectAlbum() {

        //打开相册
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(albumIntent, REQUEST_ALBUM);
    }
    private void cropImage(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImageFile));
        startActivityForResult(intent, REQUEST_CROP);
    }
    private void createImageFile() {

        //创建File对象，用于存储拍照后的图片
        mImageFile = new File(getExternalCacheDir(), "output_image.jpg");

        try {
            if (mImageFile.exists()) {
                mImageFile.delete();
            }
            mImageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "出错啦", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            mImageUri = FileProvider.getUriForFile(CameraAlbumActivity.this,
                    "com.djxg.silence.quickrecord.fileprovider", mImageFile);

        } else {
            mImageUri = Uri.fromFile(mImageFile);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bmpGray;
        Bitmap binarymap;

        TessEngine tessEngine;
        String result;

        if (RESULT_OK != resultCode) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CAMERA:
                //cropImage(mImageUri);
/*                Bitmap bitmap_c = null;
                try {
                    bitmap_c = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(mImageUri));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/

                Bitmap bitmap_c = data.getParcelableExtra("data");

                mImageShow.setImageBitmap(bitmap_c);


                tessEngine = TessEngine.Generate(MyApplication.sAppContext);
                bmpGray = ProcessUtils.bitmap2Gray(bitmap_c);
                binarymap = ProcessUtils.gray2Binary(bmpGray);
                result = tessEngine.detectText(binarymap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (bitmap_c != null) {
                    bmpGray.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                }
                byte[] bytes = stream.toByteArray();

                Intent intent = new Intent(CameraAlbumActivity.this, EditActivity.class);
                intent.putExtra("resultImage", bytes);
                intent.putExtra("resultText", result);
                startActivity(intent);



                break;
            case REQUEST_ALBUM:
                createImageFile();

                //Bitmap bitmap1 = null;
                if (!mImageFile.exists()) {
                    return;
                }
                Uri uri = data.getData();
                if (uri != null) {

                    cropImage(uri);
                }


                break;
            case REQUEST_CROP:
                //mPictureIb.setImageURI(Uri.fromFile(mImageFile));
                //mImageShow.setImageURI(mImageUri);


                Bitmap bitmap_p = null;
                try {
                    bitmap_p = BitmapFactory.decodeStream(getContentResolver()
                            .openInputStream(mImageUri));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                mImageShow.setImageBitmap(bitmap_p);

                bmpGray = ProcessUtils.bitmap2Gray(bitmap_p);
                binarymap = ProcessUtils.gray2Binary(bmpGray);

                tessEngine = TessEngine.Generate(MyApplication.sAppContext);
                result = tessEngine.detectText(binarymap);

                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                if (bitmap_p != null) {
                    binarymap.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
                }
                byte[] bytes1 = stream1.toByteArray();



                Intent intent_p = new Intent(CameraAlbumActivity.this, EditActivity.class);
                intent_p.putExtra("resultImage", bytes1);
                intent_p.putExtra("resultText", result);
                startActivity(intent_p);

                break;

            default:
                break;
        }
    }

}
