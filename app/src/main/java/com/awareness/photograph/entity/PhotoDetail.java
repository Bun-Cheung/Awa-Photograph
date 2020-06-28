package com.awareness.photograph.entity;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class PhotoDetail {
    @PrimaryKey
    @NonNull
    private String photoPath = "";
    private long timestamp;
    @Ignore
    private Bitmap photo;

    @Embedded
    private SimpleWeatherInfo weatherInfo;
    private double latitude;
    private double longitude;
    @ColumnInfo(name = "light_intensity")
    private float lightIntensity;
    private String label;
    private boolean collected;

    public boolean isInfoFilled() {
        return (weatherInfo != null && timestamp != 0 && latitude != 0
                && longitude != 0 && lightIntensity != 0);
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(@NonNull String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public SimpleWeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(SimpleWeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(float lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
