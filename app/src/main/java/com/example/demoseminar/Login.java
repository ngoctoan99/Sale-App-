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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText editTexttk;
    EditText editTextmk;
    Button btnlogin;
    TextView textViewsignup;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        auth = FirebaseAuth.getInstance();
        editTexttk = findViewById(R.id.emaildn);
        editTextmk = findViewById(R.id.passworddn);
        textViewsignup = findViewById(R.id.tvdk);
        btnlogin = findViewById(R.id.btnlogin);
        progressDialog = new ProgressDialog(this);
        textViewsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signup.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTexttk.getText().toString().trim();
                String pass = editTextmk.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTexttk.setError("Tai khoan khong ton tai");
                    editTexttk.setFocusable(true);
                }
                else if (pass.isEmpty()){
                    editTextmk.setError("Vui long nhap mat khau");
                    editTextmk.setFocusable(true);
                }else if (email.isEmpty()){
                    editTexttk.setError("Vui long nhap email");
                    editTexttk.setFocusable(true);

                }else {
                    loginUser(email,pass);
                }
            }
        });
    }

    private void loginUser(String email, String pass) {
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    FirebaseUser user = auth.getCurrentUser();
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Login that bai !!!",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Login.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}