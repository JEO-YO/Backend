package com.unittest.chatservice.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.signin.SignInActivity;
import com.unittest.chatservice.user.model.User;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;
import com.unittest.chatservice.user.service.UserService;
import com.unittest.chatservice.user.service.UserServiceImpl;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        clickCancelButton();
        signUp();
    }

    private void clickCancelButton() {
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });
    }

    private void signUp() {
        Button checkButton = (Button) findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinWithInputText();
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });
    }

    private void joinWithInputText() {
        final EditText emailEditText = (EditText) findViewById(R.id.signUpEmailEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.signUpPasswordEditText);
        UserRepository userRepository = new FirebaseUserRepository();
        UserService userService = new UserServiceImpl(userRepository);
        userService.join(emailEditText.getText().toString(), passwordEditText.getText().toString());
    }
}
