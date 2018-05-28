package com.csatimes.dojma.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.FavouritesActivity;
import com.csatimes.dojma.activities.IssuesActivity;
import com.csatimes.dojma.models.HeraldItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

public class IssuesFragment extends Fragment {

    Realm mDatabase;
    ListView issuesListView;
    Toolbar mToolbar;

    public static Fragment newInstance(){
        IssuesFragment issuesFragment = new IssuesFragment();
        return issuesFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        issuesListView = (ListView) view.findViewById(R.id.content_issues_lv);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDatabase = Realm.getDefaultInstance();
        RealmList<HeraldItem> issuesList = new RealmList<>();
        issuesList.addAll(mDatabase.where(HeraldItem.class).distinct("category").sort("category", Sort.ASCENDING).findAll());
        final List<String> titlesList = new ArrayList<>();
        for (int i = 0; i < issuesList.size(); i++) {
            titlesList.add(issuesList.get(i).getCategory());
        }
        issuesListView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, titlesList));
        issuesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), FavouritesActivity.class);
                intent.putExtra("category", titlesList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_issues, container, false);

    }

}
