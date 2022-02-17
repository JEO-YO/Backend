package com.unittest.chatservice.ui.chatroomlist;

import static com.unittest.chatservice.chat.dto.ChatData.*;
import static com.unittest.chatservice.user.model.User.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.userlist.UserListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressLint("newApi")
public class ChatRoomListActivity extends AppCompatActivity {

    private static final String TAG = "JEOYO";
    private static final String GET_DATA_ERROR_MESSAGE = "Error getting data";
    private static final String REGEX_FOR_CHAT_VALUES = ",";
    private static final String REGEX_FOR_ID = "=";
    private static final int RECEIVER_INDEX = 0;
    private static final int SENDER_INDEX = 1;
    private static final int ID_INDEX = 1;
    private static final int MINIMUM_SIZE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);

        makeRecyclerView();
    }

    private void makeRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.chatRoomRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        setChatRoomList(recyclerView);
    }

    private void setChatRoomList(RecyclerView recyclerView) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, GET_DATA_ERROR_MESSAGE, task.getException());
                return;
            }
            final List<String> usersEmail = showChatRoomLatestChat(task);
            setChatRoomListAdapter(recyclerView, usersEmail);
        });
    }

    private void setChatRoomListAdapter(RecyclerView recyclerView, List<String> saveUsersEmail) {
        final ChatRoomListAdapter adapter = new ChatRoomListAdapter();
        for (int i = MINIMUM_SIZE; i < saveUsersEmail.size(); i++) {
            adapter.setArrayData(saveUsersEmail.get(i));
        }
        recyclerView.setAdapter(adapter);
    }

    private List<String> showChatRoomLatestChat(Task<DataSnapshot> task) {
        final List<String> chatsValue = reverseChatsValue(task);
        final List<String> saveUsersEmail = new ArrayList<>();
        for (String value : chatsValue) {
            final List<String> splitForChatValues = Arrays.asList(value.split(REGEX_FOR_CHAT_VALUES));
            saveUsersId(saveUsersEmail, splitForChatValues, task);
        }
        for (String id : saveUsersEmail) {
            System.out.println("id = " + id);
        }
        return saveUsersEmail;
    }

    private void saveUsersId(List<String> saveUserEmail, List<String> splitForChatValues, Task<DataSnapshot> task) {
        final String currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final String receiverId = makeReceiver(splitForChatValues);
        final String receiver = calculateEmailById(receiverId, task);
        final String senderId = makeSender(splitForChatValues);
        final String sender = calculateEmailById(senderId, task);

        if (receiverId.equals(currentUserId) && !saveUserEmail.contains(sender)) {
            saveUserEmail.add(sender);
        }
        if (senderId.equals(currentUserId) && !saveUserEmail.contains(receiver)) {
            saveUserEmail.add(receiver);
        }
    }

    private String calculateEmailById(String id, Task<DataSnapshot> task) {
        return Objects.requireNonNull(Objects.requireNonNull(task.getResult())
                .child(USER_TABLE)
                .child(id)
                .child(EMAIL_TABLE)
                .getValue()).toString();
    }

    private List<String> reverseChatsValue(Task<DataSnapshot> task) {
        final List<String> chatsValue = new ArrayList<>();
        Objects.requireNonNull(task.getResult()).child(CHAT_DATA_TABLE).getChildren()
                .forEach(dataSnapshot -> chatsValue.add(Objects.requireNonNull(dataSnapshot.getValue()).toString()));
        Collections.reverse(chatsValue);
        return chatsValue;
    }

    private String makeReceiver(List<String> splitForChatValues) {
        return splitForChatValues.get(RECEIVER_INDEX).split(REGEX_FOR_ID)[ID_INDEX];
    }

    private String makeSender(List<String> splitForChatValues) {
        return splitForChatValues.get(SENDER_INDEX).split(REGEX_FOR_ID)[ID_INDEX];
    }
}
