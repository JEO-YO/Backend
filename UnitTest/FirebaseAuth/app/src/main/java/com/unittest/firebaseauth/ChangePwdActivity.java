package com.unittest.firebaseauth;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePwdActivity extends AppCompatActivity {
    private Button change_btn, verify_btn;
    private EditText newPwdEditText, pwdEditText;

//    private FirebaseAuth mAuth;

    private final static String TAG = "ChangePwdActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        change_btn = findViewById(R.id.change_btn);
        newPwdEditText = findViewById(R.id.editTextNewPassword);

        change_btn.setVisibility(GONE);
        newPwdEditText.setVisibility(GONE);

        verify_btn = findViewById(R.id.verify_btn);
        pwdEditText = findViewById(R.id.editTextPassword);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth != null){
                    String email = mAuth.getCurrentUser().getEmail();
                    mAuth.signInWithEmailAndPassword(email, pwdEditText.getText().toString())
                            .addOnCompleteListener(ChangePwdActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        change_btn.setVisibility(VISIBLE);
                                        newPwdEditText.setVisibility(VISIBLE);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(ChangePwdActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePwd();
            }
        });
    }

    private void changePwd(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = newPwdEditText.getText().toString();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            Toast.makeText(ChangePwdActivity.this, "password changed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
