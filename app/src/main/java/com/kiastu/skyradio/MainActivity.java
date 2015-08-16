package com.kiastu.skyradio;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Camera mCamera;
    private CameraPreview mPreview;
    public static String DEBUG_TAG = "MainActivity";
    private int cameraId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // do we have a camera?
        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        } else {
            mCamera = getCameraInstance();
            configureCamera();
            mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.view_preview);
            preview.addView(mPreview);
            preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCamera.takePicture(null, null, mPicture);

                }
            });
//            Button button = (Button) findViewById(R.id.take_picture);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mCamera.takePicture(null, null,mPicture);
//                }
//            });

            String path = Environment.getExternalStorageDirectory() + "/" + "test.jpg";
            Log.d("File path:",path);
            File file = new File(path);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    Log.d("File Saved", "The file was saved properly!");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.d(DEBUG_TAG, "Error creating media file, check storage permissions:");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                //if nothing went wrong... to base camp!
                sendImageToBase(pictureFile);
            } catch (FileNotFoundException e) {
                Log.d(DEBUG_TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(DEBUG_TAG, "Error accessing file: " + e.getMessage());
            }
            //restart the camera
            mCamera.startPreview();
        }
    };

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    private void configureCamera(){
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        params.setPictureSize(sizes.get(5).width, sizes.get(5).height);
        //find all of the sizes.
        for(Camera.Size size : sizes){
            Log.d("Size","Available Size => Width: "+size.width+" Height: "+size.height);
        }
        mCamera.setParameters(params);
    }
    @Override
    public void onNewIntent(Intent intent){
        String action = intent.getAction();
        if(action.equals(MediaStore.ACTION_IMAGE_CAPTURE)){
            //correct intent, take a picture.
            mCamera.takePicture(null, null, mPicture);
        }
    }
    private void sendImageToBase(File file){

    }
}
