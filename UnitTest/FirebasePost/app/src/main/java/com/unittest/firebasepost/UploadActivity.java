package com.unittest.firebasepost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unittest.firebasepost.Model.Post;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class UploadActivity extends AppCompatActivity {
    private ImageView imageView_thumbnail;

    private EditText editText_classify, editText_category, editText_period, editText_frequency, editText_memberCnt, editText_memberCdt, editText_title, editText_description;
    private Button button_pick, button_upload;
    private String classify, category, period, frequency, memberCnt, memberCdt, title, description;

    private final static String TAG = "UploadActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        setId();
        setonClick();
    }

    private void setData() {
        classify = editText_classify.getText().toString();
        category = editText_category.getText().toString();
        period = editText_period.getText().toString();
        frequency = editText_frequency.getText().toString();
        memberCnt = editText_memberCnt.getText().toString();
        memberCdt = editText_memberCdt.getText().toString();
        title = editText_title.getText().toString();
        description = editText_description.getText().toString();

        upload();
    }

    private void upload() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String writer = mAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String key = reference.child("Posts").push().getKey();
        Post post = new Post(key, writer, classify, category, period, frequency, memberCnt, memberCdt, title, description, "Posts/" + key);

        reference.child("Posts").child(key).setValue(post);// upload to db

        uploadThumbnail(key);
        Toast.makeText(UploadActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
    }

//    private void checkDuplicatedKey(String key, Post post){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//        reference.child("Posts").orderByKey().equalTo(key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//                    //Key exists
//                    Toast.makeText(UploadActivity.this, "데이터 저장 오류 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG,"Key is duplicated: "+key);
//                }else{
//
//                    reference.child("Posts").child(key).setValue(post);// upload to db
//
//                    uploadThumbnail(key);
//                    Toast.makeText(UploadActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UploadActivity.this, "취소됨", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private String createKey() { //이메일 인증코드 생성
//        String[] str = {"A", "B", "C", "D", "E","F","G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S"
//                , "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
//                "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "_"};
//        String newCode = new String();
//
//        for (int x = 0; x < 20; x++) {
//            int random = (int) (Math.random() * str.length);
//            newCode += str[random];
//        }
//
//        return newCode;
//    }

    private void uploadThumbnail(String key) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference postImageRef = storageRef.child("posts/" + key);

        // Get the data from an ImageView as bytes
        imageView_thumbnail.setDrawingCacheEnabled(true);
        imageView_thumbnail.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView_thumbnail.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = postImageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    private void setonClick() {
        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
            }
        });
    }

    private void setId() {
        imageView_thumbnail = findViewById(R.id.imageView_thumbnail);

        editText_classify = findViewById(R.id.editTextClassify);
        editText_category = findViewById(R.id.editTextCategory);
        editText_period = findViewById(R.id.editTextPeriod);
        editText_frequency = findViewById(R.id.editTextFrequency);
        editText_memberCnt = findViewById(R.id.editTextMemberCnt);
        editText_memberCdt = findViewById(R.id.editTextMemberCdt);
        editText_title = findViewById(R.id.editTextTitle);
        editText_description = findViewById(R.id.editTextDescription);

        button_pick = findViewById(R.id.pick_btn);
        button_upload = findViewById(R.id.upload_btn);

    }
}
