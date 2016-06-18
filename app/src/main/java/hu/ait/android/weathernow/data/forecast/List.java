package hu.ait.android.weathernow.data.forecast;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("temp")
    @Expose
    public Temp temp;
    @SerializedName("pressure")
    @Expose
    public Double pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("weather")
    @Expose
    public java.util.List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("speed")
    @Expose
    public Double speed;
    @SerializedName("deg")
    @Expose
    public Integer deg;
    @SerializedName("clouds")
    @Expose
    public Integer clouds;
    @SerializedName("rain")
    @Expose
    public Double rain;

    public Temp getTemp() {
        return temp;
    }

    public Weather getWeather() {
        return weather.get(0);
    }

}
