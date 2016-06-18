package hu.ait.android.weathernow.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.weathernow.MainActivity;
import hu.ait.android.weathernow.R;
import hu.ait.android.weathernow.WeatherActivity;
import hu.ait.android.weathernow.data.City;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> implements CityTouchHelperAdapter {

    private Context context;
    private List<City> cities = new ArrayList<City>();

    public CityAdapter(Context context) {
        this.context = context;
        this.cities = City.listAll(City.class);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_row, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvCityName.setText(cities.get(position).getCityName());
        // Set delete button
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDismiss(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void addCity(City city) {
        cities.add(0, city);
        city.save();
        notifyItemInserted(0);
    }

    public void removeCity(int position) {
        cities.get(position).delete();
        cities.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemDismiss(int position) {
        removeCity(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(cities, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(cities, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvCityName;
        ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCityName = (TextView) itemView.findViewById(R.id.tvCityName);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //Sets the view holder's on click listener
            ((MainActivity)v.getContext()).openWeatherActivity(tvCityName.getText().toString());
        }
    }

}
