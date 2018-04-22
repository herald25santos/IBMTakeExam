package com.santos.herald.ibmpracticalexam.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherMainEntity {

    @SerializedName("temp")
    @Expose
    public String temp;

    @SerializedName("pressure")
    @Expose
    public String pressure;

    @SerializedName("humidity")
    @Expose
    public String humidity;

    @SerializedName("temp_min")
    @Expose
    public String temp_min;

    @SerializedName("temp_max")
    @Expose
    public String temp_max;

}
