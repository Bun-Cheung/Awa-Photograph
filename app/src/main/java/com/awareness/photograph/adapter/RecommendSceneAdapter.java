package com.awareness.photograph.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awareness.photograph.R;
import com.awareness.photograph.entity.RecommendScene;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecommendSceneAdapter extends RecyclerView.Adapter<RecommendSceneAdapter.ViewHolder> {

    private List<RecommendScene> sceneList;
    private Context context;
    private onItemClickListener onItemClickListener;
    private static final int MAX_DISPLAY_COUNT = 999;

    public RecommendSceneAdapter(Context context, List<RecommendScene> sceneList) {
        this.sceneList = sceneList;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView backgroundImage;
        TextView sceneName;
        TextView checkInCount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.background_image);
            sceneName = itemView.findViewById(R.id.scene_name);
            checkInCount = itemView.findViewById(R.id.check_in_count);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_scene, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendScene scene = sceneList.get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(position));
        }
//        Glide.with(holder.itemView).
//                load(scene.getImage()).
//                placeholder(R.drawable.picture1).
//                into(holder.backgroundImage);
        holder.backgroundImage.setImageResource(scene.getImage());
        holder.sceneName.setText(scene.getSceneName());
        String checkInCountStr;
        if (scene.getCheckInCount() > MAX_DISPLAY_COUNT) {
            checkInCountStr = "999+ users checked in";
        } else {
            checkInCountStr = scene.getCheckInCount() + " users checked in";
        }
        holder.checkInCount.setText(checkInCountStr);
    }

    @Override
    public int getItemCount() {
        return sceneList.size();
    }

    public interface onItemClickListener {
        void onClick(int position);
    }

    void setOnItemClickListener(RecommendSceneAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
