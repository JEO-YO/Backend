package com.unittest.firebasereview;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView_users;
    private UsersAdapter usersAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView_users = findViewById(R.id.recyclerView_users);
        recyclerView_users.setLayoutManager(new LinearLayoutManager(this));
        retrieveData();
    }

    private void retrieveData() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users");


        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<User> users = new ArrayList<User>();
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    users.add(dsp.getValue(User.class)); //add result into array list
                }
                Toast.makeText(UsersActivity.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                usersAdapter = new UsersAdapter(users);
                recyclerView_users.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
