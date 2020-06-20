package com.awareness.photograph.entity;

public class SimpleWeatherInfo {
    private long temperature;
    private String weather;
    private int UvIndex;
    private int windSpeed;

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setUvIndex(int uvIndex) {
        UvIndex = uvIndex;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public long getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }

    public int getUvIndex() {
        return UvIndex;
    }

    public int getWindSpeed() {
        return windSpeed;
    }
}
