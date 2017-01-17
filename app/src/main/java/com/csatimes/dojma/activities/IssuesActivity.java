package com.csatimes.dojma.activities;

/**
 * Created by yash on 16/7/16.
 */

import android.app.ListActivity;
import android.os.Bundle;

import com.csatimes.dojma.models.HeraldItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class IssuesActivity extends ListActivity {
    private Realm database;
    private RealmResults<HeraldItem> categories;
    private RealmList<HeraldItem> resultsList;


    //private String[] categorydata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = Realm.getDefaultInstance();
        categories = database.where(HeraldItem.class).findAllSorted("categoryTitle", Sort.ASCENDING);
        resultsList = new RealmList<>();
        Set<HeraldItem> set = new HashSet<>();
        set.addAll(categories);

        //ArrayList<String> myStringArray1 = new ArrayList<String>();
        List<String> catlist = new ArrayList<>(set.size());
        List<HeraldItem> catlistarr = new ArrayList<>();
        catlistarr.addAll(categories);

        Set<HeraldItem> set2 = new HashSet<>();
        set2.addAll(catlistarr);

        catlistarr.clear();
        catlist.clear();


        for (HeraldItem temp : set2) {
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
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}
