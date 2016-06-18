package hu.ait.android.weathernow.data.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    public Double temp;
    @SerializedName("pressure")
    @Expose
    public Double pressure;
    @SerializedName("humidity")
    @Expose
    public Double humidity;
    @SerializedName("temp_min")
    @Expose
    public Double tempMin;
    @SerializedName("temp_max")
    @Expose
    public Double tempMax;

    public Double getTemp() {return temp;}

    public Double getTempMin() {
        return tempMin;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getHumidity() {

        return humidity;
    }

    public Double getTempMax() {
        return tempMax;
    }
}
