package com.santos.herald.ibmpracticalexam.network;

import com.santos.herald.ibmpracticalexam.data.WeatherEntity;
import com.santos.herald.ibmpracticalexam.data.WeatherResponse;
import com.santos.herald.ibmpracticalexam.network.response.ResponseList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {


//    //User Authentication
//    @FormUrlEncoded
//    @POST("oauth/token")
//    Observable<OAuthModel> requestAuth(
//            @Field("client_id") Integer client_id,
//            @Field("client_secret") String client_secret,
//            @Field("grant_type") String grant_type,
//            @Field("username") String username,
//            @Field("password") String password
//    );

    @GET("data/2.5/weather")
    Observable<WeatherResponse> fetchWeatherList(
            @Query("q") String q,
            @Query("appid") String appid
    );

}