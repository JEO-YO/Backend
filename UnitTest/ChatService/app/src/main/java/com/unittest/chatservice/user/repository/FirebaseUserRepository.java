package com.unittest.chatservice.user.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.chatservice.user.model.User;

public class FirebaseUserRepository implements UserRepository {

    private static final String TAG = "MSG";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static final String TABLE = "ChatUsers";

    @Override
    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            String uid = task.getResult().getUser().getUid();
                            save(uid, email, password);
                            return;
                        }
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
    }

    @Override
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:Success");
                            return;
                        }
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                });
    }

    private void save(String id, String email, String password) {
        User user = new User(id, email, password);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference basePath = reference.child(TABLE).child(String.valueOf(user.getId()));
        basePath.setValue(user);
    }
}
