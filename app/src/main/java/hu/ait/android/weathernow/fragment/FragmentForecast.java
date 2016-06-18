package hu.ait.android.weathernow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import hu.ait.android.weathernow.R;
import hu.ait.android.weathernow.WeatherActivity;
import hu.ait.android.weathernow.data.forecast.ForecastResult;


public class FragmentForecast extends Fragment {

    ImageView ivForecastIcon;
    TextView tvForecastWeather;
    TextView tvForecastMax;
    TextView tvForecastMin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_forecast, container, false);
        ivForecastIcon = (ImageView) rootView.findViewById(R.id.ivForecastIcon);
        tvForecastWeather = (TextView) rootView.findViewById(R.id.tvForecastWeather);
        tvForecastMax = (TextView) rootView.findViewById(R.id.tvForecastMax);
        tvForecastMin = (TextView) rootView.findViewById(R.id.tvForecastMin);
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
    public void onEvent(ForecastResult e) {
        tvForecastWeather.setText(e.getTomorrowList().getWeather().getMain());
        tvForecastMax.setText(getActivity().getString(R.string.labelMaxT) + e.getTomorrowList().getTemp().getTomorrowMax());
        tvForecastMin.setText(getActivity().getString(R.string.labelMinT) + e.getTomorrowList().getTemp().getTomorrowMin());

        Glide.with(this).load(((WeatherActivity)getActivity()).weatherURL(e.getTomorrowList().getWeather().getIcon()))
                .centerCrop()
                .into(ivForecastIcon);
    }


}
