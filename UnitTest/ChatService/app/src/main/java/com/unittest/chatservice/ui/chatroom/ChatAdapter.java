package com.unittest.chatservice.ui.chatroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unittest.chatservice.R;
import com.unittest.chatservice.chat.dto.ChatData;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    private final Context mContext;
    private final List<ChatData> mChat;

    public ChatAdapter(Context mContext, List<ChatData> mChat) {
        this.mContext = mContext;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ChatViewHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatData chatData = mChat.get(position);
        holder.showMessage.setText(chatData.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(currentUser.getUid())) {
            return MSG_TYPE_RIGHT;
        }
        return MSG_TYPE_LEFT;
    }
}
