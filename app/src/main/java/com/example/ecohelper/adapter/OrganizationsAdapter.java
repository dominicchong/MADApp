package com.example.ecohelper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecohelper.model.organizations;
import com.example.ecohelper.R;

import java.util.List;

public class OrganizationsAdapter extends RecyclerView.Adapter<com.example.ecohelper.adapter.OrganizationsAdapter.OrganizationsViewHolder> {

    private List<organizations> OrganizationsList;
    public OrganizationsAdapter(List<organizations> NewsList){
        this.OrganizationsList = OrganizationsList;
    }
    @NonNull
    @Override
    public com.example.ecohelper.adapter.OrganizationsAdapter.OrganizationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_organizations , parent , false);
        return new com.example.ecohelper.adapter.OrganizationsAdapter.OrganizationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationsViewHolder holder, int position) {
        holder.mText.setText(OrganizationsList.get(position).getOffer());
        holder.mImageview.setImageResource(OrganizationsList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return OrganizationsList.size();
    }

    public class OrganizationsViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageview;
        private TextView mText;
        public OrganizationsViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.bestSellerImage);
            mText = itemView.findViewById(R.id.bestSellerText);
        }
    }
}