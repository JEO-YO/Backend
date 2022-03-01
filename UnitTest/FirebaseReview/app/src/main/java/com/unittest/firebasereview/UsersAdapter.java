package com.unittest.firebasereview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private static ArrayList<User> users;

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_user, parent, false) ;
        UsersAdapter.ViewHolder vh = new UsersAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
        if (users.isEmpty()){
            notifyItemRemoved(0);
            Log.d("empty", String.valueOf(users.size()));
        }
        String text = users.get(position).getName();
        holder.textView_user.setText(text);

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return users.size() ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private final TextView textView_user ;

        public ViewHolder(View itemView) {
            super(itemView) ;
            itemView.setOnClickListener(this);
            // 뷰 객체에 대한 참조. (hold strong reference)
            textView_user = itemView.findViewById(R.id.textView_user) ;
//            button_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    removeAt(getLayoutPosition());
//                }
//            });

        }


        void onBind(String string){
        }

        @Override
        public void onClick(View view) {

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public UsersAdapter(ArrayList<User> users) {
        this.users = users;
    }

}