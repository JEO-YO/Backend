package com.unittest.chatservice.ui.userlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.chatroom.ChatRoomActivity;

public class UserListViewHolder extends RecyclerView.ViewHolder {

    public final TextView textView;
    public final Button button;
    public static final String SEND_DATA_KEY = "userEmail";

    UserListViewHolder(Context context, View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.userListTextView);
        button = itemView.findViewById(R.id.chatRoomButton);
        button.setOnClickListener(view -> {
            final String strText = textView.getText().toString();
            Toast.makeText(context, strText, Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(context, ChatRoomActivity.class);
            intent.putExtra(SEND_DATA_KEY, strText);
            context.startActivity(intent);
            ((Activity)context).finish();
        });
    }
}
