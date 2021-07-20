package com.worldinova.open.eglobal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<News> NewsList;

    public NewsAdapter(Context mCtx, List<News> NewsList) {
        this.mCtx = mCtx;
        this.NewsList = NewsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_news, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        News News = NewsList.get(position);

        holder.textViewTitle.setText(News.getTitle());
        holder.textViewDesc.setText(News.getDescription());

        Glide.with(holder.imageViewPhotoUrl)
                .load(News.getPhoto_url())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageViewPhotoUrl);
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewDesc;
        ImageView imageViewPhotoUrl;

        public ProductViewHolder(View itemView) {
            super(itemView);

            imageViewPhotoUrl = itemView.findViewById(R.id.imageview_photo_url);
            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewDesc = itemView.findViewById(R.id.textview_desc);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            News News = NewsList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, UpdateNewsActivity.class);
            intent.putExtra("News", News);
            mCtx.startActivity(intent);
        }
    }
}
