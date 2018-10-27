package com.example.huyhoang8398.usthweather;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ForecastFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View forecast = inflater.inflate(R.layout.fragment_forecast, container, false);
        forecast.setBackgroundColor(Color.WHITE);
        return forecast;
    }
}
