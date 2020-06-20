package com.awareness.photograph.entity;

public class PhotoDetail {
    private String photoPath;
    private long timestamp;
    private SimpleWeatherInfo weatherInfo;
    private double latitude;
    private double longitude;
    private double lightIntensity;
    private boolean collected;

    public boolean isInfoFilled() {
        return (photoPath != null && weatherInfo != null && timestamp != 0 && latitude != 0
                && longitude != 0 && lightIntensity != 0);
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
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

    public double getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(double lightIntensity) {
        this.lightIntensity = lightIntensity;
    }
}
