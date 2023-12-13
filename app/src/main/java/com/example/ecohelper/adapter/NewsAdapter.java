package com.example.ecohelper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecohelper.model.news;
import com.example.ecohelper.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Integer> NewsList;
    public NewsAdapter(List<Integer> NewsList){
        this.NewsList = NewsList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news , parent , false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.mImageView.setImageResource(NewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.cardView3);
        }
    }
}
