package hu.ait.android.weathernow.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import hu.ait.android.weathernow.R;
import hu.ait.android.weathernow.WeatherActivity;
import hu.ait.android.weathernow.data.current_weather.WeatherResult;

public class FragmentMainWeather extends Fragment {

    TextView tvCityName;
    TextView tvWeather;
    TextView tvTemperature;
    ImageView ivWeatherIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_weather, container, false);
        tvCityName = (TextView) rootView.findViewById(R.id.tvCityName);
        tvWeather = (TextView) rootView.findViewById(R.id.tvWeather);
        tvTemperature = (TextView) rootView.findViewById(R.id.tvTemperature);
        ivWeatherIcon = (ImageView) rootView.findViewById(R.id.ivWeatherIcon);



        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(WeatherResult event) {
        tvCityName.setText(event.getName());
        tvTemperature.setText(event.getMain().getTemp().toString());
        tvWeather.setText(event.getWeather().getMain());

        Glide.with(this).load(((WeatherActivity)getActivity()).weatherURL(event.getWeather().getIcon()))
                .centerCrop()
                .into(ivWeatherIcon);

    }

}
