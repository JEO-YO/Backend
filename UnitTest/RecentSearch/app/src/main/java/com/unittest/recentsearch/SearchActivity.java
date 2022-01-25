package com.unittest.recentsearch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unittest.recentsearch.adapter.SearchAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {

    private EditText editText_search;
    private Button search_btn;
    private RecyclerView recyclerView_history;
    private String recent;
    //    private ArrayList<String> searches;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mAuth = FirebaseAuth.getInstance();
        search_btn = findViewById(R.id.search);
        editText_search = findViewById(R.id.editTextSearch);
        recyclerView_history = findViewById(R.id.recyclerView_history);

        recyclerView_history.setLayoutManager(new LinearLayoutManager(this));

        getHistory();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(recent);
                getHistory();
            }
        });


    }

    private void getHistory(){
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Searches").child(mAuth.getCurrentUser().getUid());

        ref1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    recent = String.valueOf(task.getResult().getValue());
                    if (!recent.isEmpty() && recent != "null" && recent != null){
                        String[] histories = recent.split(",");
//                        Log.d("firebase", histories[0]);

                        SearchAdapter searchAdapter = new SearchAdapter(histories, new ClickListener() {
                            @Override
                            public void onDeleteClicked(int position) {
                                for (int i = position; i < histories.length - 1; i++) {
                                    histories[i] = histories[i + 1];
                                }
                                recent = convertStringArr2String(histories);
                                deleteData(recent);
                                getHistory();
                            }
                        });
                        recyclerView_history.setAdapter(searchAdapter);
                    }

                }
            }
        });
    }

    private void deleteData(String recent){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Searches").child(mAuth.getCurrentUser().getUid()).setValue(recent);// upload to db
        Toast.makeText(SearchActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
    }

    private String convertStringArr2String(String[] stringArray){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < stringArray.length; i++) {
            sb.append(stringArray[i] + ",");
        }
        String str = sb.toString();

        return str;
    }

    private void upload(String recent){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String title = editText_search.getText().toString();
        if(!title.isEmpty()){
            String data = recent + title + ",";
            reference.child("Searches").child(mAuth.getCurrentUser().getUid()).setValue(data);// upload to db
            Toast.makeText(SearchActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
        }


    }


}
