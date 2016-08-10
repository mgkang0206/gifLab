package com.example.mgkan.playwithgif.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgkan on 2016-08-09.
 */
public class Gif {

  @SerializedName("images")
  private Images images;

  public Gif(Images images){
    this.images = images;
  }

  public Images getImages() {
    return images;
  }

  public void setImages(Images images) {
    this.images = images;
  }
}
