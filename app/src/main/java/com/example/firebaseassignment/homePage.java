package com.example.firebaseassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class homePage extends AppCompatActivity {

    RecyclerView recyclerView;
    myAdapter myadapter;
    ArrayList<User> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.userlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
//        list = bundle.getParcelableArrayList("list");
        String id = bundle.getString("id");
        String  name= bundle.getString("name");
        String  cnic= bundle.getString("cnic");
        String  age= bundle.getString("age");
        String  semester= bundle.getString("semester");
        String  cgpa= bundle.getString("cgpa");
        User user = new User(id,name,cnic,age,semester,cgpa);
        list.add(user);
        //list.add((User) getIntent().getSerializableExtra("list"));
        Toast.makeText(this, "Logged in as: "+ list.get(0).getName(), Toast.LENGTH_SHORT).show();
        myadapter = new myAdapter(this,list);
        recyclerView.setAdapter(myadapter);
    }
}