package com.tan90.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by I320626 on 11/13/2015.
 */
public class imagePickActivity extends Activity {
    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepick);
        imageView = (ImageView) findViewById(R.id.result);
    }

    public void pickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream inputStream = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try{
                if (bitmap != null) {
                    bitmap.recycle();
                }
                inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
