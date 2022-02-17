package com.unittest.chatservice.ui.userlist;

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

public class UserListActivity extends AppCompatActivity {

    private static final String TAG = "JEOYO";
    private static final int MINIMUM_SIZE = 0;
    private static final String GET_DATA_ERROR_MESSAGE = "Error getting data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        makeRecyclerView();
    }

    private void makeRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
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
            final List<String> usersEmail = getUsersEmail(task);
            setUserListAdapter(recyclerView, usersEmail);
        });
    }

    private void setUserListAdapter(RecyclerView recyclerView, List<String> usersEmail) {
        final UserListAdapter adapter = new UserListAdapter();
        for (int i = MINIMUM_SIZE; i < usersEmail.size(); i++) {
            adapter.setArrayData(usersEmail.get(i));
        }
        recyclerView.setAdapter(adapter);
    }

    private List<String> getUsersEmail(Task<DataSnapshot> task) {
        final List<String> usersEmail = new ArrayList<>();
        for (DataSnapshot data : Objects.requireNonNull(task.getResult()).getChildren()) {
            final String email = Objects.requireNonNull(data.child(EMAIL_TABLE).getValue()).toString();
            usersEmail.add(email);
        }
        return usersEmail;
    }
}
