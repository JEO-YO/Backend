package com.unittest.chatservice.ui.chatroomlist;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.chatroom.ChatRoomActivity;
import com.unittest.chatservice.ui.userlist.UserListActivity;

public class ChatRoomListViewHolder extends RecyclerView.ViewHolder {

    public final Button buttonToChatRoom;
    public final Button buttonToUserList;
    public final TextView textView;
    public static final String SEND_DATA_KEY = "userEmail";

    public ChatRoomListViewHolder(@NonNull Context context, View itemView) {
        super(itemView);
        buttonToChatRoom = itemView.findViewById(R.id.chatRoomListButton);
        buttonToUserList = itemView.findViewById(R.id.userListButton);
        textView = itemView.findViewById(R.id.chatRoomListTextView);
        buttonToChatRoom.setOnClickListener(view -> {
            final Intent intent = new Intent(context, ChatRoomActivity.class);
            intent.putExtra(SEND_DATA_KEY, textView.getText().toString());
            context.startActivity(intent);
        });
        buttonToUserList.setOnClickListener(view -> {
            context.startActivity(new Intent(context, UserListActivity.class));
        });
    }
}
