package com.example.mgkan.playwithgif.model;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;

/**
 * Created by mgkan on 2016-08-09.
 */
public class GifResponse {

  @SerializedName("data")
  private Gif data;

  public Gif getData() {
    return data;
  }

  public void setData(Gif data) {
    this.data = data;
  }
}
