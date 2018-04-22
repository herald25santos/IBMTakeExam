package com.santos.herald.ibmpracticalexam.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WeatherEntity implements Serializable {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("main")
    @Expose
    public String main;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("icon")
    @Expose
    public String icon;

}
