package com.unittest.firebaseauth;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPwdActivity extends AppCompatActivity {


    private Button send_btn;
    private EditText email_editText;
    private FirebaseAuth mAuth;
    private String TAG = "ResetPwdActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        mAuth = FirebaseAuth.getInstance();

        send_btn = findViewById(R.id.send_btn);
        email_editText = findViewById(R.id.editTextTextEmailAddress);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = email_editText.getText().toString();
                send(emailAddress);
            }
        });



    }

    private void send(String addr){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(addr)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(ResetPwdActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
//                            openEmailApp();
                        }
                    }
                });
    }

    private void openEmailApp(){

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(ResetPwdActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
