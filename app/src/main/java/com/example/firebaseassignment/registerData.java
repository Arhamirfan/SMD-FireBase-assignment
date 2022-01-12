package com.example.firebaseassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class registerData extends AppCompatActivity {

    EditText name,cnic,age,semester,gpa;
    ProgressDialog pd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        name = findViewById(R.id.edtname);
        cnic = findViewById(R.id.edtcnic);
        age = findViewById(R.id.edtage);
        semester = findViewById(R.id.edtsemester);
        gpa = findViewById(R.id.edtgpa);
        pd = new ProgressDialog(registerData.this);


    }

    public void registerToFirebase(View view) {

        String naam = name.getText().toString();
        String snakth = cnic.getText().toString().trim();
        String umar = age.getText().toString().trim();
        String sem = semester.getText().toString().trim();
        String karname = gpa.getText().toString().trim();
        uploadData(naam,snakth,umar,sem,karname);
    }

    private void uploadData(String naam, String snakth, String umar, String sem, String karname) {
        pd.setTitle("Adding data to Firebase");
        pd.show();
        String id = UUID.randomUUID().toString();
        Map<String,Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("name",naam);
        doc.put("cnic",snakth);
        doc.put("age",umar);
        doc.put("semester",sem);
        doc.put("gpa",karname);
        db.collection("UserData").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(registerData.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(registerData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(registerData.this, "Failed: "+ e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}