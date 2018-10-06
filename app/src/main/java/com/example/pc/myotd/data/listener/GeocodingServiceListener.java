package com.example.pc.myotd.data.listener;

import com.example.pc.myotd.data.data.LocationResult;

/**
 * Created by PIER on 05/01/2016.
 */
public interface  GeocodingServiceListener {
    void geocodeSuccess(LocationResult location);

    void geocodeFailure(Exception exception);
}

