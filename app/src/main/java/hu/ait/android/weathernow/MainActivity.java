package hu.ait.android.weathernow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import hu.ait.android.weathernow.adapter.CityAdapter;
import hu.ait.android.weathernow.adapter.CityTouchHelperCallback;
import hu.ait.android.weathernow.data.City;
import hu.ait.android.weathernow.fragment.FragmentNewCity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int METRIC = 0;
    public static final int IMPERIAL = 1;
    public static final String METRIC_LABEL = "metric";
    public static final String IMPERIAL_LABEL = "imperial";
    public static final String CITY_NAME = "CITY_NAME";
    public static final String UNITS_PREFERENCE = "UNITS_PREFERENCE";

    public final CityAdapter cityAdapter = new CityAdapter(this);
    public final FragmentManager fragmentManager = getSupportFragmentManager();
    public CharSequence[] unitsList;
    public int units = METRIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unitsList = new CharSequence[] {getString(R.string.labelMetric), getString(R.string.labelImperial)};

        //Set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set up FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity();
            }
        });

        //Set up DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set up RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cityAdapter);

        //Set up ItemTouchHelper
        ItemTouchHelper.Callback callback = new CityTouchHelperCallback(cityAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

    }

    /**
     * Show FragmentNewCity
     */
    private void addCity() {
        FragmentNewCity fragmentNewCity = new FragmentNewCity();
        fragmentNewCity.show(fragmentManager, FragmentNewCity.TAG);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showUnitsDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_city) {
            addCity();
        } else if (id == R.id.nav_about) {
            showToast(getString(R.string.txtAboutApp));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Adds new city to the RecyclerView
     *
     * @param cityName String name of city being added
     */
    public void newCityHandler(String cityName) {
        City city = new City(cityName);
        cityAdapter.addCity(city);
    }

    public void showToast(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }

    public void openWeatherActivity(String cityName) {
        Intent intentWeather = new Intent(MainActivity.this, WeatherActivity.class);
        intentWeather.putExtra(CITY_NAME, cityName);
        intentWeather.putExtra(UNITS_PREFERENCE, units);
        startActivity(intentWeather);
    }

    public void showUnitsDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.titleUnits);
        alertDialogBuilder.setSingleChoiceItems(unitsList, units, null);
        alertDialogBuilder.setPositiveButton(getString(R.string.labelOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                units = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(UNITS_PREFERENCE, units);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        units = savedInstanceState.getInt(UNITS_PREFERENCE);
    }


}
