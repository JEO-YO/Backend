package com.unittest.chatservice.ui.chatroom;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView showMessage;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        showMessage = itemView.findViewById(R.id.showMessage);
    }
}
