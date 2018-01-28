package com.example.android.sunshine.sync;
//  COMPLETED (1) Create a class called SunshineSyncTask
//  COMPLETED (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
//      COMPLETED (3) Within syncWeather, fetch new weather data
//      COMPLETED (4) If we have valid results, delete the old data and insert the new

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class SunshineSyncTask {
    synchronized public static void syncWeather(Context context){
        try {
            URL url = NetworkUtils.getUrl(context);
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            ContentValues[] values = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonResponse);
            if (values != null && values.length > 0){
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);
                contentResolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
