package com.nvan.oderdrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nvan.oderdrink.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nvan.oderdrink.Model.User;

public class Register extends AppCompatActivity {
    private TextView signin;
    private EditText username;
    private EditText password;

    private  Button register;

    private FirebaseAuth mAuth;
    public static final String TAG = Register.class.getName();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        signin = findViewById(R.id.SignIn);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.passWord);
        register = findViewById(R.id.bttRegister);


        progressDialog = new ProgressDialog(this);



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oncClickRegister();


            }
        });


    }

    private void onClickAdduser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user");
        String pathObject = String.valueOf(user.getId());
        myRef.child(pathObject).setValue(user);
    }

    private void oncClickRegister() {
        String strEmail = username.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();
                            if (userr == null){
                                return;
                            }
                            String id = userr.getUid();
                            String email = userr.getEmail();
                            String name =  "";
                            String address = "";
                            String phone = "";
                            User user = new User(id,email,name,address,phone);
                            onClickAdduser(user);
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }



}