package com.awareness.photograph.presetdata;

import android.content.Context;
import android.util.SparseArray;

import com.awareness.photograph.R;
import com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId;
import com.huawei.hms.kit.awareness.status.weather.constant.WeatherId;

public class WeatherDescription {
    private static SparseArray<String> weatherDescMap = new SparseArray<>();
    private static SparseArray<String> cnWeatherDescMap = new SparseArray<>();

    public static void init(Context context) {
        weatherDescMap.put(WeatherId.SUNNY, context.getString(R.string.weather_sunny));
        weatherDescMap.put(WeatherId.MOSTLY_SUNNY, context.getString(R.string.weather_mostly_sunny));
        weatherDescMap.put(WeatherId.PARTLY_SUNNY, context.getString(R.string.weather_partly_sunny));
        weatherDescMap.put(WeatherId.INTERMITTENT_CLOUDS, context.getString(R.string.weather_intermittent_clouds));
        weatherDescMap.put(WeatherId.HAZY_SUNSHINE, context.getString(R.string.weather_hazy_sunshine));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY, context.getString(R.string.weather_mostly_cloudy));
        weatherDescMap.put(WeatherId.CLOUDY, context.getString(R.string.weather_cloudy));
        weatherDescMap.put(WeatherId.DREARY, context.getString(R.string.weather_dreary));
        weatherDescMap.put(WeatherId.FOG, context.getString(R.string.weather_fog));
        weatherDescMap.put(WeatherId.SHOWERS, context.getString(R.string.weather_showers));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_WITH_SHOWERS, context.getString(R.string.weather_mostly_cloudy_with_showers));
        weatherDescMap.put(WeatherId.PARTLY_SUNNY_WITH_SHOWERS, context.getString(R.string.weather_partly_sunny_with_showers));
        weatherDescMap.put(WeatherId.T_STORMS, context.getString(R.string.weather_T_storms));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_WITH_T_STORMS, context.getString(R.string.weather_mostly_cloudy_with_T_storms));
        weatherDescMap.put(WeatherId.PARTLY_SUNNY_WITH_T_STORMS, context.getString(R.string.weather_partly_sunny_with_T_storms));
        weatherDescMap.put(WeatherId.RAIN, context.getString(R.string.weather_rain));
        weatherDescMap.put(WeatherId.FLURRIES, context.getString(R.string.weather_flurries));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_WITH_FLURRIES, context.getString(R.string.weather_mostly_cloudy_with_flurries));
        weatherDescMap.put(WeatherId.PARTLY_SUNNY_WITH_FLURRIES, context.getString(R.string.weather_partly_sunny_with_flurries));
        weatherDescMap.put(WeatherId.SNOW, context.getString(R.string.weather_snow));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_WITH_SNOW, context.getString(R.string.weather_mostly_cloudy_with_snow));
        weatherDescMap.put(WeatherId.ICE, context.getString(R.string.weather_ice));
        weatherDescMap.put(WeatherId.SLEET, context.getString(R.string.weather_sleet));
        weatherDescMap.put(WeatherId.FREEZING_RAIN, context.getString(R.string.weather_freezing_rain));
        weatherDescMap.put(WeatherId.RAIN_AND_SNOW, context.getString(R.string.weather_rain_and_snow));
        weatherDescMap.put(WeatherId.HOT, context.getString(R.string.weather_hot));
        weatherDescMap.put(WeatherId.COLD, context.getString(R.string.weather_cold));
        weatherDescMap.put(WeatherId.WINDY, context.getString(R.string.weather_windy));
        weatherDescMap.put(WeatherId.CLEAR, context.getString(R.string.weather_clear));
        weatherDescMap.put(WeatherId.MOSTLY_CLEAR, context.getString(R.string.weather_mostly_clear));
        weatherDescMap.put(WeatherId.PARTLY_CLOUDY, context.getString(R.string.weather_partly_cloudy));
        weatherDescMap.put(WeatherId.INTERMITTENT_CLOUDS_2, context.getString(R.string.weather_intermittent_clouds));
        weatherDescMap.put(WeatherId.HAZY_MOONLIGHT, context.getString(R.string.weather_hazy_moonlight));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_2, context.getString(R.string.weather_mostly_cloudy));
        weatherDescMap.put(WeatherId.PARTLY_CLOUDY_WITH_SHOWERS, context.getString(R.string.weather_partly_cloudy_with_showers));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_WITH_SHOWERS_2, context.getString(R.string.weather_mostly_cloudy_with_showers));
        weatherDescMap.put(WeatherId.PARTLY_CLOUDY_WITH_T_STORMS, context.getString(R.string.weather_partly_cloudy_with_T_storms));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_WITH_T_STORMS_2, context.getString(R.string.weather_mostly_cloudy_with_T_storms));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_WITH_FLURRIES_2, context.getString(R.string.weather_mostly_cloudy_with_flurries));
        weatherDescMap.put(WeatherId.MOSTLY_CLOUDY_WITH_SNOW_2, context.getString(R.string.weather_mostly_cloudy_with_snow));

        cnWeatherDescMap.put(CNWeatherId.SUNNY, context.getString(R.string.cnWeather_sunny));
        cnWeatherDescMap.put(CNWeatherId.CLOUDY, context.getString(R.string.cnWeather_cloudy));
        cnWeatherDescMap.put(CNWeatherId.OVERCAST, context.getString(R.string.cnWeather_overcast));
        cnWeatherDescMap.put(CNWeatherId.SHOWER, context.getString(R.string.cnWeather_shower));
        cnWeatherDescMap.put(CNWeatherId.THUNDERSHOWER, context.getString(R.string.cnWeather_thundershower));
        cnWeatherDescMap.put(CNWeatherId.THUNDERSHOWER_WITH_HAIL, context.getString(R.string.cnWeather_thundershower_with_hail));
        cnWeatherDescMap.put(CNWeatherId.SLEET, context.getString(R.string.cnWeather_sleet));
        cnWeatherDescMap.put(CNWeatherId.LIGHT_RAIN, context.getString(R.string.cnWeather_light_rain));
        cnWeatherDescMap.put(CNWeatherId.MODERATE_RAIN, context.getString(R.string.cnWeather_moderate_rain));
        cnWeatherDescMap.put(CNWeatherId.HEAVY_RAIN, context.getString(R.string.cnWeather_heavy_rain));
        cnWeatherDescMap.put(CNWeatherId.STORM, context.getString(R.string.cnWeather_storm));
        cnWeatherDescMap.put(CNWeatherId.HEAVY_STORM, context.getString(R.string.cnWeather_heavy_storm));
        cnWeatherDescMap.put(CNWeatherId.SEVERE_STORM, context.getString(R.string.cnWeather_severe_storm));
        cnWeatherDescMap.put(CNWeatherId.SNOW_FLURRY, context.getString(R.string.cnWeather_snow_flurry));
        cnWeatherDescMap.put(CNWeatherId.LIGHT_SNOW, context.getString(R.string.cnWeather_light_snow));
        cnWeatherDescMap.put(CNWeatherId.MODERATE_SNOW, context.getString(R.string.cnWeather_moderate_snow));
        cnWeatherDescMap.put(CNWeatherId.HEAVY_SNOW, context.getString(R.string.cnWeather_heavy_snow));
        cnWeatherDescMap.put(CNWeatherId.SNOWSTORM, context.getString(R.string.cnWeather_snowstorm));
        cnWeatherDescMap.put(CNWeatherId.FOGGY, context.getString(R.string.cnWeather_foggy));
        cnWeatherDescMap.put(CNWeatherId.ICE_RAIN, context.getString(R.string.cnWeather_ice_rain));
        cnWeatherDescMap.put(CNWeatherId.DUSTSTORM, context.getString(R.string.cnWeather_duststorm));
        cnWeatherDescMap.put(CNWeatherId.LIGHT_TO_MODERATE_RAIN, context.getString(R.string.cnWeather_light_to_moderate_rain));
        cnWeatherDescMap.put(CNWeatherId.MODERATE_TO_HEAVY_RAIN, context.getString(R.string.cnWeather_moderate_to_heavy_rain));
        cnWeatherDescMap.put(CNWeatherId.HEAVY_RAIN_TO_STORM, context.getString(R.string.cnWeather_heavy_rain_to_storm));
        cnWeatherDescMap.put(CNWeatherId.STORM_TO_HEAVY_STORM, context.getString(R.string.cnWeather_storm_to_heavy_storm));
        cnWeatherDescMap.put(CNWeatherId.HEAVY_TO_SEVERE_STORM, context.getString(R.string.cnWeather_heavy_to_severe_storm));
        cnWeatherDescMap.put(CNWeatherId.LIGHT_TO_MODERATE_SNOW, context.getString(R.string.cnWeather_light_to_moderate_snow));
        cnWeatherDescMap.put(CNWeatherId.MODERATE_TO_HEAVY_SNOW, context.getString(R.string.cnWeather_moderate_to_heavy_snow));
        cnWeatherDescMap.put(CNWeatherId.HEAVY_SNOW_TO_SNOWSTORM, context.getString(R.string.cnWeather_heavy_snow_to_snowstorm));
        cnWeatherDescMap.put(CNWeatherId.DUST, context.getString(R.string.cnWeather_dust));
        cnWeatherDescMap.put(CNWeatherId.SAND, context.getString(R.string.cnWeather_sand));
        cnWeatherDescMap.put(CNWeatherId.SANDSTORM, context.getString(R.string.cnWeather_sandstorm));
        cnWeatherDescMap.put(CNWeatherId.DENSE_FOGGY, context.getString(R.string.cnWeather_dense_foggy));
        cnWeatherDescMap.put(CNWeatherId.SNOW, context.getString(R.string.cnWeather_snow));
        cnWeatherDescMap.put(CNWeatherId.MODERATE_FOGGY, context.getString(R.string.cnWeather_moderate_foggy));
        cnWeatherDescMap.put(CNWeatherId.HAZE, context.getString(R.string.cnWeather_haze));
        cnWeatherDescMap.put(CNWeatherId.MODERATE_HAZE, context.getString(R.string.cnWeather_moderate_haze));
        cnWeatherDescMap.put(CNWeatherId.HEAVY_HAZE, context.getString(R.string.cnWeather_heavy_haze));
        cnWeatherDescMap.put(CNWeatherId.SEVERE_HAZE, context.getString(R.string.cnWeather_severe_haze));
        cnWeatherDescMap.put(CNWeatherId.HEAVY_FOGGY, context.getString(R.string.cnWeather_heavy_foggy));
        cnWeatherDescMap.put(CNWeatherId.SEVERE_FOGGY, context.getString(R.string.cnWeather_severe_foggy));
        cnWeatherDescMap.put(CNWeatherId.RAINFALL, context.getString(R.string.cnWeather_rain));
        cnWeatherDescMap.put(CNWeatherId.SNOWFALL, context.getString(R.string.cnWeather_snow));
    }

    public static String getWeatherDesc(int weatherId) {
        return weatherDescMap.get(weatherId);
    }

    public static String getCnWeatherDesc(int cnWeatherId) {
        return cnWeatherDescMap.get(cnWeatherId);
    }
}
