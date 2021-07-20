package com.worldinova.open.eglobal;

import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUrl;
    private EditText editTextTitle;
    private EditText editTextDesc;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        editTextUrl = findViewById(R.id.edittext_photo_url);
        editTextTitle = findViewById(R.id.edittext_title);
        editTextDesc = findViewById(R.id.edittext_desc);

        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.textview_view_news).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String photo_url, String news_title, String news_desc) {
        if (photo_url.isEmpty()) {
            editTextUrl.setError("Name required");
            editTextUrl.requestFocus();
            return true;
        }

        if (news_title.isEmpty()) {
            editTextTitle.setError("Brand required");
            editTextTitle.requestFocus();
            return true;
        }

        if (news_desc.isEmpty()) {
            editTextDesc.setError("Description required");
            editTextDesc.requestFocus();
            return true;
        }
        return false;
    }


    private void saveNews(){
        String photo_url = editTextUrl.getText().toString().trim();
        String news_title = editTextTitle.getText().toString().trim();
        String news_desc = editTextDesc.getText().toString().trim();

        if (!hasValidationErrors(photo_url, news_title, news_desc)) {

            CollectionReference dbnews = db.collection("news");

            News news = new News(photo_url, news_title, news_desc);

            dbnews.add(news)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "News Added", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    @Override
    public void onClick(View v) {

       switch(v.getId()){
           case R.id.button_save:
               saveNews();
               break;
           case R.id.textview_view_news:
               startActivity(new Intent(this, ViewNewsActivity.class));
               break;
       }

    }
}
