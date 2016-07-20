package com.csatimes.dojma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Utilities extends Fragment {



    public Utilities() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilities, container, false);
        ListView listView = (ListView) view.findViewById(R.id.utilities_medc_list);
        listView.setFooterDividersEnabled(true);
        listView.setAdapter(new ContactAdapter(getContext(), ContactAdapter.contactNames));

        return view;
    }

}
