package com.santos.herald.ibmpracticalexam.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WeatherResponse {

    @SerializedName("coord")
    @Expose
    public WeatherCoordinatesEntity coord;

    @SerializedName("weather")
    @Expose
    public List<WeatherEntity> weather;

    @SerializedName("main")
    @Expose
    public WeatherMainEntity main;

    @SerializedName("name")
    @Expose
    public String weatherLocation;

    @SerializedName("id")
    @Expose
    public int id;

}
