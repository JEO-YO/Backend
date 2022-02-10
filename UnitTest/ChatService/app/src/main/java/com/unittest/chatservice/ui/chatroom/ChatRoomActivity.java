package com.unittest.chatservice.ui.chatroom;

import static com.unittest.chatservice.chat.dto.ChatData.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unittest.chatservice.R;
import com.unittest.chatservice.chat.service.ChatService;
import com.unittest.chatservice.chat.service.ChatServiceImpl;

public class
ChatRoomActivity extends AppCompatActivity {

    private final ChatService chatService = new ChatServiceImpl();
    private RecyclerView messageView;
    private static final String GET_DATA_NAME = "userId";
    private static final String NOT_ALLOWED_EMPTY_MESSAGE = "You can't send empty message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        makeRecyclerView();

        final String userId = getIntent().getStringExtra(GET_DATA_NAME);
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        clickSendButton(userId, currentUser);
        final DatabaseReference chatUsers = FirebaseDatabase.getInstance().getReference(CHAT_DATA_TABLE).child(userId);
        messageEventListener(userId, currentUser, chatUsers);
    }

    private void messageEventListener(String userId, FirebaseUser currentUser, DatabaseReference chatUsers) {
        chatUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatService.read(currentUser.getUid(), userId, ChatRoomActivity.this, messageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void clickSendButton(String userId, FirebaseUser currentUser) {
        final Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener((view) -> {
            final EditText sendMessageEditText = findViewById(R.id.sendMessageEditText);
            final String message = sendMessageEditText.getText().toString();
            if (!message.isEmpty()) {
                chatService.send(message, currentUser.getUid(), userId);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        messageView.setLayoutManager(linearLayoutManager);
    }
}
