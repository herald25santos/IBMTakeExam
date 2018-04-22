package com.santos.herald.ibmpracticalexam.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherCoordinatesEntity {

    @SerializedName("lon")
    @Expose
    public float lon;

    @SerializedName("lat")
    @Expose
    public float lat;

}
