package hu.ait.android.weathernow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import hu.ait.android.weathernow.data.current_weather.WeatherResult;
import hu.ait.android.weathernow.data.forecast.ForecastResult;
import hu.ait.android.weathernow.fragment.FragmentDetailWeather;
import hu.ait.android.weathernow.fragment.FragmentForecast;
import hu.ait.android.weathernow.fragment.FragmentMainWeather;
import hu.ait.android.weathernow.fragment.FragmentMap;
import hu.ait.android.weathernow.network.WeatherAPI;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends FragmentActivity {

    public static final String CITY_WEATHER = "CITY_WEATHER";
    public static final String APPID = "3051ad44bacea335472cab9dafcfd236";
    public static final String HTTP_OPENWEATHERMAP_IMAGE = "http://openweathermap.org/img/w/";
    public static final String PNG = ".png";
    public static int NUM_PAGES = 2;
    public static String NUM_DAYS = "1";
    public int unitsPreference;

    LinearLayout weatherLayout;
    ViewPager topViewPager;
    ViewPager bottomViewPager;
    PagerAdapter currentWeatherPagerAdapter;
    PagerAdapter otherWeatherPagerAdapter;
    CircleIndicator topIndicator;
    CircleIndicator bottomIndicator;


    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        cityName = getIntent().getStringExtra(MainActivity.CITY_NAME);
        unitsPreference = getIntent().getIntExtra(MainActivity.UNITS_PREFERENCE, 0);


        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://api.openweathermap.org/data/2.5/").
                addConverterFactory(GsonConverterFactory.create()).
                build();

        //API requests
        final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        currentWeatherRequest(weatherAPI);
        forecastRequest(weatherAPI);

        weatherLayout = (LinearLayout) findViewById(R.id.weatherLayout);
        topIndicator = (CircleIndicator) findViewById(R.id.currentWeatherIndicator);
        bottomIndicator = (CircleIndicator) findViewById(R.id.otherPagerIndicator);

        //Set up ViewPager
        topViewPager = (ViewPager) findViewById(R.id.currentWeatherPager);
        bottomViewPager = (ViewPager) findViewById(R.id.otherPager);
        currentWeatherPagerAdapter = new CurrentWeatherPagerAdapter(getSupportFragmentManager());
        otherWeatherPagerAdapter = new OtherWeatherPagerAdapter(getSupportFragmentManager());
        topViewPager.setAdapter(currentWeatherPagerAdapter);
        bottomViewPager.setAdapter(otherWeatherPagerAdapter);

        topIndicator.setViewPager(topViewPager);
        bottomIndicator.setViewPager(bottomViewPager);

        currentWeatherPagerAdapter.registerDataSetObserver(topIndicator.getDataSetObserver());
        otherWeatherPagerAdapter.registerDataSetObserver(bottomIndicator.getDataSetObserver());


    }

    private void currentWeatherRequest(WeatherAPI api) {


        Call<WeatherResult> call;

        if (unitsPreference == MainActivity.METRIC) {
            call = api.getCurrentWeather(cityName, MainActivity.METRIC_LABEL, APPID);
        } else {
            call = api.getCurrentWeather(cityName, MainActivity.IMPERIAL_LABEL, APPID);
        }


        call.enqueue(new Callback<WeatherResult>() {

            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {

                if (response.body().getName().trim().equalsIgnoreCase(cityName.trim())) {
                    EventBus.getDefault().post((WeatherResult)response.body());
                } else {
                    Toast.makeText(WeatherActivity.this,
                            R.string.msgNoWeatherInfo,
                            Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
            }
        });
    }

    public void forecastRequest(WeatherAPI api) {
        Call<ForecastResult> call;

        if (unitsPreference == MainActivity.METRIC) {
            call = api.getForecast(cityName, MainActivity.METRIC_LABEL, APPID, NUM_DAYS);
        } else {
            call = api.getForecast(cityName, MainActivity.IMPERIAL_LABEL, APPID, NUM_DAYS);
        }

        call.enqueue(new Callback<ForecastResult>() {
            @Override
            public void onResponse(Call<ForecastResult> call, Response<ForecastResult> response) {

                if (response.body().getCity().getName().trim().equalsIgnoreCase(cityName.trim())) {
                    EventBus.getDefault().post((ForecastResult)response.body());
                } else {
                    Toast.makeText(WeatherActivity.this, R.string.msgNoWeatherInfo, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ForecastResult> call, Throwable t) {
            }
        });
    }



    private class CurrentWeatherPagerAdapter extends FragmentStatePagerAdapter {

        public CurrentWeatherPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new FragmentMainWeather();
                default:
                    return new FragmentDetailWeather();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    private class OtherWeatherPagerAdapter extends FragmentStatePagerAdapter {

        public OtherWeatherPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new FragmentMap();
                default:
                    return new FragmentForecast();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    /**
     * Creates the url to get the icon from the api
     *
     * @param icon String that represents the weather icon
     * @return String url that gets the icon image
     */
    public String weatherURL(String icon) {
        return HTTP_OPENWEATHERMAP_IMAGE + icon + PNG;
    }
}
