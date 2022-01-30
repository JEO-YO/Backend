package com.unittest.userinfosave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.unittest.userinfosave.model.user.UserInfo;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final HashMap<UserInfo, String> userInfo = new HashMap<>();
    public static final String USER_INFO = "userInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonToUserInfo = (Button) findViewById(R.id.btn_next);
        buttonToUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveUserInfoPage();
            }
        });
    }

    private void moveUserInfoPage() {
        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
        sendData(intent);
        startActivity(intent);
    }

    private void sendData(Intent intent) {
        putUserInfo();
        intent.putExtra(USER_INFO, userInfo);
    }

    private void putUserInfo() {
        EditText name = (EditText) findViewById(R.id.editText_name);
        EditText university = (EditText) findViewById(R.id.editText_university);
        EditText department = (EditText) findViewById(R.id.editText_department);

        userInfo.put(UserInfo.NAME, name.getText().toString());
        userInfo.put(UserInfo.UNIVERSITY, university.getText().toString());
        userInfo.put(UserInfo.DEPARTMENT, department.getText().toString());
    }
}
