package com.unittest.chatservice.ui.main;

import static com.unittest.chatservice.user.model.User.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.chatservice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String ID = "id";
    private static final String TAG = "MSG";
    private static final int MINIMUM_SIZE = 0;
    private static final String GET_DATA_ERROR_MESSAGE = "Error getting data";

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
                Log.e(TAG, GET_DATA_ERROR_MESSAGE, task.getException());
                return;
            }
            final List<String> usersId = getUsersId(task);
            final MainAdapter adapter = new MainAdapter();
            for (int i = MINIMUM_SIZE; i < usersId.size(); i++) {
                adapter.setArrayData(usersId.get(i));
            }
            recyclerView.setAdapter(adapter);
        });
    }

    private List<String> getUsersId(Task<DataSnapshot> task) {
        final List<String> userNames = new ArrayList<>();
        for (DataSnapshot data : Objects.requireNonNull(task.getResult()).getChildren()) {
            final String email = Objects.requireNonNull(data.child(ID).getValue()).toString();
            userNames.add(email);
        }
        return userNames;
    }

}
