package com.awareness.photograph.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awareness.photograph.R;
import com.awareness.photograph.Utils;
import com.awareness.photograph.entity.PhotoDetail;
import com.awareness.photograph.entity.SimpleWeatherInfo;

import java.util.List;

public class PhotoDetailAdapter extends RecyclerView.Adapter<PhotoDetailAdapter.ViewHolder> {
    private List<PhotoDetail> photoDetailList;
    private onClickCollectionListener onClickCollectionListener;

    public PhotoDetailAdapter(List<PhotoDetail> photoDetailList) {
        this.photoDetailList = photoDetailList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoIv;
        TextView dateTv;
        TextView lightTv;
        TextView weatherTv;
        TextView latLngTv;
        ImageButton collectionIb;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoIv = itemView.findViewById(R.id.iv_photo);
            lightTv = itemView.findViewById(R.id.tv_light_intensity);
            dateTv = itemView.findViewById(R.id.tv_date);
            weatherTv = itemView.findViewById(R.id.tv_weather);
            latLngTv = itemView.findViewById(R.id.tv_lat_lng);
            collectionIb = itemView.findViewById(R.id.ib_collect);
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
        Bitmap photoBitmap = detail.getPhoto();
        if (photoBitmap == null) {
            photoBitmap = Utils.decodeImage(holder.itemView.getContext(), detail.getPhotoPath());
        }
        holder.photoIv.setImageBitmap(photoBitmap);
        holder.dateTv.setText(Utils.formatTimestamp(detail.getTimestamp()));
        String lightStr = detail.getLightIntensity() + " lux";
        holder.lightTv.setText(lightStr);
        SimpleWeatherInfo weatherInfo = detail.getWeatherInfo();
        String weatherStr = weatherInfo.getTemperature() + "℃  " +
                weatherInfo.getWeather() + "  UV:" + weatherInfo.getUvIndex() +
                "  WindSpeed:" + weatherInfo.getWindSpeed() + "km/h";
        holder.weatherTv.setText(weatherStr);
        String latLngStr = "lat:" + detail.getLatitude() + " ,lng:" + detail.getLongitude();
        holder.latLngTv.setText(latLngStr);
        if (detail.isCollected()) {
            holder.collectionIb.setImageResource(R.drawable.ic_collect_fill);
        }
        if (onClickCollectionListener != null) {
            holder.collectionIb.setOnClickListener(v -> {
                onClickCollectionListener.onClick(v, position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return photoDetailList.size();
    }

    public interface onClickCollectionListener {
        void onClick(View view, int position);
    }

    public void setOnClickCollectionListener(onClickCollectionListener listener) {
        this.onClickCollectionListener = listener;
    }
}
