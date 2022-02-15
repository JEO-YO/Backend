package com.unittest.chatservice.chat.repository;

import static com.unittest.chatservice.chat.dto.ChatData.CHAT_DATA_TABLE;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.chatservice.chat.dto.ChatData;

public class FirebaseChatRepository implements ChatRepository {

    @Override
    public void saveChat(ChatData chatData) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(CHAT_DATA_TABLE).push().setValue(chatData);
    }

    @Override
    public DatabaseReference getChatData() {
        return FirebaseDatabase.getInstance().getReference(CHAT_DATA_TABLE);
    }
}
