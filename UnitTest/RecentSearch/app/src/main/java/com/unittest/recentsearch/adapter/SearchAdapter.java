package com.unittest.recentsearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.unittest.recentsearch.ClickListener;
import com.unittest.recentsearch.R;

import java.lang.ref.WeakReference;

import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private String[] histories;
    private final ClickListener listener;

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_recent, parent, false) ;
        SearchAdapter.ViewHolder vh = new SearchAdapter.ViewHolder(view, listener) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        String text = histories[position];
        holder.textView_history.setText(text);

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return histories.length ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView_history ;
        private WeakReference<ClickListener> listenerRef;
        private final ImageButton button_delete;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView) ;

            listenerRef = new WeakReference<>(listener);


            // 뷰 객체에 대한 참조. (hold strong reference)
            button_delete = itemView.findViewById(R.id.button_delete);
            textView_history = itemView.findViewById(R.id.textView_history) ;

            button_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if (v.getId() == textView_history.getId()){
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }

            listenerRef.get().onDeleteClicked(getAdapterPosition());
        }

        void onBind(String string){
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SearchAdapter(String[] histories, ClickListener listener) {
        this.listener = listener;
        this.histories = histories;
    }


}

