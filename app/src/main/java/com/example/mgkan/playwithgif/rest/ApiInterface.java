package com.example.mgkan.playwithgif.rest;

import com.example.mgkan.playwithgif.model.Gif;
import com.example.mgkan.playwithgif.model.GifResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mgkan on 2016-08-03.
 */
public interface ApiInterface {

  @GET("/v1/gifs/translate")
  Call<GifResponse> getGifWithSearch(@Query("api_key") String apiKey,
                                     @Query("s") String word);
}
