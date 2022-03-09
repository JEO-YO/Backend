package com.unittest.chatservice.chat.repository;

import static com.unittest.chatservice.chat.dto.ChatData.CHAT_DATA_TABLE;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unittest.chatservice.chat.dto.ChatData;
import com.unittest.chatservice.chat.util.ChatRoomFactory;
import com.unittest.chatservice.ui.chatroom.ChatAdapter;
import com.unittest.chatservice.ui.chatroom.ChatRoomActivity;
import com.unittest.chatservice.ui.userlist.UserListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirebaseChatRepository implements ChatRepository {

    private final ChatRoomFactory chatRoomFactory = new ChatRoomFactory();

    private static final String TAG = "JEOYO";
    private static final String GET_DATA_ERROR_MESSAGE = "Error getting data";
    private static final int MINIMUM_SIZE = 0;

    @Override
    public void saveChat(ChatData chatData) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(CHAT_DATA_TABLE).push().setValue(chatData);
    }

    @Override
    public void readChat(String myId, String userId, RecyclerView messageView) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(CHAT_DATA_TABLE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setMessageView(snapshot, messageView, myId, userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void createChat(RecyclerView recyclerView) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, GET_DATA_ERROR_MESSAGE, task.getException());
                return;
            }
            final List<String> usersEmail = chatRoomFactory.showChatRoomLatest(task);
            setChatRoomListAdapter(recyclerView, usersEmail);
        });
    }

    private void setChatRoomListAdapter(RecyclerView recyclerView, List<String> saveUsersEmail) {
        final UserListAdapter adapter = new UserListAdapter();
        for (int i = MINIMUM_SIZE; i < saveUsersEmail.size(); i++) {
            adapter.setArrayData(saveUsersEmail.get(i));
        }
        recyclerView.setAdapter(adapter);
    }

    private void setMessageView(@NonNull DataSnapshot snapshot, RecyclerView messageView, String myId, String userId) {
        final List<ChatData> mChat = new ArrayList<>();
        for (DataSnapshot data : snapshot.getChildren()) {
            messageView.setAdapter(new ChatAdapter(ChatRoomActivity.context, getMyChatData(mChat, data, myId, userId)));
        }
    }

    private List<ChatData> getMyChatData(List<ChatData> mChat, DataSnapshot data, String myId, String userId) {
        final ChatData chat = data.getValue(ChatData.class);
        assert chat != null;
        if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
            mChat.add(chat);
        }
        return mChat;
    }
}
