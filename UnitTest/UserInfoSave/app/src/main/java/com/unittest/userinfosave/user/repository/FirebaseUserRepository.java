package com.unittest.userinfosave.user.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unittest.userinfosave.user.model.User;

public class FirebaseUserRepository implements UserRepository {

    private static final String TABLE = "Users";
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference reference = database.getReference();
    String key = reference.child(TABLE).push().getKey();

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
}
