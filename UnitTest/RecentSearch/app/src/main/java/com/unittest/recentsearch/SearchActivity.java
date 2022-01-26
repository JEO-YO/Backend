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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.unittest.recentsearch.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity implements ClickListener {

    private EditText editText_search;
    private Button search_btn;
    private RecyclerView recyclerView_history;

    DatabaseReference ref;
    private ArrayList<String> searches = new ArrayList<String>();
    SearchAdapter searchAdapter;
//    ArrayList<String> exampleArray;

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

        ref = FirebaseDatabase.getInstance().getReference().child("Searches").child(mAuth.getCurrentUser().getUid());

        getHistory();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHistory();

                upload();
                editText_search.setText("");

            }
        });


    }

    @Override
    public void onDeleteClicked(View v, int position) {
        searches.remove(position);
        deleteData(searches);
    }

    private void getHistory() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Searches").child(mAuth.getCurrentUser().getUid());
        ArrayList<String> recent = new ArrayList<String>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
                };
                ArrayList<String> recent = snapshot.getValue(t);

                if (recent == null) {
                    System.out.println("No messages");
                    searches.clear();
                } else {
                    searches = recent;
                    System.out.println("The first message is: " + searches.get(0));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Do something for this

            }

        });
    }

    private void upload() {
        String title = editText_search.getText().toString();
        if (!title.isEmpty() && title != null) {
            searches.add(title);
            ref.setValue(searches);// upload to db
            Toast.makeText(SearchActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        searchAdapter = new SearchAdapter(searches, this);
        recyclerView_history.setAdapter(searchAdapter);
    }

    private void deleteData(ArrayList<String> recent) {

        ref.setValue(recent);// upload to db
        Toast.makeText(SearchActivity.this, String.valueOf(recent.size()), Toast.LENGTH_SHORT).show();
    }



}
