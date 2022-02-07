package com.unittest.chatservice.ui.main;

import static com.unittest.chatservice.user.repository.FirebaseUserRepository.TABLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private static final String TAG = "MSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeRecyclerView();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println("currentUser = " + currentUser.getUid());
    }

    private void makeRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        setUserEmails(recyclerView);
    }


    private void setUserEmails(RecyclerView recyclerView) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(TABLE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Error getting data", task.getException());
                    return;
                }
                final List<String> userEmails = getUserEmails(task);
                final Adapter adapter = new Adapter();
                for (int i = 0; i < userEmails.size(); i++) {
                    adapter.setArrayData(userEmails.get(i));
                }
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private List<String> getUserEmails(Task<DataSnapshot> task) {
        Iterable<DataSnapshot> children = task.getResult().getChildren();
        final List<String> userNames = new ArrayList<>();
        for (DataSnapshot data : children) {
            final String email = data.child(EMAIL).getValue().toString();
            userNames.add(email);
        }
        return userNames;
    }
}
