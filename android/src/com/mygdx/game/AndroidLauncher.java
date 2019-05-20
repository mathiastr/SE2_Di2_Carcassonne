package com.mygdx.game;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidEventListener;

import java.io.ByteArrayOutputStream;

public class  AndroidLauncher extends AndroidApplication {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Carcassonne.PhotoCallback lastPhotoCallback;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Carcassonne(new Carcassonne.NativeInterface() {
			@Override
			public void getPhoto(Carcassonne.PhotoCallback cb) {
                dispatchTakePictureIntent();
                lastPhotoCallback = cb;
			}
		}), config);

		addAndroidEventListener(new AndroidEventListener() {
			@Override
			public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap bmp = (Bitmap) extras.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    bmp.recycle();
                    lastPhotoCallback.onPhotoReady(byteArray);
                    lastPhotoCallback = null;
                }
			}
		});
	}

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
