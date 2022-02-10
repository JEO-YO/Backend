package com.unittest.chatservice.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.signin.SignInActivity;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;
import com.unittest.chatservice.user.service.UserService;
import com.unittest.chatservice.user.service.UserServiceImpl;

public class SignUpActivity extends AppCompatActivity {

    private final UserRepository userRepository = new FirebaseUserRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        clickCancelButton();
        signUp();
    }

    private void clickCancelButton() {
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            finish();
        });
    }

    private void signUp() {
        Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(view -> {
            joinWithInputText();
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            finish();
        });
    }

    private void joinWithInputText() {
        final EditText emailEditText = findViewById(R.id.signUpEmailEditText);
        final EditText passwordEditText = findViewById(R.id.signUpPasswordEditText);
        UserService userService = new UserServiceImpl(userRepository);
        userService.join(emailEditText.getText().toString(), passwordEditText.getText().toString());
    }
}
