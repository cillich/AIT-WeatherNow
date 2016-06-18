package hu.ait.android.weathernow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import hu.ait.android.weathernow.MainActivity;
import hu.ait.android.weathernow.R;

public class FragmentNewCity extends DialogFragment {

    public static final String TAG = "FragmentNewCity";
    EditText etCityName;
    Button btnAddCity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_city, null, false);

        etCityName = (EditText) rootView.findViewById(R.id.etNewCityName);
        btnAddCity = (Button) rootView.findViewById(R.id.btnAddCity);
        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).newCityHandler(etCityName.getText().toString());
                dismiss();
            }
        });
        getDialog().setTitle(R.string.titleAddCity);
        setCancelable(true);


        return rootView;
    }



}
