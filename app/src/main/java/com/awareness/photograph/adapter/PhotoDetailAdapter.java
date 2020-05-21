package com.awareness.photograph.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awareness.photograph.R;
import com.awareness.photograph.entity.PhotoDetail;
import com.awareness.photograph.entity.SimpleWeatherInfo;

import java.util.List;

public class PhotoDetailAdapter extends RecyclerView.Adapter<PhotoDetailAdapter.ViewHolder> {
    private List<PhotoDetail> photoDetailList;

    public PhotoDetailAdapter(List<PhotoDetail> photoDetailList) {
        this.photoDetailList = photoDetailList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoIv;
        TextView dateTv;
        TextView weatherTv;
        TextView siteTv;
        TextView collectCountTv;
        TextView praiseCountTv;
        TextView commentCountTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoIv = itemView.findViewById(R.id.iv_photo);
            dateTv = itemView.findViewById(R.id.tv_date);
            weatherTv = itemView.findViewById(R.id.tv_weather);
            siteTv = itemView.findViewById(R.id.tv_site);
            collectCountTv = itemView.findViewById(R.id.tv_collect_count);
            praiseCountTv = itemView.findViewById(R.id.tv_praise_count);
            commentCountTv = itemView.findViewById(R.id.tv_comment_count);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhotoDetail detail = photoDetailList.get(position);
        holder.photoIv.setImageResource(detail.getPhoto());
        holder.dateTv.setText(detail.getDate());
        holder.siteTv.setText(detail.getSite());
        holder.collectCountTv.setText(String.valueOf(detail.getCollectCount()));
        holder.praiseCountTv.setText(String.valueOf(detail.getPraiseCount()));
        holder.commentCountTv.setText(String.valueOf(detail.getCommentCount()));

        SimpleWeatherInfo weatherInfo = detail.getWeatherInfo();
        String weatherStr = weatherInfo.getTemperature() + "℃  " +
                weatherInfo.getWeather() + "  UV:" + weatherInfo.getUvIndex() +
                "  WindSpeed:" + weatherInfo.getWindSpeed() + "km/h";
        holder.weatherTv.setText(weatherStr);
    }

    @Override
    public int getItemCount() {
        return photoDetailList.size();
    }
}
