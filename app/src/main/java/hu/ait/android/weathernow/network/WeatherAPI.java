package hu.ait.android.weathernow.network;

import java.util.List;
import java.util.Map;

import hu.ait.android.weathernow.data.current_weather.WeatherResult;
import hu.ait.android.weathernow.data.forecast.ForecastResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WeatherAPI {

    @GET("weather")
    Call<WeatherResult> getCurrentWeather(@Query("q") String q,
                                          @Query("units") String units,
                                          @Query("appid") String appid);

    @GET("forecast/daily")
    Call<ForecastResult> getForecast(@Query("q") String q,
                                     @Query("units") String units,
                                     @Query("appid") String appid,
                                     @Query("cnt") String cnt);

}
