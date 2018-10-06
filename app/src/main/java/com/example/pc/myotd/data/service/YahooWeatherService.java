package com.example.pc.myotd.data.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.pc.myotd.data.data.Channel;
import com.example.pc.myotd.data.data.Item;
import com.example.pc.myotd.data.listener.WeatherServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by PIER on 05/01/2016.
 */
public class YahooWeatherService {
    private WeatherServiceListener listener;
    private Exception error;
    private String max="";
    private String min="";
    private static int tmax=0;
    private static int tmin=0;
    private static int tcurrent=0;


    public YahooWeatherService(WeatherServiceListener listener) {
        this.listener = listener;
    }

    public static int getTMax(){
        return tmax;
    }
    public static int getTMin(){
        return tmin;


    }

    public static int getT(){
        return tcurrent;
    }



    public String getmax(){
        return this.max;
    }
    public String getmin(){
        return this.min;
    }

    public void refreshWeather(String location) {

        new AsyncTask<String, Void, Channel>() {
            @Override
            protected Channel doInBackground(String[] locations) {

                String location = locations[0];

                Channel channel = new Channel();

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", location);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json&u=c", Uri.encode(YQL));


                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    String[] h=result.toString().split("high"+'"'+":"+'"');
                    h=h[1].split(""+'"');
                    String high=h[0];
                    String[] l=result.toString().split("low"+'"'+":"+'"');
                    l=l[1].split(""+'"');
                    String low=l[0];

                    int ma= (int) (Math.round(Integer.parseInt(high) - 32)/(1.8));
                    int mi= (int) (Math.round(Integer.parseInt(low) - 32)/(1.8));
                    tmin=mi;
                    tmax=ma;

                    max="Max: "+ma+"°C";
                    min="Min: "+mi+"°C";

                    JSONObject data = new JSONObject(result.toString());

                    JSONObject queryResults = data.optJSONObject("query");


                    int count = queryResults.optInt("count");

                    if (count == 0) {
                        error = new LocationWeatherException("No weather information found for " + location);
                        return null;
                    }

                    JSONObject channelJSON = queryResults.optJSONObject("results").optJSONObject("channel");
                    channel.populate(channelJSON);


                    Item item = channel.getItem();

                    int tcurrent= (int) Math.round((item.getCondition().getTemperature() - 32)/(1.8));

                    return channel;

                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Channel channel) {

                if (channel == null && error != null) {
                    listener.serviceFailure(error);
                } else {
                    listener.serviceSuccess(channel);
                }

            }

        }.execute(location);
    }

    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> ArrayList<T> asCollection(String jsonArrayVal) throws JSONException {
        if (null == jsonArrayVal) {
            return null;
        }
        ArrayList<T> result = new ArrayList<T>();

        JSONArray arrayVal = new JSONArray(jsonArrayVal);
        for (int i = 0; i < arrayVal.length(); i++) {
            result.add((T)arrayVal.get(i));
        }
        return result;
    }


}

