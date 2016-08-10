package com.example.mgkan.playwithgif.model;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;

/**
 * Created by mgkan on 2016-08-09.
 */
public class Images {

  @SerializedName("original")
  private Original original;

  @SerializedName("original_still")
  private OriginalStill originalStill;

  public Images(Original original, OriginalStill originalStill){
    this.original = original;
    this.originalStill = originalStill;
  }

  public Original getOriginal() {
    return original;
  }

  public void setOriginal(Original original) {
    this.original = original;
  }

  public OriginalStill getOriginalStill() {
    return originalStill;
  }

  public void setOriginalStill(OriginalStill originalStill) {
    this.originalStill = originalStill;
  }
}
