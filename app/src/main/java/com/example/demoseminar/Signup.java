package com.example.demoseminar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText editTexttkdk;
    EditText editTextmkdk;
    Button btnsignup , btnback;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_signup);
        editTexttkdk = findViewById(R.id.emaildk);
        editTextmkdk = findViewById(R.id.passworddk);
        btnsignup = findViewById(R.id.btnsignup);
        btnback = findViewById(R.id.btnback);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
                finish();
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTexttkdk.getText().toString().trim();
                String pass = editTextmkdk.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTexttkdk.setError("Tai khoan khong hop le");
                    editTexttkdk.setFocusable(true);
                }else if (pass.length() < 6){
                    editTextmkdk.setError("Mat khau it nhat la 6 ki tu");
                    editTextmkdk.setFocusable(true);
                }else if (pass.isEmpty()){
                    editTextmkdk.setError("Vui long nhap mat khau");
                    editTextmkdk.setFocusable(true);
                }else if (email.isEmpty()){
                    editTexttkdk.setError("Vui long nhap email");
                    editTexttkdk.setFocusable(true);
                }
                else {
                    registerUser(email,pass);
                }
            }
        });
    }

    private void registerUser(String email, String pass) {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    FirebaseUser user = auth.getCurrentUser();
                    String email = user.getEmail();
                    String uid = user.getUid();
                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("email",email);
                    hashMap.put("uid",uid);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("User");
                    reference.child(uid).setValue(hashMap);
                    Toast.makeText(Signup.this,"Register...",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signup.this,Login.class));
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(Signup.this,"Dang ki that bai",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Signup.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}