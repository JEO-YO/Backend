package com.unittest.recentsearch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private EditText editText_search;
    private Button search_btn;

//    private ArrayList<String> searches;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mAuth = FirebaseAuth.getInstance();
        search_btn = findViewById(R.id.search);
        editText_search = findViewById(R.id.editTextSearch);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHistory();
            }
        });


    }

    private void getHistory(){
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Searches").child(mAuth.getCurrentUser().getUid());

        ref1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String recent = String.valueOf(task.getResult().getValue());
                    if (recent == "null")
                        upload("");
                    else{
                        upload(recent);
                        String[] histories = recent.split(",");
                        Log.d("firebase", histories[0]);
                    }

                }
            }
        });
    }

    private void upload(String recent){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String title = editText_search.getText().toString();
        String data = recent + title + ",";
//        searches.add(title);
//        Log.d("Search Data", searches.get(0));

        reference.child("Searches").child(mAuth.getCurrentUser().getUid()).setValue(data);// upload to db

        Toast.makeText(SearchActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
    }


}
