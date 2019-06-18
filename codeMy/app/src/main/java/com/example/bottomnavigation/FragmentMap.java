package com.example.bottomnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.google.android.gms.common.GoogleApiAvailability;

public class FragmentMap extends Fragment {

    public FragmentMap()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);
        return view;
    }
}

