package com.example.mgkan.playwithgif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mgkan.playwithgif.model.Gif;
import com.example.mgkan.playwithgif.model.GifResponse;
import com.example.mgkan.playwithgif.rest.ApiClient;
import com.example.mgkan.playwithgif.rest.ApiInterface;
import com.koushikdutta.ion.Ion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  private final static String API_KEY = "dc6zaTOxFJmzC";
  private ProgressBar mProgressBar;
  String searchString;
  private ImageView photoStill;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    final ApiInterface apiService =
      ApiClient.getClient().create(ApiInterface.class);

    final EditText searchBox = (EditText) findViewById(R.id.search_box);
    Button searchButton = (Button) findViewById(R.id.searchButton);
    final ImageView photo = (ImageView) findViewById(R.id.image_box);
    photoStill = (ImageView) findViewById(R.id.image_box_still);

    mProgressBar = (ProgressBar) findViewById(R.id.progress);
    mProgressBar.setMax(100);

    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        searchString = searchBox.getText().toString();

        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        Call<GifResponse> call = apiService.getGifWithSearch(API_KEY, searchString);
        call.enqueue(new Callback<GifResponse>() {
          @Override
          public void onResponse(Call<GifResponse> call, Response<GifResponse> response) {
            int statusCode = response.code();
            String gifUrl = response.body().getData().getImages().getOriginal().getUrl();
            final String gifStill = response.body().getData().getImages().getOriginalStill().getUrl();

            Ion.with(photo)
              .load(gifUrl);

            Ion.with(photoStill)
              .load(gifStill);

            Button invertButton = (Button) findViewById(R.id.invertButton);
            invertButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                new ImageProcessingAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,gifStill);
              }
            });

          }

          @Override
          public void onFailure(Call<GifResponse> call, Throwable t) {
            Log.d("TEST", t.toString());
          }
        });
      }
    });
  }

  private class ImageProcessingAsyncTask extends AsyncTask<String, Integer, Bitmap> {

    //TODO: Fill in the parameter type
    @Override
    protected Bitmap doInBackground(String... params) {
      try {
        URL url = new URL(params[0]);
        Bitmap bitmap1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        return invertImageColors(bitmap1);//change method
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    //TODO: Fill in the parameter type
    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      //TODO: Update the progress bar
    }

    //TODO: Fill in the parameter type
    @Override
    protected void onPostExecute(Bitmap bitmap) {
      photoStill.setImageBitmap(bitmap);
      mProgressBar.setVisibility(View.INVISIBLE);
      //TODO: Complete this method
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      mProgressBar.setVisibility(View.VISIBLE);
      mProgressBar.setProgress(0);
      //TODO: Complete this method
    }

    private Bitmap invertImageColors(Bitmap bitmap) {
      //You must use this mutable Bitmap in order to modify the pixels
      Bitmap mutableBitmap = bitmap.copy(bitmap.getConfig(), true);

      //Loop through each pixel, and invert the colors
      for (int i = 0; i < mutableBitmap.getWidth(); i++) {
        for (int j = 0; j < mutableBitmap.getHeight(); j++) {
          int px = mutableBitmap.getPixel(i, j);
          int red = Color.red(px);
          int green = Color.green(px);
          int blue = Color.blue(px);
          int newPx = Color.argb(255, 255 - red, 255 - green, 255 - blue);
          mutableBitmap.setPixel(i, j, newPx);
          //TODO: Get the Red, Green, and Blue values for the current pixel, and reverse them
          //TODO: Set the current pixel's color to the new, reversed value
        }
        int progressVal = Math.round((long) (100 * (i / (1.0 * mutableBitmap.getWidth()))));
        mProgressBar.setProgress(progressVal);
        //TODO: Update the progress bar. progressVal is the current progress value out of 100
      }
      return mutableBitmap;
    }
  }
}