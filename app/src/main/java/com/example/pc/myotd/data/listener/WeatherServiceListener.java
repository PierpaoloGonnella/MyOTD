package com.example.pc.myotd.data.listener;

import com.example.pc.myotd.data.data.Channel;

/**
 * Created by PIER on 05/01/2016.
 */
public interface WeatherServiceListener {
        void serviceSuccess(Channel channel);

        void serviceFailure(Exception exception);
        }
