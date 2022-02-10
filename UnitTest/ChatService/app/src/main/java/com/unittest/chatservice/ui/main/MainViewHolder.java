package com.unittest.chatservice.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.chatroom.ChatRoomActivity;

class MainViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public Button button;

    MainViewHolder(Context context, View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.itemListTextView);
        button = itemView.findViewById(R.id.itemListButton);
        button.setOnClickListener(view -> {
            String strText = textView.getText().toString();
            Toast.makeText(context, strText, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ChatRoomActivity.class);
            intent.putExtra("userId", strText);
            context.startActivity(intent);
        });
    }
}
