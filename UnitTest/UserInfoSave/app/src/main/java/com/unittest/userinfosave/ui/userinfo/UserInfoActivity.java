package com.unittest.userinfosave.ui.userinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unittest.userinfosave.AppConfig;
import com.unittest.userinfosave.R;
import com.unittest.userinfosave.user.User;
import com.unittest.userinfosave.user.UserInfo;
import com.unittest.userinfosave.ui.main.MainActivity;

import java.util.HashMap;

public class UserInfoActivity extends AppCompatActivity {

    private HashMap<UserInfo, java.lang.String> userInfo;
    private final AppConfig appConfig = new AppConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        getSendData();
        clickEvent();
    }

    private void clickEvent() {
        String toFindArea = "Seoul";

        clickJoin();
        clickFind(toFindArea);
    }

    private void clickFind(String toFindArea) {
        Button btnFind = (Button) findViewById(R.id.btn_find);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appConfig.userService().find(toFindArea);
                Toast.makeText(UserInfoActivity.this, "찾기 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickJoin() {
        Button btnJoin = (Button) findViewById(R.id.btn_join);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                putUserInfo();
                User user = new User(1L, userInfo);
                appConfig.userService().join(user);
                Toast.makeText(UserInfoActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void putUserInfo() {
        EditText activityArea = (EditText) findViewById(R.id.editText_activityArea);
        EditText interesting = (EditText) findViewById(R.id.editText_interesting);

        userInfo.put(UserInfo.ACTIVITY_AREA, activityArea.getText().toString());
        userInfo.put(UserInfo.INTERESTING, interesting.getText().toString());
    }

    private void getSendData() {
        Intent intent = getIntent();
        userInfo = (HashMap<UserInfo, java.lang.String>) intent.getSerializableExtra(MainActivity.USER_INFO);
        appendTextView();
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
