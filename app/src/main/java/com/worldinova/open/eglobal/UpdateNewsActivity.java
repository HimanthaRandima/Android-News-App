package com.worldinova.open.eglobal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPhotoUrl;
    private EditText editTextTitle;
    private EditText editTextDesc;

    private FirebaseFirestore db;

    private News News;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);

        News = (News) getIntent().getSerializableExtra("News");
        db = FirebaseFirestore.getInstance();

        editTextPhotoUrl = findViewById(R.id.edittext_photo_url);
        editTextTitle = findViewById(R.id.edittext_title);
        editTextDesc = findViewById(R.id.edittext_desc);

        editTextPhotoUrl.setText(News.getPhoto_url());
        editTextTitle.setText(News.getTitle());
        editTextDesc.setText(News.getDescription());


        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String photo_url, String title, String desc) {
        if (photo_url.isEmpty()) {
            editTextPhotoUrl.setError("Url required");
            editTextPhotoUrl.requestFocus();
            return true;
        }

        if (title.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            editTextDesc.setError("Description required");
            editTextDesc.requestFocus();
            return true;
        }
        return false;
    }


    private void updateNews() {
        String photo_url = editTextPhotoUrl.getText().toString().trim();
        String title = editTextTitle.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();

        if (!hasValidationErrors(photo_url, title, desc)) {

            News p = new News(photo_url, title, desc);


            db.collection("news").document(News.getId())
                    .update(
                            "title", p.getTitle(),
                            "description", p.getDescription(),
                            "photo_url", p.getPhoto_url()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateNewsActivity.this, "News Updated", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void deleteProduct() {
        db.collection("news").document(News.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateNewsActivity.this, "News deleted", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(UpdateNewsActivity.this, ViewNewsActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                updateNews();
                break;
            case R.id.button_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure about this?");
                builder.setMessage("Deletion is permanent...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

                break;
        }
    }
}
