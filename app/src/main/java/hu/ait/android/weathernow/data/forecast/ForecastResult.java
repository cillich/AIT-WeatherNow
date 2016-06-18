package hu.ait.android.weathernow.data.forecast;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastResult {

    @SerializedName("city")
    @Expose
    public City city;
    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public Double message;
    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public java.util.List<List> list = new ArrayList<List>();

    public City getCity() {
        return city;
    }

    /**
     * Retrieves the weather for tomorrow
     *
     * @return List representing tomorrow's weather
     */
    public List getTomorrowList() {
        // Only return tomorrow's weather
        return list.get(0);
    }

}