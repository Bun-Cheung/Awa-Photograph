package com.awareness.photograph.entity;

public class SimpleWeatherInfo {
    private long temperature;
    private String weather;
    private int UvIndex;
    private int windSpeed;

    public SimpleWeatherInfo(long temperature, String weather, int uvIndex, int windSpeed) {
        this.temperature = temperature;
        this.weather = weather;
        UvIndex = uvIndex;
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
