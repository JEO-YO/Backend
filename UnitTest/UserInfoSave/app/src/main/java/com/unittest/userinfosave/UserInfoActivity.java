package com.unittest.userinfosave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class UserInfoActivity extends AppCompatActivity {

    private final HashMap<SendData, String> userInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent intent = getIntent();
        getSendData(intent);
        appendTextView();
    }

    private void getSendData(Intent intent) {
        userInfo.put(SendData.NAME, intent.getStringExtra(String.valueOf(SendData.NAME)));
        userInfo.put(SendData.UNIVERSITY, intent.getStringExtra(String.valueOf(SendData.UNIVERSITY)));
        userInfo.put(SendData.DEPARTMENT, intent.getStringExtra(String.valueOf(SendData.DEPARTMENT)));
    }

    private void appendTextView() {
        TextView textViewName = findViewById(R.id.textView_name);
        TextView textViewUniversity = findViewById(R.id.textView_university);
        TextView textViewDepartment = findViewById(R.id.textView_department);

        textViewName.append(userInfo.get(SendData.NAME));
        textViewUniversity.append(userInfo.get(SendData.UNIVERSITY));
        textViewDepartment.append(userInfo.get(SendData.DEPARTMENT));
    }
}