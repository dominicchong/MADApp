package com.example.madapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.madapp.model.programmes;
import com.example.madapp.R;

import java.util.List;

public class ProgrammesAdapter extends RecyclerView.Adapter<ProgrammesAdapter.ProgrammesViewHolder> {

    private List<programmes> ProgrammesList;
    public ProgrammesAdapter(List<programmes> NewsList){
        this.ProgrammesList = ProgrammesList;
    }
    @NonNull
    @Override
    public ProgrammesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_programmes , parent , false);
        return new ProgrammesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammesViewHolder holder, int position) {
        holder.mText.setText(ProgrammesList.get(position).getOffer());
        holder.mImageview.setImageResource(ProgrammesList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return ProgrammesList.size();
    }

    public class ProgrammesViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageview;
        private TextView mText;
        public ProgrammesViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.bestSellerImage);
            mText = itemView.findViewById(R.id.bestSellerText);
        }
    }
}
