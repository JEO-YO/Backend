package com.unittest.chatservice.ui.chatroomlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.R;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListViewHolder> {

    private final List<String> chatRoomList;

    public ChatRoomListAdapter() {
        chatRoomList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChatRoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.user_list, parent, false);
        return new ChatRoomListViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomListViewHolder holder, int position) {
        final String chatRoom = chatRoomList.get(position);
        holder.textView.setText(chatRoom);
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    public void setArrayData(String strData) {
        chatRoomList.add(strData);
    }
}
