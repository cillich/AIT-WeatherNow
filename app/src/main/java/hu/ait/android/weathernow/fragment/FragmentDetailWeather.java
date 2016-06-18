package hu.ait.android.weathernow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import hu.ait.android.weathernow.R;
import hu.ait.android.weathernow.data.current_weather.WeatherResult;

public class FragmentDetailWeather extends Fragment {

    TextView tvMaxTemp;
    TextView tvMinTemp;
    TextView tvHumidity;
    TextView tvPressure;
    TextView tvWindSpeed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_detail_weather, container, false);

        tvMaxTemp = (TextView) rootView.findViewById(R.id.tvMaxTemp);
        tvMinTemp = (TextView) rootView.findViewById(R.id.tvMinTemp);
        tvHumidity = (TextView) rootView.findViewById(R.id.tvHumidity);
        tvPressure = (TextView) rootView.findViewById(R.id.tvPressure);
        tvWindSpeed = (TextView) rootView.findViewById(R.id.tvWindSpeed);




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
        tvMaxTemp.setText(getActivity().getString(R.string.labelMaxT) + String.valueOf(event.getMain().getTempMax()));
        tvMinTemp.setText(getActivity().getString(R.string.labelMinT) + String.valueOf(event.getMain().getTempMin()));
        tvHumidity.setText(getActivity().getString(R.string.labelHumidity) + String.valueOf(event.getMain().getHumidity()));
        tvPressure.setText(getActivity().getString(R.string.labelPressure) + String.valueOf(event.getMain().getPressure()));
        tvWindSpeed.setText(getActivity().getString(R.string.labelWindSpeed) + String.valueOf(event.getWind().getSpeed()));
    }
}
