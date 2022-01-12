package com.example.firebaseassignment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder>{

    Context context;
    ArrayList<User> list;
    ProgressDialog pd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public myAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = list.get(position);
        holder.name.setText(user.getName());
        holder.cnic.setText(user.getCnic());
        holder.cnic.setFocusable(false);
        holder.cnic.setEnabled(false);
        holder.age.setText(user.getAge());
        holder.semester.setText(user.getSemester());
        holder.cgpa.setText(user.getCgpa());
        pd = new ProgressDialog((homePage)context);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = holder.name.getText().toString();
                String umar = holder.age.getText().toString().trim();
                String sem = holder.semester.getText().toString().trim();
                String karname = holder.cgpa.getText().toString().trim();
                pd.setTitle("Updating..");
                pd.show();
                db.collection("UserData").document(list.get(position).id).update("name",naam,"age",umar,"semester",sem,"cgpa",karname).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(context, "Successfully Updated,Login again..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                        ((homePage)context).finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(context, "Failed to update: "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setTitle("Deleting..");
                pd.show();

                db.collection("UserData").document(list.get(position).id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(context, "Successfully deleted,Session expired..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                        ((homePage)context).finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(context, "Failed to delete: "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        EditText name,cnic,age,semester,cgpa;
        Button update,delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            cnic = itemView.findViewById(R.id.tvcnic);
            age  = itemView.findViewById(R.id.tvage);
            semester  = itemView.findViewById(R.id.tvsemester);
            cgpa = itemView.findViewById(R.id.tvcgpa);
            update = itemView.findViewById(R.id.btnupdatedata);
            delete = itemView.findViewById(R.id.btndeletedata);
        }
    }
}
