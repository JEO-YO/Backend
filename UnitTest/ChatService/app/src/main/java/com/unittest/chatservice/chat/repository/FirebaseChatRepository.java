package com.unittest.chatservice.chat.repository;

import static com.unittest.chatservice.chat.dto.ChatData.*;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unittest.chatservice.chat.dto.ChatData;
import com.unittest.chatservice.ui.chatroom.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirebaseChatRepository implements ChatRepository {

    @Override
    public void saveChat(ChatData chatData) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(CHAT_DATA_TABLE).push().setValue(chatData);
    }

    @Override
    public void getChat(String myId, String userId, Context mContext, RecyclerView messageView) {
        final List<ChatData> mChat = new ArrayList<>();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(CHAT_DATA_TABLE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    final ChatData chat = data.getValue(ChatData.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                        mChat.add(chat);
                    }
                    final ChatAdapter chatAdapter = new ChatAdapter(mContext, mChat);
                    messageView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
