package com.example.mgkan.playwithgif.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgkan on 2016-08-09.
 */
public class OriginalStill {

  @SerializedName("url")
  private String url;

  public OriginalStill(String url){
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
