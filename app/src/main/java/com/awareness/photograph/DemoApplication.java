package com.awareness.photograph;

import android.app.Application;

import com.awareness.photograph.presetdata.WeatherDescription;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WeatherDescription.init(this);
    }
}
