package com.unittest.chatservice.chat.service;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.R;
import com.unittest.chatservice.chat.dto.ChatData;
import com.unittest.chatservice.chat.repository.ChatRepository;
import com.unittest.chatservice.chat.repository.FirebaseChatRepository;
import com.unittest.chatservice.ui.chatroom.ChatRoomActivity;


public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository = new FirebaseChatRepository();

    @Override
    public void send(String message, String myId, String userId) {
        ChatData chatData = new ChatData(message, myId, userId);
        chatRepository.saveChat(chatData);
    }

    @Override
    public void read(String myId, String userId, Context mContext, RecyclerView messageView) {
        chatRepository.getChat(myId, userId, mContext, messageView);
    }
}
