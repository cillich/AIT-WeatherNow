package hu.ait.android.weathernow.data.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp {

    @SerializedName("day")
    @Expose
    public Double day;
    @SerializedName("min")
    @Expose
    public Double min;
    @SerializedName("max")
    @Expose
    public Double max;
    @SerializedName("night")
    @Expose
    public Double night;
    @SerializedName("eve")
    @Expose
    public Double eve;
    @SerializedName("morn")
    @Expose
    public Double morn;

    /**
     *
     * @return double maximum temperature for tomorrow
     */
    public Double getTomorrowMax() {
        return max;
    }

    /**
     *
     * @return double minimum temperature for tomorrow
     */
    public Double getTomorrowMin() {
        return min;
    }

}