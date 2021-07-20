package com.worldinova.open.eglobal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewNewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> NewsList;
    private ProgressBar progressBar;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NewsList = new ArrayList<>();
        adapter = new NewsAdapter(this, NewsList);

        recyclerView.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();


        db.collection("news").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                News p = d.toObject(News.class);
                                p.setId(d.getId());
                                NewsList.add(p);

                            }

                            adapter.notifyDataSetChanged();

                        }


                    }
                });

    }
}
