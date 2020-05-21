package com.awareness.photograph.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awareness.photograph.R;

import java.util.ArrayList;
import java.util.List;

public class MainPhotoListAdapter extends RecyclerView.Adapter<MainPhotoListAdapter.ViewHolder> {

    private List<Integer> photoList = new ArrayList<>();

    public MainPhotoListAdapter(List<Integer> photoList) {
        this.photoList = photoList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mainPhoto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainPhoto = itemView.findViewById(R.id.main_photo);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mainPhoto.setImageResource(photoList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
