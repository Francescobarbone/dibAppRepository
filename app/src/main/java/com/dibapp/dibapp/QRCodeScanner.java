package com.dibapp.dibapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dibapp.dibapp.home.Comment;
import com.dibapp.dibapp.home.CommentCreateActivity;
import com.dibapp.dibapp.home.StudentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCodeScanner extends AppCompatActivity {

    private static final String TAG = "Name: ";
    private Button scanButton;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(QRCodeScanner.this, StudentActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        scanButton = findViewById(R.id.scanBtn);
        final Activity activity = this;

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        final String lessName = getIntent().getStringExtra("name_lesson").trim();
        final String courseid = getIntent().getStringExtra("course_id");
        final String lessid = getIntent().getStringExtra("lesson_id");
        final String user = getIntent().getStringExtra("userMail");
        if(result != null) {
            final String QRString = result.getContents().trim();
            if(QRString.equals(lessName)) {
                Intent QRtoComment = new Intent();
                QRtoComment.putExtra("courseid", courseid);
                QRtoComment.putExtra("lessonid", lessid);
                QRtoComment.putExtra("user", user);
                startActivity(QRtoComment);
            }
             else
                Toast.makeText(this, R.string.errore_corrispondenza, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
}
