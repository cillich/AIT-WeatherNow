package hu.ait.android.weathernow.data.forecast;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hu.ait.android.weathernow.data.current_weather.Coord;


public class City {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("coord")
    @Expose
    public Coord coord;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("population")
    @Expose
    public Integer population;

    public String getName() {
        return name;
    }

}
