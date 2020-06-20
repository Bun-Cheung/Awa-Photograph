package com.awareness.photograph.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.awareness.photograph.PhotoDetailActivity;
import com.awareness.photograph.R;
import com.awareness.photograph.presetdata.SceneData;

import java.util.List;

public class MainPhotoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_TYPE_TOP_RECYCLE_VIEW = 1000;
    private final int ITEM_TYPE_MAIN_PHOTO = 1001;

    private List<Bitmap> photoList;
    private Context context;

    public MainPhotoListAdapter(Context context, List<Bitmap> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    static class MainPhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView mainPhoto;

        MainPhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            mainPhoto = itemView.findViewById(R.id.main_photo);
        }
    }

    static class TopRecycleViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        TopRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycle_view_top);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_TOP_RECYCLE_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_top, parent, false);
            return new TopRecycleViewHolder(view);
        }
        if (viewType == ITEM_TYPE_MAIN_PHOTO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_photo, parent, false);
            return new MainPhotoViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainPhotoViewHolder) {
            MainPhotoViewHolder viewHolder = (MainPhotoViewHolder) holder;
            viewHolder.mainPhoto.setImageBitmap(photoList.get(position - 1));
//            Glide.with(holder.itemView).load(photoList.get(position - 1)).into(viewHolder.mainPhoto);
        }
        if (holder instanceof TopRecycleViewHolder) {
            RecyclerView recyclerView = ((TopRecycleViewHolder) holder).recyclerView;
            if (recyclerView.getAdapter() == null) {
                RecommendSceneAdapter adapter = new RecommendSceneAdapter(context, SceneData.getMockData());
                adapter.setOnItemClickListener(position1 -> {
                    Intent intent = new Intent(context, PhotoDetailActivity.class);
                    context.startActivity(intent);
                });
                recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE_TOP_RECYCLE_VIEW : ITEM_TYPE_MAIN_PHOTO;
    }
}
