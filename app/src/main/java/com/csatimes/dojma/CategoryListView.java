package com.csatimes.dojma;

/**
 * Created by yash on 16/7/16.
 */

import android.app.ListActivity;
import android.os.Bundle;

import com.csatimes.dojma.models.HeraldNewsItemFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class CategoryListView extends ListActivity {
    private Realm database;
    private RealmResults<HeraldNewsItemFormat> categories;
    private RealmList<HeraldNewsItemFormat> resultsList;


    //private String[] categorydata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = Realm.getDefaultInstance();
        categories = database.where(HeraldNewsItemFormat.class).findAllSorted("categoryTitle", Sort.ASCENDING);
        resultsList = new RealmList<>();
        Set<HeraldNewsItemFormat> set = new HashSet<>();
        set.addAll(categories);

        //ArrayList<String> myStringArray1 = new ArrayList<String>();
        List<String> catlist = new ArrayList<>(set.size());
        List<HeraldNewsItemFormat> catlistarr = new ArrayList<>();
        catlistarr.addAll(categories);

        Set<HeraldNewsItemFormat> set2 = new HashSet<>();
        set2.addAll(catlistarr);

        catlistarr.clear();
        catlist.clear();


        for (HeraldNewsItemFormat temp : set2) {
            if (!set2.contains(catlist)) {
                catlist.add(temp.getCategoryTitle());
            }
        }
        final List<String> catlist2 = new ArrayList<>();


        for (int i = 0; i < catlist.size(); i++) {
            int flag = 0;
            for (int j = i + 1; j < catlist.size(); j++) {
                if (catlist.get(j).compareToIgnoreCase(catlist.get(i)) == 0) {
                    flag = 1;
                    break;

                }
            }
            if (flag == 0) {
                if (catlist.get(i).compareToIgnoreCase("") != 0)
                    catlist2.add(catlist.get(i));
            }
        }
        Collections.sort(catlist2);

/*
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.label, catlist2);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        this.setListAdapter(adapter);
        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), OpenCategoryListView.class);
                // sending data to new activity
                i.putExtra("myCategoryTag", catlist2.get(position));
                startActivity(i);

            }
        });

*/
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}
