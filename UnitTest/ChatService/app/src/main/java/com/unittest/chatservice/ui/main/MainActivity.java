package com.unittest.chatservice.ui.main;

import static com.unittest.chatservice.user.model.User.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.chatservice.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ID = "id";
    private static final String TAG = "MSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeRecyclerView();
    }

    private void makeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        setUserIds(recyclerView);
    }

    private void setUserIds(RecyclerView recyclerView) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(USER_TABLE).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                return;
            }
            final List<String> usersId = getUsersId(task);
            final MainAdapter adapter = new MainAdapter();
            for (int i = 0; i < usersId.size(); i++) {
                adapter.setArrayData(usersId.get(i));
            }
            recyclerView.setAdapter(adapter);
        });
    }

    private List<String> getUsersId(Task<DataSnapshot> task) {
        Iterable<DataSnapshot> children = task.getResult().getChildren();
        final List<String> userNames = new ArrayList<>();
        for (DataSnapshot data : children) {
            final String email = data.child(ID).getValue().toString();
            userNames.add(email);
        }
        return userNames;
    }

}
