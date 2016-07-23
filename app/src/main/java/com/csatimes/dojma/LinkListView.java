package com.csatimes.dojma;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yash on 21/7/16.
 */

public class LinkListView extends ListActivity {
    private String response = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.offline_category_toolbar);


        List<String> Linkname=new ArrayList<>();
        Linkname.add("Moodle");
        Linkname.add("CSA");
        Linkname.add("SWD");
        Linkname.add("BITS GOA");
        Linkname.add("BITS ERP");

        final List<String> LinkUrl=new ArrayList<>();
        LinkUrl.add("http://10.1.1.242/moodle/");
        LinkUrl.add("http://csatimes.co.in");
        LinkUrl.add("https://swd.bits-goa.ac.in/");
        LinkUrl.add("http://www.bits-pilani.ac.in/Goa/");
        LinkUrl.add("http://www.bits-pilani.ac.in/goa/login");

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.label, Linkname);
        this.setListAdapter(adapter);
        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Launching new Activity on selecting single List Item

                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(LinkUrl.get(position)));

                // sending data to new activity
                startActivity(intent);



            }
        });



    }


   /* private class DownloadList extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(DHC.linksAdrress);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                StringBuilder sb = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                    sb.append("\n");
                }
                response = sb.toString();
                in.close();

            } catch (Exception e) {
                response = null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }*/
}
