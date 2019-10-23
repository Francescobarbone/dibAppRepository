package com.dibapp.dibapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dibapp.dibapp.autenticazione.MainActivity;
import com.dibapp.dibapp.home.Comment;
import com.dibapp.dibapp.home.CommentCreateActivity;
import com.dibapp.dibapp.home.StudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

public class QRCodeScanner extends AppCompatActivity {

    private static final String TAG = "Name: ";
    private Button scanButton;
    private FirebaseFirestore mFirestore;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if(item.getItemId() == R.id.home){
                startActivity(new Intent(getApplicationContext(), StudentActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

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

        //Avvio fotocamera per la scansione del QR
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

    /*
    * Effettuo un controllo sulla scansione effettuata.
    * Se il codice scansionato è diverso da quello del codice della lezione, si ha un messaggio di errore.
    *
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //Ottenimento info da LessonListAdapter
        final String lessName = Objects.requireNonNull(getIntent().getStringExtra("name_lesson")).trim();
        final String courseid = getIntent().getStringExtra("course_id");
        final String lessid = getIntent().getStringExtra("lesson_id");
        final String user = getIntent().getStringExtra("userMail");
        if(result != null) {
            final String QRString = result.getContents().trim();
            if(QRString.equals(lessName)) {
                Intent QRtoComment = new Intent(this, CommentCreateActivity.class);
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
