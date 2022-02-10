package com.unittest.chatservice.chat.service;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unittest.chatservice.chat.dto.ChatData;
import com.unittest.chatservice.chat.repository.ChatRepository;
import com.unittest.chatservice.ui.chatroom.ChatAdapter;
import com.unittest.chatservice.ui.chatroom.ChatRoomActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void send(String message, String myId, String userId) {
        chatRepository.saveChat(new ChatData(message, myId, userId));
    }

    @Override
    public void read(String myId, String userId, RecyclerView messageView) {
        chatRepository.getChatData().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    messageView.setAdapter(new ChatAdapter(ChatRoomActivity.context, getMyChatData(data, myId, userId)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private List<ChatData> getMyChatData(DataSnapshot data, String myId, String userId) {
        final List<ChatData> mChat = new ArrayList<>();
        final ChatData chat = data.getValue(ChatData.class);
        assert chat != null;
        if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
            mChat.add(chat);
        }
        return mChat;
    }
}
