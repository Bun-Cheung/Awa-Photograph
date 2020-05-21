package com.awareness.photograph.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awareness.photograph.R;
import com.awareness.photograph.entity.RecommendScene;

import java.util.List;

public class RecommendSceneAdapter extends RecyclerView.Adapter<RecommendSceneAdapter.ViewHolder> {

    private List<RecommendScene> sceneList;
    private onItemClickListener onItemClickListener;
    private static final int MAX_DISPLAY_COUNT = 999;

    public RecommendSceneAdapter(List<RecommendScene> sceneList) {
        this.sceneList = sceneList;
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

    public void setOnItemClickListener(RecommendSceneAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
