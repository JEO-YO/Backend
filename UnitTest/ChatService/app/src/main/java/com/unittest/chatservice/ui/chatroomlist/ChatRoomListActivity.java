package com.unittest.chatservice.ui.chatroomlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.unittest.chatservice.R;
import com.unittest.chatservice.chat.repository.ChatRepository;
import com.unittest.chatservice.chat.repository.FirebaseChatRepository;
import com.unittest.chatservice.chat.service.ChatService;
import com.unittest.chatservice.chat.service.ChatServiceImpl;
import com.unittest.chatservice.ui.userlist.UserListActivity;

public class ChatRoomListActivity extends AppCompatActivity {

    private final ChatRepository chatRepository = new FirebaseChatRepository();
    private final ChatService chatService = new ChatServiceImpl(chatRepository);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);

        makeRecyclerView();
        moveUserList();
        refresh();
    }

    private void moveUserList() {
        final Button buttonToUserList = findViewById(R.id.userListButton);
        buttonToUserList.setOnClickListener(view -> {
            startActivity(new Intent(this, UserListActivity.class));
            finish();
        });
    }

    private void refresh() {
        final Button buttonRefresh = findViewById(R.id.refreshButton);
        buttonRefresh.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatRoomListActivity.class));
            finish();
        });
    }

    private void makeRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.chatRoomRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        chatService.create(recyclerView);
    }
}
