package com.nxwong.simpleweatherapp;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    public String main;

    @SerializedName("description")
    public String description;
}
