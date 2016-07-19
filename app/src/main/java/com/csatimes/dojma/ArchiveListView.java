package com.csatimes.dojma;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by yash on 19/7/16.
 */

public class ArchiveListView extends ListActivity {

    private Realm database;
    private RealmResults<HeraldNewsItemFormat> archives;
    private RealmList<HeraldNewsItemFormat> resultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                (ArchiveListView.this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();
        archives = database.where(HeraldNewsItemFormat.class).findAllSorted("originalMonthYear", Sort.ASCENDING);
        resultsList = new RealmList<>();
        Set<HeraldNewsItemFormat> set = new HashSet<>();
        set.addAll(archives);

        List<String> archlist = new ArrayList<>(set.size());
        List<HeraldNewsItemFormat> archlist2 = new ArrayList<>();
        archlist2.addAll(archives);

        Set<HeraldNewsItemFormat> set2 = new HashSet<>();
        set2.addAll(archlist2);
        Set<HeraldNewsItemFormat> setOg = new HashSet<>();
        setOg.addAll(archlist2);
        List<String> archlistOg = new ArrayList<>(set.size());
        for (HeraldNewsItemFormat temp : setOg) {
            archlistOg.add(temp.getOriginalMonthYear());

        }

        archlist2.clear();
        archlist.clear();


        final List<String> archlistOg2 = new ArrayList<>();
        for (int i = 0; i < archlistOg.size(); i++) {
            int flag = 0;
            for (int j = i + 1; j < archlistOg.size(); j++) {
                if (archlistOg.get(j).compareTo(archlistOg.get(i)) == 0) {
                    flag = 1;
                    break;

                }
            }
            if (flag == 0) {
                if (archlistOg.get(i).compareTo("") != 0)
                    archlistOg2.add(archlistOg.get(i));
            }
        }


        Collections.sort(archlistOg2);
        Collections.reverse(archlistOg2);
        final List<String> archlistString = new ArrayList<>();
        for (int i = 0; i < archlistOg2.size(); i++) {

            try {
                SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM", Locale.UK);
                Date date = newDate.parse(archlistOg2.get(i));
                SimpleDateFormat newDateOut = new SimpleDateFormat("MMM yyyy");
                archlistString.add(newDateOut.format(date));
            } catch (ParseException e) {
                //Handle exception here, most of the time you will just log it.
                Log.e("TAG", "exception found in date format");
                e.printStackTrace();
            }


        }
        //Collections.sort(archlistString);


        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.label, archlistString);
        // ListView listView = (ListView) findViewById(R.id.mobile_list);
        this.setListAdapter(adapter);
        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item


                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), OpenArchiveListView.class);
                // sending data to new activity
                i.putExtra("myOriginalMonthYear", archlistOg2.get(position));

                startActivity(i);

            }
        });

    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}
