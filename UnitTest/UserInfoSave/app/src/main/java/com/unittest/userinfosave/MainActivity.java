package com.unittest.userinfosave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public static final String SEND_NAME = "name";
    public static final String SEND_UNIVERSITY = "university";
    public static final String SEND_DEPARTMENT = "department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 버튼 클릭 시 페이지 이동
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
        EditText name = (EditText) findViewById(R.id.editText_name);
        EditText university = (EditText) findViewById(R.id.editText_university);
        EditText department = (EditText) findViewById(R.id.editText_department);

        intent.putExtra(SEND_NAME, name.getText().toString());
        intent.putExtra(SEND_UNIVERSITY, university.getText().toString());
        intent.putExtra(SEND_DEPARTMENT, department.getText().toString());
    }
}