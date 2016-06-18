package hu.ait.android.weathernow.data.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("main")
    @Expose
    public String main;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("icon")
    @Expose
    public String icon;

    /**
     *
     * @return String representing tomorrow's weather
     */
    public String getMain() {
        return main;
    }

    /**
     *
     * @return String representing the icon for tomorrow's weather
     */
    public String getIcon() {
        return icon;
    }

}