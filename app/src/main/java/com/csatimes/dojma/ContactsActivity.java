package com.csatimes.dojma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ContactsActivity extends AppCompatActivity {
    RecyclerView rvEmergency;
    RecyclerView rvAcademics;
    RecyclerView rvHostel;
    RecyclerView rvOthers;
    RecyclerView rvMisc;

    ContactAdapter adapter1;
    ContactAdapter adapter2;
    ContactAdapter adapter3;
    ContactAdapter adapter4;
    ContactAdapter adapter5;

    ContactItem[] emergencyCN = new ContactItem[]{
            new ContactItem("Main Gate", "+918322580300"),
            new ContactItem("Main Gate 2", "+918326482016"),
            new ContactItem("Medical Helpline", "+919552040123")
    };
    ContactItem[] academicsCN = new ContactItem[]{};
    ContactItem[] hostelCN = new ContactItem[]{};
    ContactItem[] othersCN = new ContactItem[]{};
    ContactItem[] miscCN = new ContactItem[]{};


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.contacts_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rvEmergency = (RecyclerView) findViewById(R.id.contacts_categories_emergency_RecyclerView);
        rvAcademics = (RecyclerView) findViewById(R.id.contacts_categories_academic_RecyclerView);
        rvHostel = (RecyclerView) findViewById(R.id.contacts_categories_hostel_RecyclerView);
        rvOthers = (RecyclerView) findViewById(R.id.contacts_categories_others_RecyclerView);
        rvMisc = (RecyclerView) findViewById(R.id.contacts_categories_misc_RecyclerView);

        adapter1 = new ContactAdapter(this, emergencyCN);
        adapter2 = new ContactAdapter(this, academicsCN);
        adapter3 = new ContactAdapter(this, hostelCN);
        adapter4 = new ContactAdapter(this, othersCN);
        adapter5 = new ContactAdapter(this, miscCN);

        rvEmergency.setAdapter(adapter1);
        rvEmergency.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvEmergency.setHasFixedSize(false);
        rvEmergency.setItemAnimator(new DefaultItemAnimator());

        rvAcademics.setAdapter(adapter2);
        rvAcademics.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAcademics.setHasFixedSize(false);
        rvAcademics.setItemAnimator(new DefaultItemAnimator());

        rvHostel.setAdapter(adapter3);
        rvHostel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHostel.setHasFixedSize(false);
        rvHostel.setItemAnimator(new DefaultItemAnimator());

        rvOthers.setAdapter(adapter4);
        rvOthers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOthers.setHasFixedSize(false);
        rvOthers.setItemAnimator(new DefaultItemAnimator());

        rvMisc.setAdapter(adapter5);
        rvMisc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMisc.setHasFixedSize(false);
        rvMisc.setItemAnimator(new DefaultItemAnimator());

    }

}
