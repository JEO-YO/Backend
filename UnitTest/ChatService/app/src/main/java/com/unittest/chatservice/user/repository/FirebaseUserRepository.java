package com.unittest.chatservice.user.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUserRepository implements UserRepository {

    private static final String TAG = "MSG";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference reference = database.getReference();

    @Override
    public void signUp(String email, String password) {
        createUser(email, password);
    }

    @Override
    public void signIn(String email, String password) {

    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            return;
                        }
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
    }
}
