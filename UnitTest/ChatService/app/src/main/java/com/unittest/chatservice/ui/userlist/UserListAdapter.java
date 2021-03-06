package com.unittest.chatservice.ui.userlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.R;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder> {

    private final List<String> userList;

    public UserListAdapter() {
        userList = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.user_list, parent, false);
        return new UserListViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        final String text = userList.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setArrayData(String strData) {
        userList.add(strData);
    }
}
