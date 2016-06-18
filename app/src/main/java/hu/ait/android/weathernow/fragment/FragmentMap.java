package hu.ait.android.weathernow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import hu.ait.android.weathernow.R;
import hu.ait.android.weathernow.data.current_weather.WeatherResult;

public class FragmentMap extends Fragment implements OnMapReadyCallback{

    //AIzaSyD2cz8j-5dbPB_QFvTw_r6kEbibLPNu8NQ
    private GoogleMap map;
    private LatLng city;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(FragmentMap.this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //Attempt to set the marker if the event is already received
        //Fixes a defect where the map has no marker
        addCityMarker();
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
        city = new LatLng(event.getCoord().getLat(), event.getCoord().getLon());
        //Attempt to add the city marker if the map is already created
        //Fixes a defect where the map has no marker
        addCityMarker();
    }

    /**
     * Adds the city marker to the map if the city's latitude and logitude are known
     */
    private void addCityMarker() {
        if (city != null) {
            //Removes any pre-existing markers
            map.clear();
            //Adds marker to the map
            map.addMarker(new MarkerOptions().position(city));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(city)
                    .tilt(30)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }
}
