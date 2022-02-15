package com.unittest.firebasereview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button upload_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mAuth = FirebaseAuth.getInstance();
        upload_btn = findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    private List<String> getReviewFromEditText(){
        int question_count = Integer.valueOf(getResources().getString(R.string.review_question_count));
        List<String> reviews = new ArrayList<>();
        for (int i=1; i <= question_count; i++){
            int id = getResources().getIdentifier("q"+ String.valueOf(i) + "_editText", "id", getPackageName());
            EditText answer_editText = findViewById(id);

            reviews.add(answer_editText.getText().toString());
        }

        return reviews;
    }

    private Review getReview(){

        List<String> reviews = getReviewFromEditText();
        Review review = new Review(reviews);

        return review;
    }

    private void upload(){
        String writer = mAuth.getCurrentUser().getUid();
        String target = "unknown";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Reviews").child(target).child(writer);
        reference.setValue(getReview());

    }
}
