package com.csatimes.dojma;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.adapters.LinkRv;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

/**
 * Created by yash on 21/7/16.
 */

public class LinkListView extends AppCompatActivity{


    LinkRv adapter;
    private String response = null;
    private DatabaseReference links = FirebaseDatabase.getInstance().getReference().child
            ("links");
    private Vector<LinkItem> linkItems = new Vector<>(10,2);
    private String LinkTitle = "_title";
    private RecyclerView linkRecyclerView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView emptyList;
    private String sprefLinkNumber = "LINK_number";
    //private String sprefPreFix = "GAZETTE_number_";
    private String sprefTitlePostFix = "_title";
    private String sprefUrlPostFix = "_url";

    public LinkListView(){

    }

    @Override
    public void onCreate(
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gazette);
        sharedPreferences=getSharedPreferences(DHC.USER_PREFERENCES,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        emptyList = (TextView) findViewById(R.id.gazette_empty_text);
        linkRecyclerView = (RecyclerView) findViewById(R.id.gazette_listview);

        linkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linkRecyclerView.setHasFixedSize(true);
        linkRecyclerView.setItemAnimator(new DefaultItemAnimator());

        setOldValues();
        adapter=new LinkRv(this, linkItems);
        linkRecyclerView.setAdapter(adapter);


    }




    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }


    @Override
    public void onResume() {
        super.onResume();

        if(isOnline()) {

            links.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    emptyList.setVisibility(View.GONE);
                    linkRecyclerView.setVisibility(View.VISIBLE);
                    linkItems.clear();
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        try {
                            linkItems.add(shot.getValue(LinkItem.class));
                        }catch(Exception ignore){

                        }
                    }

                    for(int i=0;i<linkItems.size();i++){

                        editor.putString(sprefTitlePostFix+i,linkItems.get(i).getTitle());
                        editor.putString(sprefUrlPostFix+i,linkItems.get(i).getUrl());
                    }

                    editor.putInt(sprefLinkNumber,linkItems.size());
                    editor.apply();
                    adapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    setOldValues();
                    adapter.notifyDataSetChanged();

                }
            });


        }
        else
        {
            setOldValues();
            adapter.notifyDataSetChanged();


        }


    }

    private void setOldValues(){
        int linkNumbers=sharedPreferences.getInt(sprefLinkNumber,0);

        emptyList.setVisibility(View.GONE);
        linkRecyclerView.setVisibility(View.VISIBLE);
        linkItems.clear();

        for(int i=0;i<linkNumbers;i++){

            linkItems.add(new LinkItem(sharedPreferences.getString(sprefTitlePostFix+i,"-"),sharedPreferences.getString(sprefUrlPostFix+i,"-")));

        }



    }

}
