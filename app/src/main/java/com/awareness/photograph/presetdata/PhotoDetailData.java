package com.awareness.photograph.presetdata;

import com.awareness.photograph.entity.PhotoDetail;
import com.awareness.photograph.entity.SimpleWeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class PhotoDetailData {
    private static final List<PhotoDetail> PHOTO_DETAIL_LIST = new ArrayList<>();

    static {
        SimpleWeatherInfo photo_1_weatherInfo = new SimpleWeatherInfo();
        photo_1_weatherInfo.setTemperature(30);
        photo_1_weatherInfo.setWeather("Sunny");
        photo_1_weatherInfo.setUvIndex(5);
        photo_1_weatherInfo.setWindSpeed(13);

        PhotoDetail photo_1_detail = new PhotoDetail();
        photo_1_detail.setTimestamp(System.currentTimeMillis());
        photo_1_detail.setPhotoPath("sample_picture_1.png");
        photo_1_detail.setCollected(true);
        photo_1_detail.setLatitude(23.233);
        photo_1_detail.setLongitude(113.3332);
        photo_1_detail.setLightIntensity(3800);
        photo_1_detail.setWeatherInfo(photo_1_weatherInfo);

        SimpleWeatherInfo photo_2_weatherInfo = new SimpleWeatherInfo();
        photo_2_weatherInfo.setTemperature(26);
        photo_2_weatherInfo.setWeather("Sunny");
        photo_2_weatherInfo.setUvIndex(4);
        photo_2_weatherInfo.setWindSpeed(5);

        PhotoDetail photo_2_detail = new PhotoDetail();
        photo_2_detail.setTimestamp(System.currentTimeMillis());
        photo_2_detail.setPhotoPath("sample_picture_2.png");
        photo_2_detail.setCollected(false);
        photo_2_detail.setLatitude(21.345);
        photo_2_detail.setLongitude(114.678);
        photo_2_detail.setLightIntensity(5030);
        photo_2_detail.setWeatherInfo(photo_2_weatherInfo);

        SimpleWeatherInfo photo_3_weatherInfo = new SimpleWeatherInfo();
        photo_3_weatherInfo.setTemperature(33);
        photo_3_weatherInfo.setWeather("Cloudy");
        photo_3_weatherInfo.setUvIndex(4);
        photo_3_weatherInfo.setWindSpeed(3);

        PhotoDetail photo_3_detail = new PhotoDetail();
        photo_3_detail.setTimestamp(System.currentTimeMillis());
        photo_3_detail.setPhotoPath("sample_picture_3.png");
        photo_3_detail.setLatitude(20.456);
        photo_3_detail.setLongitude(139.1234);
        photo_3_detail.setLightIntensity(4321.1);
        photo_3_detail.setWeatherInfo(photo_3_weatherInfo);

        PHOTO_DETAIL_LIST.add(photo_1_detail);
        PHOTO_DETAIL_LIST.add(photo_2_detail);
        PHOTO_DETAIL_LIST.add(photo_3_detail);
    }

    public static List<PhotoDetail> getPhotoDetailList() {
        return PHOTO_DETAIL_LIST;
    }
}
