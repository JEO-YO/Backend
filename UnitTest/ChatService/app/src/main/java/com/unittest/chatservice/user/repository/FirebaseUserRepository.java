package com.unittest.chatservice.user.repository;

import static com.unittest.chatservice.user.model.User.*;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.chatservice.ui.userlist.UserListAdapter;
import com.unittest.chatservice.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirebaseUserRepository implements UserRepository {

    private static final String TAG = "JEOYO";
    private static final String CREATE_USER_FAIL_MESSAGE = "createUserWithEmail:failure";
    private static final String SIGN_IN_FAIL_MESSAGE = "signInWithEmail:failure";
    private static final int MINIMUM_SIZE = 0;
    private static final String GET_DATA_ERROR_MESSAGE = "Error getting data";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    @Override
    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        final String uid = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                        save(uid, email, password);
                        return;
                    }
                    Log.w(TAG, CREATE_USER_FAIL_MESSAGE, task.getException());
                });
    }

    @Override
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, SIGN_IN_FAIL_MESSAGE, task.getException());
                    }
                });
    }

    @Override
    public String findCurrentUserId() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    }

    @Override
    public void createUserList(RecyclerView recyclerView) {
        reference.child(USER_TABLE).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, GET_DATA_ERROR_MESSAGE, task.getException());
                return;
            }
            setUserListAdapter(recyclerView, getUsersEmail(task));
        });
    }

    private List<String> getUsersEmail(Task<DataSnapshot> task) {
        final List<String> usersEmail = new ArrayList<>();
        for (DataSnapshot data : Objects.requireNonNull(task.getResult()).getChildren()) {
            final String email = Objects.requireNonNull(data.child(EMAIL_TABLE).getValue()).toString();
            usersEmail.add(email);
        }
        return usersEmail;
    }

    private void setUserListAdapter(RecyclerView recyclerView, List<String> usersEmail) {
        final UserListAdapter adapter = new UserListAdapter();
        for (int i = MINIMUM_SIZE; i < usersEmail.size(); i++) {
            adapter.setArrayData(usersEmail.get(i));
        }
        recyclerView.setAdapter(adapter);
    }

    private void save(String id, String email, String password) {
        final User user = new User(id, email, password);
        final DatabaseReference basePath = reference.child(USER_TABLE).child(String.valueOf(user.getId()));
        basePath.setValue(user);
    }
}
