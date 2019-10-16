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
        Intent regToMain = new Intent(QRCodeScanner.this, StudentActivity.class);
        startActivity(regToMain);
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
        if(result!=null) {
            String scanningContent = result.getContents();
            final String corso = scanningContent.substring(0,20);
            final String lezione = scanningContent.substring(20,40);
            FirebaseFirestore mFirebase = FirebaseFirestore.getInstance();
            FirebaseAuth user = FirebaseAuth.getInstance();
            final String userEmail = user.getCurrentUser().getEmail();

            mFirebase.collection("Courses /"+corso+"/Lessons").addSnapshotListener(QRCodeScanner.this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if(e!=null){
                        Log.d(TAG, "error : "+ e.getMessage());

                    }

                    boolean isSub = false;

                    for(DocumentSnapshot doc: documentSnapshots){
                        String lessID = doc.getId();

                        if(lessID.equals(lezione)){
                            isSub = true;
                            break;
                        }
                    }

                    if(isSub){
                        Intent intent = new Intent(QRCodeScanner.this, CommentCreateActivity.class);
                        intent.putExtra("idLezione",lezione);
                        intent.putExtra("idCorso",corso);
                        intent.putExtra("email",userEmail);
                        startActivity(intent);
                    }else Toast.makeText(QRCodeScanner.this, "Lezione non Trovata", Toast.LENGTH_SHORT).show();

                }
            });

            if(result.getContents()==null)
                Toast.makeText(this, "Errore di scan", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }
}
