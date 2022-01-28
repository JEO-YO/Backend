package com.unittest.recentsearch;

import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

import static java.lang.System.out;

public class SearchActivity extends AppCompatActivity implements ClickListener {

    private EditText editText_search;
    private Button search_btn;
    private RecyclerView recyclerView_history;

    DatabaseReference ref;
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
        ArrayList<String> searches = new ArrayList<String>();

        recyclerView_history = findViewById(R.id.recyclerView_history);
        recyclerView_history.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference().child("Searches").child(mAuth.getCurrentUser().getUid());

//        getHistory();
        setAdapter(readList());
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getHistory();
//
//                upload();
                ArrayList<String> searches = readList();
                searches.add(editText_search.getText().toString());
                saveList(searches);
                editText_search.setText("");
                setAdapter(searches);

            }
        });


    }

    private void saveList(ArrayList<String> list) {
        try {
            File file = this.getFilesDir();
            File filename = new File(file, "Search_history");

            if(filename.exists()) {
                filename.delete(); // Clear available files
            }
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(list);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    private ArrayList<String> readList()
    {
        ArrayList<String> histories = new ArrayList<>();
        try {
            File file = this.getFilesDir();
            File filename = new File(file, "Search_history");
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            histories = (ArrayList<String>) in.readObject();
            in.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return histories;
    }

    @Override
    public void onDeleteClicked(View v, int position, ArrayList<String> histories) {
        histories.remove(position);
        deleteData(histories);
    }

//    private void getHistory() {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Searches").child(mAuth.getCurrentUser().getUid());
//        ArrayList<String> recent = new ArrayList<String>();
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
//                };
//                ArrayList<String> recent = snapshot.getValue(t);
//
//                if (recent == null) {
//                    out.println("No messages");
//                    searches.clear();
//                } else {
//                    searches = recent;
//                    out.println("The first message is: " + searches.get(0));
//                }
//                setAdapter();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Do something for this
//
//            }
//
//        });
//    }

//    private void upload() {
//        String title = editText_search.getText().toString();
//        if (!title.isEmpty() && title != null) {
//            searches.add(title);
//            ref.setValue(searches);// upload to db
//            Toast.makeText(SearchActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void setAdapter(ArrayList<String> histories) {
        searchAdapter = new SearchAdapter(histories, this);
        recyclerView_history.setAdapter(searchAdapter);
    }

    private void deleteData(ArrayList<String> histories) {
//        ref.setValue(recent);// upload to db
        saveList(histories);
        setAdapter(readList());
        Toast.makeText(SearchActivity.this, String.valueOf(histories.size()), Toast.LENGTH_SHORT).show();
    }



}
