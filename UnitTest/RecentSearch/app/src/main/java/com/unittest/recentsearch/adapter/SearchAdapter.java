package com.unittest.recentsearch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.unittest.recentsearch.ClickListener;
import com.unittest.recentsearch.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private static ArrayList<String> histories;
    private static ClickListener listener;

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_recent, parent, false) ;
        SearchAdapter.ViewHolder vh = new SearchAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        if (histories.isEmpty()){
            notifyItemRemoved(0);
            Log.d("empty", String.valueOf(histories.size()));
        }
        String text = histories.get(position);
        holder.textView_history.setText(text);

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return histories.size() ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private final TextView textView_history ;

        private final ImageButton button_delete;

        public ViewHolder(View itemView) {
            super(itemView) ;
            itemView.setOnClickListener(this);
            // 뷰 객체에 대한 참조. (hold strong reference)
            button_delete = itemView.findViewById(R.id.button_delete);
            textView_history = itemView.findViewById(R.id.textView_history) ;
//            button_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    removeAt(getLayoutPosition());
//                }
//            });


            button_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            listener.onDeleteClicked(v, this.getLayoutPosition(), SearchAdapter.histories );

        }

        void onBind(String string){
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SearchAdapter(ArrayList<String> histories, ClickListener listener) {
        this.listener = listener;
        this.histories = histories;
    }

    public void removeAt(int position) {
        histories.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, histories.size());
    }


}

