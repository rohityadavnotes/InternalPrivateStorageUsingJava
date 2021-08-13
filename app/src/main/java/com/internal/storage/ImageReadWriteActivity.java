package com.internal.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.internal.storage.utilities.InternalPrivateFileUtils;

public class ImageReadWriteActivity extends AppCompatActivity {

    public static final String TAG = ImageReadWriteActivity.class.getSimpleName();

    private ImageView writeImageView, readImageView;
    private Button writeButton, readButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_read_write);
        initializeView();
        initializeEvent();
    }

    private void initializeView() {
        writeImageView = findViewById(R.id.writeImageView);
        readImageView  = findViewById(R.id.readImageView);
        writeButton    = findViewById(R.id.writeButton);
        readButton     = findViewById(R.id.readButton);
    }

    private void initializeEvent() {
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }

    private void saveImage() {
        BitmapDrawable drawable = (BitmapDrawable) writeImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        InternalPrivateFileUtils.saveImageBitmap(ImageReadWriteActivity.this, bitmap, "image_1", "jpg");
    }

    private void deleteImage() {
        InternalPrivateFileUtils.deleteImageBitmap(ImageReadWriteActivity.this, "image_1", "jpg");
    }

    private void loadImage() {
        Bitmap bitmap = InternalPrivateFileUtils.loadImageBitmap(ImageReadWriteActivity.this, "image_1", "jpg");
        readImageView.setImageBitmap(bitmap);
        if (bitmap == null) {
            Toast.makeText(ImageReadWriteActivity.this, "No image found", Toast.LENGTH_SHORT).show();
        }
    }
}