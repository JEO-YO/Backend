package com.unittest.chatservice.ui.main;

import static com.unittest.chatservice.user.repository.FirebaseUserRepository.TABLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.chatservice.R;
import com.unittest.chatservice.ui.signin.SignInActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        Adapter adapter = new Adapter();
        getSendData();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println("currentUser.getUid() = " + currentUser.getUid());

        reference.child(TABLE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("MSG", "Error getting data", task.getException());
                    return;
                }
                final List<String> userNames = getUserNames(task);
                for (int i = 0; i < userNames.size(); i++) {
                    adapter.setArrayData(userNames.get(i));
                }
                recyclerView.setAdapter(adapter);
            }
        });

    }

    private List<String> getUserNames(Task<DataSnapshot> task) {
        final List<String> userNames = new ArrayList<>();
        Iterable<DataSnapshot> children = task.getResult().getChildren();
        for (DataSnapshot data : children) {
            String name = data.child("email").getValue().toString();
            userNames.add(name);
        }
        return userNames;
    }


    private void getSendData() {
        Intent intent = getIntent();
        String currentUser = intent.getStringExtra("currentUser");
        System.out.println("currentUser = " + currentUser);
    }
}
