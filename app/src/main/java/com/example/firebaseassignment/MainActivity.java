package com.example.firebaseassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText naame,cnnic;
    ProgressDialog pd;
    ArrayList<User> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        naame = findViewById(R.id.edttxtname);
        cnnic = findViewById(R.id.edttxtcnic);
        pd = new ProgressDialog(MainActivity.this);
        list = new ArrayList<>();
    }

    public void searchData(View view) {
        String name = naame.getText().toString().trim();
        String cnic = cnnic.getText().toString().trim();
        Searchname(name);
        Searchcnic(cnic);
    }

    private void Searchcnic(String cnic) {
        pd.setTitle("Searching..");
        pd.show();
        db.collection("UserData").whereEqualTo("cnic",cnic).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                pd.dismiss();
                list = new ArrayList<>();
                for(DocumentSnapshot doc: task.getResult())
                {
                    User user = new User();
                    user.setId(doc.getString("id"));
                    user.setName(doc.getString("name"));
                    user.setAge(doc.getString("age"));
                    user.setCnic(doc.getString("cnic"));
                    user.setSemester(doc.getString("semester"));
                    user.setCgpa(doc.getString("gpa"));
                    String id = doc.getString("id");

                    list.add(user);
                    Log.d("TAG", "onComplete: get Data from ID" + id);
                    Toast.makeText(MainActivity.this, "name ="+user.getName() + " , cnic : "+ user.getCnic(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,homePage.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",user.getId());
                    bundle.putString("name",user.getName());
                    bundle.putString("cnic",user.getCnic());
                    bundle.putString("age",user.getAge());
                    bundle.putString("semester",user.getSemester());
                    bundle.putString("cgpa",user.getCgpa());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to find cnic : "+ e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Searchname(String name) {

        db.collection("UserData").whereEqualTo("name",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                Toast.makeText(MainActivity.this, "Name matched..", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to find name : "+ e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerPage(View view) {
        Intent intent = new Intent(MainActivity.this,registerData.class);
        startActivity(intent);
    }
}