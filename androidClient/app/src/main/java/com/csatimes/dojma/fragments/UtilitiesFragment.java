package com.csatimes.dojma.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.UtilitiesAdapter;
import com.csatimes.dojma.models.UtilitiesItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UtilitiesFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // data to populate the RecyclerView with
        List<UtilitiesItem> menuItems = new ArrayList<>();

        menuItems.add(new UtilitiesItem("GajaLakshmi", R.drawable.ic_gaja, "menu"));
        menuItems.add(new UtilitiesItem("Food King", R.drawable.ic_food_king,"menu"));
        menuItems.add(new UtilitiesItem("Red Chillies", R.drawable.ic_red_chillies,"menu"));
        menuItems.add(new UtilitiesItem("Ice & Spice", R.drawable.ic_ice_spice,"menu"));
        menuItems.add(new UtilitiesItem("Monginis", R.drawable.ic_monginis,"menu"));
        menuItems.add(new UtilitiesItem("A Night Mess", R.drawable.ic_noodles,"menu"));
        menuItems.add(new UtilitiesItem("C Night Mess", R.drawable.ic_noodles,"menu"));
        menuItems.add(new UtilitiesItem("D Night Mess", R.drawable.ic_noodles,"menu"));
        menuItems.add(new UtilitiesItem("A Mess", R.drawable.ic_mess,"menu"));
        menuItems.add(new UtilitiesItem("C Mess", R.drawable.ic_mess,"menu"));
        menuItems.add(new UtilitiesItem("D Mess", R.drawable.ic_mess,"menu"));

        List<UtilitiesItem> utilitiesItems = new ArrayList<>();

        utilitiesItems.add(new UtilitiesItem("Contacts", R.drawable.ic_noun_contacts_636095,"util"));
        utilitiesItems.add(new UtilitiesItem("Taxi", R.drawable.ic_cab_new,"util"));
//        utilitiesItems.add(new UtilitiesItem("Scooty Rentals", R.drawable.ic_scooter,"util"));
//        utilitiesItems.add(new UtilitiesItem("Car Rentals", R.drawable.ic_car_rental,"util"));
        utilitiesItems.add(new UtilitiesItem("Campus Map", R.drawable.ic_map,"util"));
        utilitiesItems.add(new UtilitiesItem("Links", R.drawable.ic_links,"util"));

        List<UtilitiesItem> lookbackItems = new ArrayList<>();

        lookbackItems.add(new UtilitiesItem("Archives", R.drawable.ic_inbox,"util"));
//        lookbackItems.add(new UtilitiesItem("Gazette", R.drawable.ic_gazzette,"util"));

        // set up the RecyclerView for menu
        RecyclerView recyclerView = view.findViewById(R.id.menu_recycler);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns));
        UtilitiesAdapter adapter = new UtilitiesAdapter(getActivity().getApplicationContext(), menuItems);
        recyclerView.setAdapter(adapter);

        // set up the RecyclerView for utilities
        RecyclerView recyclerView1 = view.findViewById(R.id.utility_recycler);
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns));
        UtilitiesAdapter adapter1 = new UtilitiesAdapter(getActivity().getApplicationContext(), utilitiesItems);
        recyclerView1.setAdapter(adapter1);

        // set up the RecyclerView for lookback
        RecyclerView recyclerView2 = view.findViewById(R.id.lookback_recycler);
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns));
        UtilitiesAdapter adapter2 = new UtilitiesAdapter(getActivity().getApplicationContext(), lookbackItems);
        recyclerView2.setAdapter(adapter2);
    }
}
