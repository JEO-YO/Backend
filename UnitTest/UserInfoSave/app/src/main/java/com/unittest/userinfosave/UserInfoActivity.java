package com.unittest.userinfosave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.unittest.userinfosave.user.UserInfo;
import java.util.HashMap;

public class UserInfoActivity extends AppCompatActivity {

    private final HashMap<UserInfo, String> userInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent intent = getIntent();
        getSendData(intent);
        appendTextView();
    }

    private void getSendData(Intent intent) {
        userInfo.put(UserInfo.NAME, intent.getStringExtra(String.valueOf(UserInfo.NAME)));
        userInfo.put(UserInfo.UNIVERSITY, intent.getStringExtra(String.valueOf(UserInfo.UNIVERSITY)));
        userInfo.put(UserInfo.DEPARTMENT, intent.getStringExtra(String.valueOf(UserInfo.DEPARTMENT)));
    }

    private void appendTextView() {
        TextView textViewName = findViewById(R.id.textView_name);
        TextView textViewUniversity = findViewById(R.id.textView_university);
        TextView textViewDepartment = findViewById(R.id.textView_department);

        textViewName.append(userInfo.get(UserInfo.NAME));
        textViewUniversity.append(userInfo.get(UserInfo.UNIVERSITY));
        textViewDepartment.append(userInfo.get(UserInfo.DEPARTMENT));
    }
}