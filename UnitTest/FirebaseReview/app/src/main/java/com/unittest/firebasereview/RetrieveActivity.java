package com.unittest.firebasereview;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RetrieveActivity extends AppCompatActivity {
    private ArrayList<Review> reviews;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        retrieveData();
    }

    private void setReviewEditText(){
        int question_count = Integer.valueOf(getResources().getString(R.string.review_question_count));
        Review review = reviews.get(reviews.size()-1);
        List<String> answers = review.getAnswers();

        for (int i=0; i < question_count; i++){
            int id = getResources().getIdentifier("q"+ String.valueOf(i+1) + "_editText", "id", getPackageName());
            EditText answer_editText = findViewById(id);

            answer_editText.setText(answers.get(i));

        }
    }


    private void retrieveData() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Reviews").child("unknown");
        ;

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                reviews = new ArrayList<Review>();
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    reviews.add(dsp.getValue(Review.class)); //add result into array list
                }
                Toast.makeText(RetrieveActivity.this, String.valueOf(reviews.size()), Toast.LENGTH_SHORT).show();
                setReviewEditText();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
