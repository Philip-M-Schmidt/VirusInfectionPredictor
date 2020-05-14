package com.alfadasApps.Virus_Infection_Predictor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.alfadasApps.Virus_Infection_Predictor.Helper.GraphicOverlay;
import com.alfadasApps.Virus_Infection_Predictor.Helper.RectOverlay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.google.android.gms.vision.CameraSource.CAMERA_FACING_BACK;
import static com.google.android.gms.vision.CameraSource.CAMERA_FACING_FRONT;

public class photoActivity extends AppCompatActivity {

            Button coronaDetectButton;
            Button switchCameraButton;
            private GraphicOverlay graphicOverlay;
            private CameraView cameraView;

            AlertDialog alertDialog;

            @Override
            protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.photo_main);

                coronaDetectButton = findViewById(R.id.detect_virus_btn);
                switchCameraButton = findViewById(R.id.switch_camera);
                graphicOverlay = findViewById(R.id.graphic_correct_overlay);
                cameraView = findViewById(R.id.camera_view);



        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Please wait, Diagnosis in process...")
                .setCancelable(false)
                .build();

        coronaDetectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                cameraView.start();
                cameraView.captureImage();
                graphicOverlay.clear();

            }
        });

        switchCameraButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view)
            {

                if(cameraView.getFacing() == CAMERA_FACING_BACK){
                    cameraView.setFacing(CAMERA_FACING_FRONT);
                }
                else{
                    cameraView.setFacing(CAMERA_FACING_BACK);
            }
            }
        });


        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage)
            {
                alertDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(), false);
                cameraView.stop();

                processCoronaDetection(bitmap);


            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }


    private void processCoronaDetection(Bitmap bitmap)
    {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions = new FirebaseVisionFaceDetectorOptions.Builder().build();

        FirebaseVisionFaceDetector firebaseVisionFaceDetector = FirebaseVision.getInstance()
                .getVisionFaceDetector(firebaseVisionFaceDetectorOptions);

        firebaseVisionFaceDetector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces)
                    {
                        getCoronaResults(firebaseVisionFaces);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(photoActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCoronaResults(List<FirebaseVisionFace> firebaseVisionFaces)
    {
        int counter = 0;
        for (FirebaseVisionFace face : firebaseVisionFaces)
        {
            Rect rect = face.getBoundingBox();
            RectOverlay rectOverlay = new RectOverlay(graphicOverlay, rect);

            graphicOverlay.add(rectOverlay);
            counter = counter + 1;
        }
        alertDialog.dismiss();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
                startActivity(intent);
            }
        }, 3000);   //5 seconds
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }
}

