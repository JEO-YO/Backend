package com.unittest.firebasepost;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unittest.firebasepost.Model.Post;

import java.util.ArrayList;
import java.util.Map;

public class RetrieveActivity extends AppCompatActivity {
    private ImageView imageView_thumbnail;

    private TextView textView_key, textView_classify, textView_category, textView_period, textView_frequency, textView_memberCnt,
            textView_memberCdt, textView_title, textView_description;
    private Button button_retrieve;
    private ArrayList<Post> posts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        setViewId();

        button_retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(RetrieveActivity.this, "클릭", Toast.LENGTH_SHORT).show();

                retrieveData();
            }
        });
    }

    private void retrieveData() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Posts");
        ;

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                posts = new ArrayList<Post>();
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    posts.add(dsp.getValue(Post.class)); //add result into array list
                }
                Toast.makeText(RetrieveActivity.this, String.valueOf(posts.size()), Toast.LENGTH_SHORT).show();
                setTextView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    private void retrieveThumbnail(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

// Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("posts/"+textView_key.getText());

        pathReference.getDownloadUrl().
                addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide
                        .with(RetrieveActivity.this)
                        .load(uri) // the uri you got from Firebase
                        .into(imageView_thumbnail); //Your imageView variable
//                imageView_thumbnail.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void setTextView(){
        Post post = posts.get(0);

        textView_key.setText(post.getKey());
        textView_classify.setText(post.getClassify());
        textView_category.setText(post.getCategory());
        textView_period.setText(post.getPeriod());
        textView_frequency.setText(post.getFrequency());
        textView_memberCnt.setText(post.getMemberCnt());
        textView_memberCdt.setText(post.getMemberCondition());
        textView_title.setText(post.getTitle());
        textView_description.setText(post.getDescription());

        retrieveThumbnail();

    }

    private void setViewId() {
        imageView_thumbnail = findViewById(R.id.imageView_thumbnail);

        textView_key = findViewById(R.id.textView_key);
        textView_classify = findViewById(R.id.textView_classify);
        textView_category = findViewById(R.id.textView_category);
        textView_period = findViewById(R.id.textView_period);
        textView_frequency = findViewById(R.id.textView_frequency);
        textView_memberCnt = findViewById(R.id.textView_memberCnt);
        textView_memberCdt = findViewById(R.id.textView_memberCdt);
        textView_title = findViewById(R.id.textView_title);
        textView_description = findViewById(R.id.textView_description);

        button_retrieve = findViewById(R.id.retrieve_btn);
    }


}