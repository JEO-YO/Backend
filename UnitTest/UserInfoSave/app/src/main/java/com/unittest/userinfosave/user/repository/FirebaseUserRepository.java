package com.unittest.userinfosave.user.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.userinfosave.user.model.User;

public class FirebaseUserRepository implements UserRepository {

    private static final String TABLE = "Users";
    private static final String ACTIVITY_AREA = "activityArea";
    private static final String NAME = "name";
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference reference = database.getReference();
    private final java.lang.String key = reference.child(TABLE).push().getKey();

    @Override
    public void save(User user) {
        DatabaseReference basePath = reference.child(TABLE).child(key);
        basePath.child("id").setValue(user.getId());
        basePath.child("name").setValue(user.getName());
        basePath.child("university").setValue(user.getUniversity());
        basePath.child("department").setValue(user.getDepartment());
        basePath.child("activityArea").setValue(user.getActivityArea());
        basePath.child("interesting").setValue(user.getInteresting());
    }

    @Override
    public void findNamesByActivityArea(String activityArea) {
        DatabaseReference basePath = reference.child(TABLE);
        basePath.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("MSG", "Error getting data", task.getException());
                } else {
                    Iterable<DataSnapshot> children = task.getResult().getChildren();
                    for (DataSnapshot data : children) {
                        if (data.child(ACTIVITY_AREA).getValue().toString().equals(activityArea)) {
                            Log.d("MSG", data.child(NAME).getValue().toString());
                        }
                    }
                }
            }
        });
    }
}
