package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Vikramaditya Kukreja on 20-07-2016.
 */

public class ContactAdapter extends ArrayAdapter {

    static String[] contactNames = {
            "Dr. Doomsday",
            "Dr. Who",
            "Reception"
    };
    public String[] contactNumbers = {
            "1",
            "2",
            "3"
    };


    public ContactAdapter(Context context, String[] array) {
        super(context, 0, array);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.utilities_contact_format, parent, false);
        }
        // Lookup view for data population
        final TextView contactName = (TextView) convertView.findViewById(R.id.utilities_medc_name);
        // Populate the data into the template view using the data object
        contactName.setText(contactNames[position]);

        ImageButton call = (ImageButton) convertView.findViewById(R.id.utitlities_medc_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contactNumbers[position]));
                Log.e("TAG", "calling");
                getContext().startActivity(intent);

            }
        });
        ImageButton add = (ImageButton) convertView.findViewById(R.id.utitlities_medc_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, contactNames[position]);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactNumbers[position]);
                getContext().startActivity(intent);

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
