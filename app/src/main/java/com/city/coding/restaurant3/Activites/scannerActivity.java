package com.city.coding.restaurant3.Activites;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.city.coding.restaurant3.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class scannerActivity extends AppCompatActivity {
    SurfaceView cameraPreview ;
    BarcodeDetector barcodeDetector ;
    CameraSource cameraSource ;
    SurfaceHolder holder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        //get id's
        cameraPreview = findViewById(R.id.cameraPreview);

        //set overlay
        cameraPreview.setZOrderMediaOverlay(true);
        holder = cameraPreview.getHolder();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE|Barcode.DATA_MATRIX)
                .build();
        //check isOperational
        if(!barcodeDetector.isOperational()){
            Toast.makeText(this,"sorry , could't setUp detector" ,Toast.LENGTH_LONG).show();
            this.finish();
        }
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedFps(24)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(ContextCompat.checkSelfPermission(scannerActivity.this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> detection = detections.getDetectedItems();
                if(detection.size()>0){
                    Intent intent = new Intent();
                    intent.putExtra("barCode",detection.valueAt(0));
                    //Log.e("#code result", detection.valueAt(0).displayValue );
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }






}