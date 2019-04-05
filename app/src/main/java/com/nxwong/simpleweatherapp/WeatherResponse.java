package com.nxwong.simpleweatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {

    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<Weather>();
}
