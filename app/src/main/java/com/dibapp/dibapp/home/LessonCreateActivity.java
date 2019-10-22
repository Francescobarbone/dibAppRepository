package com.dibapp.dibapp.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dibapp.dibapp.R;
import com.dibapp.dibapp.autenticazione.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class LessonCreateActivity extends AppCompatActivity {

    private EditText argomento;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth firebaseAuth;
    private Button saveLesson;
    private ImageView image;
    private ImageView generate;
    private static boolean clicked = false;

    //admin per memorizzare le info
    private final User admin = new User();

    @Override
    public void onBackPressed(){
        startActivity(new Intent(LessonCreateActivity.this, TeacherActivity.class));
    }

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
                startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);

        //Istanza firebase dell'utente in uso corrente
        firebaseAuth = FirebaseAuth.getInstance();
        argomento = findViewById(R.id.argText);
        saveLesson = findViewById(R.id.saveLessonButton);
        image = findViewById(R.id.id_qr);
        generate = findViewById(R.id.ic_generate_qr);

        //Memorizza utente in uso corrente
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        //salvo le info dell'user
        if (user != null) {
            admin.setEmail(user.getEmail());
        }

        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                    String email = doc.getString("email");
                    if (email != null && email.equals(admin.getEmail())) {
                        //getting admin's courseID
                        admin.setCourseId(doc.getString("idCorso"));
                        break;
                    }
                }
            }
        });

        saveLesson.setVisibility(View.INVISIBLE);
        //Metodo per la creazione della lezione
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;
                final String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
                final String arg = argomento.getText().toString();

                if(arg.isEmpty()){
                    argomento.setError(getString(R.string.inserire_argomento));
                    argomento.requestFocus();
                } else {
                    MultiFormatWriter mfw  = new MultiFormatWriter();
                    try{
                        BitMatrix btmx = mfw.encode(argomento.getText().toString().trim(), BarcodeFormat.QR_CODE, 300, 300);
                        BarcodeEncoder bce = new BarcodeEncoder();
                        Bitmap btp = bce.createBitmap(btmx);
                        image.setImageBitmap(btp);
                        saveLesson.setVisibility(View.VISIBLE);
                    }catch(WriterException e){
                        e.printStackTrace();
                    }
                    saveLesson.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Lesson less = new Lesson(admin.getCourseId(), currentDate, arg);
                            mFirestore.collection("Courses /" + admin.getCourseId() + "/Lessons").add(less).addOnCompleteListener(LessonCreateActivity.this, new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LessonCreateActivity.this, R.string.lezione_inserita, Toast.LENGTH_SHORT).show();
                                    argomento.setText("");
                                }
                            }
                            });
                        }
                    });
                }
            }
        });
    }
}
