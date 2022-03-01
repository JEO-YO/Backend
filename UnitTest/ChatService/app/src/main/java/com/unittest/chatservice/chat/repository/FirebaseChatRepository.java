package com.unittest.chatservice.chat.repository;

import static com.unittest.chatservice.chat.dto.ChatData.CHAT_DATA_TABLE;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unittest.chatservice.chat.dto.ChatData;
import com.unittest.chatservice.ui.chatroom.ChatAdapter;
import com.unittest.chatservice.ui.chatroom.ChatRoomActivity;

import java.util.ArrayList;
import java.util.List;

public class FirebaseChatRepository implements ChatRepository {

    @Override
    public void saveChat(ChatData chatData) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(CHAT_DATA_TABLE).push().setValue(chatData);
    }

    @Override
    public void readChat(String myId, String userId, RecyclerView messageView) {
        getChatData().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setMessageView(snapshot, messageView, myId, userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private DatabaseReference getChatData() {
        return FirebaseDatabase.getInstance().getReference(CHAT_DATA_TABLE);
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
