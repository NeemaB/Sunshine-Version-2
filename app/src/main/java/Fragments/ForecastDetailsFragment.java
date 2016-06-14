package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.SettingsActivity;

public class ForecastDetailsFragment extends Fragment {

    String forecastDetails;

    public ForecastDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        forecastDetails = getArguments().getString("forecastDetails");

        TextView textView = (TextView) rootView.findViewById(R.id.forecast_details);
        textView.setText(forecastDetails);


        return rootView;
    }
}