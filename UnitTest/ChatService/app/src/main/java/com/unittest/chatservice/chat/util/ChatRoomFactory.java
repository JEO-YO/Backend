package com.unittest.chatservice.chat.util;

import static com.unittest.chatservice.chat.dto.ChatData.CHAT_DATA_TABLE;
import static com.unittest.chatservice.user.model.User.EMAIL_TABLE;
import static com.unittest.chatservice.user.model.User.USER_TABLE;

import android.annotation.SuppressLint;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressLint("newApi")
public class ChatRoomFactory {

    private static final String REGEX_FOR_CHAT_VALUES = ",";
    private static final String REGEX_FOR_ID = "=";
    private static final int RECEIVER_INDEX = 0;
    private static final int SENDER_INDEX = 1;
    private static final int ID_INDEX = 1;

    public ChatRoomFactory() {
    }

    public List<String> showChatRoomLatest(Task<DataSnapshot> task) {
        final List<String> chatsValue = reverseChatsValue(task);
        final List<String> saveUsersEmail = new ArrayList<>();
        for (String value : chatsValue) {
            final List<String> splitForChatValues = Arrays.asList(value.split(REGEX_FOR_CHAT_VALUES));
            saveUsersId(saveUsersEmail, splitForChatValues, task);
        }
        return saveUsersEmail;
    }

    private List<String> reverseChatsValue(Task<DataSnapshot> task) {
        final List<String> chatsValue = new ArrayList<>();
        Objects.requireNonNull(task.getResult()).child(CHAT_DATA_TABLE).getChildren()
                .forEach(dataSnapshot -> chatsValue.add(Objects.requireNonNull(dataSnapshot.getValue()).toString()));
        Collections.reverse(chatsValue);
        return chatsValue;
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

    private String makeReceiver(List<String> splitForChatValues) {
        return splitForChatValues.get(RECEIVER_INDEX).split(REGEX_FOR_ID)[ID_INDEX];
    }

    private String makeSender(List<String> splitForChatValues) {
        return splitForChatValues.get(SENDER_INDEX).split(REGEX_FOR_ID)[ID_INDEX];
    }
}
