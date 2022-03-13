package com.unittest.chatservice.ui.chatroom;

import static com.unittest.chatservice.chat.dto.ChatData.CHAT_DATA_TABLE;
import static com.unittest.chatservice.user.model.User.EMAIL_TABLE;
import static com.unittest.chatservice.user.model.User.USER_TABLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unittest.chatservice.R;
import com.unittest.chatservice.chat.repository.ChatRepository;
import com.unittest.chatservice.chat.repository.FirebaseChatRepository;
import com.unittest.chatservice.chat.service.ChatService;
import com.unittest.chatservice.chat.service.ChatServiceImpl;
import com.unittest.chatservice.ui.chatroomlist.ChatRoomListActivity;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;
import com.unittest.chatservice.user.service.UserService;
import com.unittest.chatservice.user.service.UserServiceImpl;

import java.util.Objects;

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
        startChat(email);
    }

    // TODO::refactor 할 부분 : 순환참조 이슈 제거하며 service, repo에 옮기는 것 생각해보기
    public void startChat(String email) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(USER_TABLE);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chat(dataSnapshot, email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // TODO::refactor 할 부분 : 순환참조 이슈 제거하며 service, repo에 옮기는 것 생각해보기
    private void chat(DataSnapshot dataSnapshot, String email) {
        if (Objects.requireNonNull(dataSnapshot.child(EMAIL_TABLE).getValue()).toString().equals(email)) {
            final String userId = dataSnapshot.getKey();
            assert userId != null;
            clickSendButton(userId);
            final DatabaseReference chatUsers = FirebaseDatabase.getInstance().getReference(CHAT_DATA_TABLE).child(userId);
            messageEventListener(userId, chatUsers);
        }
    }

    private void messageEventListener(String userId, DatabaseReference chatUsers) {
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

    private void clickSendButton(String userId) {
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

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
        final Intent intent = new Intent(this, ChatRoomListActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
