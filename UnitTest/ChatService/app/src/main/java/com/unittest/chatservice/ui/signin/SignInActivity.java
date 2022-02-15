package com.unittest.chatservice.ui.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.userlist.UserListActivity;
import com.unittest.chatservice.ui.signup.SignUpActivity;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;
import com.unittest.chatservice.user.service.UserService;
import com.unittest.chatservice.user.service.UserServiceImpl;

public class SignInActivity extends AppCompatActivity {

    private final UserRepository userRepository = new FirebaseUserRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        moveSignUpActivity();
        moveMainActivity();
    }

    private void moveSignUpActivity() {
        final Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            finish();
        });
    }

    private void moveMainActivity() {
        final Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(view -> {
            authWithInputText();
            startActivity(new Intent(SignInActivity.this, UserListActivity.class));
            finish();
        });
    }

    private void authWithInputText() {
        final EditText email = findViewById(R.id.signInEmailEditText);
        final EditText password = findViewById(R.id.signInPasswordEditText);
        final UserService userService = new UserServiceImpl(userRepository);
        userService.auth(email.getText().toString(), password.getText().toString());
    }
}
