package com.unittest.chatservice.ui.userlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.unittest.chatservice.R;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;
import com.unittest.chatservice.user.service.UserService;
import com.unittest.chatservice.user.service.UserServiceImpl;

public class UserListActivity extends AppCompatActivity {

    private final UserRepository userRepository = new FirebaseUserRepository();
    private final UserService userService = new UserServiceImpl(userRepository);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        makeRecyclerView();
    }

    private void makeRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        userService.createUsers(recyclerView);
    }
}
