package com.example.pc.myotd.data.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.pc.myotd.data.data.Channel;
import com.example.pc.myotd.data.listener.WeatherServiceListener;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by PIER on 05/01/2016.
 */
public class WeatherCacheService {
    private Context context;
    private Exception error;
    private final String CACHED_WEATHER_FILE = "weather.data";

    public WeatherCacheService(Context context) {
        this.context = context;
    }

    public void save(Channel channel) {
        new AsyncTask<Channel, Void, Void>() {
            @Override
            protected Void doInBackground(Channel[] channels) {

                FileOutputStream outputStream;

                try {
                    outputStream = context.openFileOutput(CACHED_WEATHER_FILE, Context.MODE_PRIVATE);
                    outputStream.write(channels[0].toJSON().toString().getBytes());
                    outputStream.close();

                } catch (IOException e) {
                    // TODO: file save operation failed
                }

                return null;
            }
        }.execute(channel);
    }

    public void load(final WeatherServiceListener listener) {

        new AsyncTask<WeatherServiceListener, Void, Channel>() {
            private WeatherServiceListener weatherListener;

            @Override
            protected Channel doInBackground(WeatherServiceListener[] serviceListeners) {
                weatherListener = serviceListeners[0];
                try {
                    FileInputStream inputStream = context.openFileInput(CACHED_WEATHER_FILE);

                    StringBuilder cache = new StringBuilder();
                    int content;
                    while ((content = inputStream.read()) != -1) {
                        cache.append((char) content);
                    }

                    inputStream.close();

                    JSONObject jsonCache = new JSONObject(cache.toString());

                    Channel channel = new Channel();
                    channel.populate(jsonCache);

                    return channel;

                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Channel channel) {
                if (channel == null && error != null) {
                    weatherListener.serviceFailure(error);
                } else {
                    weatherListener.serviceSuccess(channel);
                }
            }
        }.execute(listener);
    }
}
