package com.unittest.chatservice.chat.repository;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.chat.dto.ChatData;

public interface ChatRepository {

    void saveChat(ChatData chatData);

    void getChat(String myId, String userId, Context mContext, RecyclerView messageView);
}
