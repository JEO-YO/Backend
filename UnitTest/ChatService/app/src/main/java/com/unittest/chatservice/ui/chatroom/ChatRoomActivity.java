package com.unittest.chatservice.ui.chatroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.unittest.chatservice.R;
import com.unittest.chatservice.chat.repository.ChatRepository;
import com.unittest.chatservice.chat.repository.FirebaseChatRepository;
import com.unittest.chatservice.chat.service.ChatService;
import com.unittest.chatservice.chat.service.ChatServiceImpl;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;
import com.unittest.chatservice.user.service.UserService;
import com.unittest.chatservice.user.service.UserServiceImpl;

public class ChatRoomActivity extends AppCompatActivity {

    private final ChatRepository chatRepository = new FirebaseChatRepository();
    private final ChatService chatService = new ChatServiceImpl(chatRepository);
    private final UserRepository userRepository = new FirebaseUserRepository();
    private final UserService userService = new UserServiceImpl(userRepository);

    private RecyclerView messageView;
    private static final String GET_DATA_NAME = "userEmail";
    private static final String NOT_ALLOWED_EMPTY_MESSAGE = "You can't send empty message";
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        makeRecyclerView();
        context = this;

        final String email = getIntent().getStringExtra(GET_DATA_NAME);
        chatService.start(email);
    }

    public void messageEventListener(String userId, DatabaseReference chatUsers) {
        chatUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatService.read(userService.getCurrentUserId(), userId, messageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void clickSendButton(String userId) {
        final Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener((view) -> {
            final EditText sendMessageEditText = findViewById(R.id.sendMessageEditText);
            final String message = sendMessageEditText.getText().toString();
            if (!message.isEmpty()) {
                chatService.send(message, userService.getCurrentUserId(), userId);
                sendMessageEditText.setText("");
                return;
            }
            Toast.makeText(ChatRoomActivity.this, NOT_ALLOWED_EMPTY_MESSAGE, Toast.LENGTH_SHORT).show();
            sendMessageEditText.setText("");
        });
    }

    private void makeRecyclerView() {
        messageView = findViewById(R.id.messageRecyclerView);
        messageView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        messageView.setLayoutManager(linearLayoutManager);
    }
}
