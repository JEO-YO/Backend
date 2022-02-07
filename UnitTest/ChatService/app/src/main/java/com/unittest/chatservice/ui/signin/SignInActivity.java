package com.unittest.chatservice.ui.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.main.MainActivity;
import com.unittest.chatservice.ui.signup.SignUpActivity;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;
import com.unittest.chatservice.user.service.UserService;
import com.unittest.chatservice.user.service.UserServiceImpl;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        moveSignUpActivity();
        moveMainActivity();

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

    private void moveMainActivity() {
        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authWithInputText();
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void authWithInputText() {
        final EditText email = (EditText) findViewById(R.id.signInEmailEditText);
        final EditText password = (EditText) findViewById(R.id.signInPasswordEditText);
        UserRepository userRepository = new FirebaseUserRepository();
        UserService userService = new UserServiceImpl(userRepository);
        userService.auth(email.getText().toString(), password.getText().toString());
    }
}
