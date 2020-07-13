package com.harshita.teach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText email;
    private EditText password;
    private Button signUpBtn;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.password_signup);
        signUpBtn = findViewById(R.id.signup_button_signup);
        login = findViewById(R.id.login_text_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailID = email.getText().toString();
                String paswd = password.getText().toString();
                if (emailID.isEmpty()) {
                    email.setError("Provide your Email first!");
                    email.requestFocus();
                } else if (paswd.isEmpty()) {
                    password.setError("Set your password");
                    password.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty())) {

                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).
                            addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(I);
                finish();
            }
        });
    }
}


