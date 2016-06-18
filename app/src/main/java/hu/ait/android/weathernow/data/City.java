package hu.ait.android.weathernow.data;

import com.orm.SugarRecord;

public class City extends SugarRecord {

    String cityName;

    public City() {

    }

    public City(String name) {
        cityName = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
