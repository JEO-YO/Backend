package com.unittest.chatservice.user.repository;

import static com.unittest.chatservice.user.model.User.*;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.chatservice.user.model.User;

import java.util.Objects;

public class FirebaseUserRepository implements UserRepository {

    private static final String TAG = "MSG";
    private static final String CREATE_USER_FAIL_MESSAGE = "createUserWithEmail:failure";
    private static final String SIGN_IN_FAIL_MESSAGE = "signInWithEmail:failure";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

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

    private void save(String id, String email, String password) {
        final User user = new User(id, email, password);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference basePath = reference.child(USER_TABLE).child(String.valueOf(user.getId()));
        basePath.setValue(user);
    }
}
