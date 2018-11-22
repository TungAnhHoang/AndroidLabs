package com.example.tunganh.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class WeatherForecast extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar progressWeather = findViewById(R.id.progressWeather);
        progressWeather.setVisibility(View.INVISIBLE);

        ForecastQuery query = new ForecastQuery();
        query.execute();
    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String speed;
        private String min;
        private String max;
        private String temperature;
        private String picture;
        private String picURL;
        private Bitmap weatherPic;
        private ProgressBar progressBar = findViewById(R.id.progressWeather);

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream stream = conn.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(stream, "UTF-8");

                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getName().equals("temperature")) {
                        temperature = xpp.getAttributeValue(null, "value");
                        publishProgress(25);
                        min = xpp.getAttributeValue(null, "min");
                        publishProgress(50);
                        max = xpp.getAttributeValue(null, "max");
                        publishProgress(75);
                    } else if (xpp.getName().equals("speed")) {
                        speed = xpp.getAttributeValue(null, "value");
                    } else if (xpp.getName().equals("weather")) {
                        picture = xpp.getAttributeValue(null, "icon");
                        picURL = "http://openweathermap.org/img/w/" + picture + ".png";
                        if (fileExistance(picture + ".png")) {
                            Log.i("downloaded", picURL);
                            Log.i("downloaded", "Downloaded, getting from storage: " + picture + ".png");

                            FileInputStream file = null;
                            weatherPic = BitmapFactory.decodeStream(file);
                        } else {
                            Log.i("downloading", picURL);
                            Log.i("downloading", "Downloading from internet: " + picture + ".png");

                            weatherPic = HttpUtils.getImage(picURL);
                            FileOutputStream output = openFileOutput(picture + ".png", Context.MODE_PRIVATE);
                            weatherPic.compress(Bitmap.CompressFormat.PNG, 80, output);
                            output.flush();
                            output.close();
                        }
                        publishProgress(100);
                    }
                    xpp.next();
                }
            } catch (Exception e) {
                System.out.print("File not found");
            }
            return null;
        }

        private boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ImageView image = findViewById(R.id.currentWeather);
            TextView currentTemp = findViewById(R.id.currentTemp);
            TextView min = findViewById(R.id.minTemp);
            TextView max = findViewById(R.id.maxTemp);
            TextView speed = findViewById(R.id.wind);

            image.setImageBitmap(weatherPic);
            currentTemp.setText(String.format(getResources().getString(R.string.current), this.temperature));
            min.setText(String.format(getResources().getString(R.string.min), this.min));
            max.setText(String.format(getResources().getString(R.string.max), this.max));
            speed.setText(String.format(getResources().getString(R.string.speed), this.speed));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}