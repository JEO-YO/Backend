package com.unittest.chatservice.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private final ArrayList<String> arrayList;

    public MainAdapter() {
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new MainViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        String text = arrayList.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayData(String strData) {
        arrayList.add(strData);
    }
}
