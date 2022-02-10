package com.unittest.chatservice.chat.service;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface ChatService {

    void send(String message, String currentUserId, String userId);

    void read(String myId, String userId, Context mContext, RecyclerView messageView);
}
