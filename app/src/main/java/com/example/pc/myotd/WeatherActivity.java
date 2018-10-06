package com.example.pc.myotd;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.myotd.data.data.Channel;
import com.example.pc.myotd.data.data.Item;
import com.example.pc.myotd.data.data.LocationResult;
import com.example.pc.myotd.data.listener.GeocodingServiceListener;
import com.example.pc.myotd.data.listener.WeatherServiceListener;
import com.example.pc.myotd.data.service.GoogleMapsGeocodingService;
import com.example.pc.myotd.data.service.WeatherCacheService;
import com.example.pc.myotd.data.service.YahooWeatherService;

public class WeatherActivity extends Fragment implements WeatherServiceListener, GeocodingServiceListener, LocationListener {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    TextView max;
    TextView min;

    private YahooWeatherService weatherService;
    private GoogleMapsGeocodingService geocodingService;
    private WeatherCacheService cacheService;

    private ProgressDialog dialog;


    private int weatherServiceFailures = 0;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SharedPreferences preferences = null;

    public static WeatherActivity newInstance(int sectionNumber) {
        WeatherActivity fragment = new WeatherActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public WeatherActivity() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //FragmentActivity faActivity = (FragmentActivity) super.getActivity();
        super.onCreate(savedInstanceState);
        View        llLayout    =    inflater.inflate(R.layout.activity_weather, container, false);
        weatherIconImageView = (ImageView) llLayout.findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)  llLayout.findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)  llLayout.findViewById(R.id.conditionTextView);
        locationTextView = (TextView)  llLayout.findViewById(R.id.locationTextView);
        max =(TextView) llLayout.findViewById(R.id.max);
        min =(TextView) llLayout.findViewById(R.id.min);

        weatherService = new YahooWeatherService(this);
        geocodingService = new GoogleMapsGeocodingService(this);
        cacheService = new WeatherCacheService(this.getContext());

        preferences = super.getActivity().getPreferences(Context.MODE_PRIVATE);

        dialog = new ProgressDialog(this.getContext());
        dialog.setMessage(getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();


        String cachedLocation = preferences.getString(getString(R.string.location), null);

        if (cachedLocation == null) {
            getWeatherFromCurrentLocation();
        } else {
            weatherService.refreshWeather(cachedLocation);
        }

        return llLayout;
    }


    private void getWeatherFromCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);


        Criteria locationCriteria = new Criteria();
        locationCriteria.setAccuracy(Criteria.ACCURACY_MEDIUM);

        String provider = locationManager.getBestProvider(locationCriteria, true);

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestSingleUpdate(provider, this, null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        MenuInflater menuInflater = inflater;
        menuInflater.inflate(R.menu.weather_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.currentLocation:
                dialog.show();
                getWeatherFromCurrentLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();

        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, this.getContext().getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        int tempC = (int) (Math.round(item.getCondition().getTemperature() - 32)/(1.8));
        String F=channel.getUnits().getTemperature();

        String temperatureLabel = getResources().getString(R.string.temperature_output, tempC, "C");

        temperatureTextView.setText(temperatureLabel);
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(channel.getLocation());

        max.setText( weatherService.getmax());
        min.setText( weatherService.getmin());

    }

    @Override
    public void serviceFailure(Exception exception) {
        if (weatherServiceFailures > 0) {
            dialog.hide();
            Toast.makeText(this.getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            weatherServiceFailures++;
            cacheService.load(this);
        }
    }

    @Override
    public void geocodeSuccess(LocationResult location) {
        weatherService.refreshWeather(location.getAddress());

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.location), location.getAddress());
        editor.commit();
    }

    @Override
    public void geocodeFailure(Exception exception) {
        cacheService.load(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        geocodingService.refreshLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
}
