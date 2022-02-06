package com.unittest.chatservice.ui.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.main.MainActivity;
import com.unittest.chatservice.ui.signup.SignUpActivity;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;

import java.io.Serializable;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "MSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        moveSignUpActivity();
        UserRepository userRepository = new FirebaseUserRepository();
        moveMainActivity(userRepository);

    }

    private void moveSignUpActivity() {
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }

    private void moveMainActivity(UserRepository userRepository) {
        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText email = (EditText) findViewById(R.id.signInEmailEditText);
                final EditText password = (EditText) findViewById(R.id.signInPasswordEditText);
                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    intent.putExtra("currentUser", currentUser);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                            }
                        });
            }
        });
    }
}
