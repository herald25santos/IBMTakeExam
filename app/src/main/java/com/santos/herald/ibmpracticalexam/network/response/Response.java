package com.santos.herald.ibmpracticalexam.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response<T> {

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public T data;

}