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
    ContactItem[] hostelCN = new ContactItem[]{
            new ContactItem("Dr. Alpesh M Dhorajia(AH1)","0832-2580452"),
            new ContactItem("Dr. Saroj S. Baral(AH2)","0832-2580119"),
            new ContactItem("Dr. Vikas V. Chaudhari(AH3)","0832-2580117"),
            new ContactItem("Dr. Ch. V.V.S.N.V. Prasad(AH4)","0832-2580420"),
            new ContactItem("Dr. Halan Prakash(AH5)","0832-2580344"),
            new ContactItem("Dr. Ranjit S Patil(AH6)","0832-2580209"),
            new ContactItem("Dr. Ravi Prasad Aduri(AH7)","0832-2580394"),
            new ContactItem("Dr. Ramesha C. K.(AH8)","0832-2580361"),
            new ContactItem("Dr. Angshuman Sarkar(CH1)","0832-2580324"),
            new ContactItem("Dr. Arnab Banerjee(CH2)","0832-2580361"),
            new ContactItem("Dr. Mainak Banerjee(CH3)","0832-2580347"),
            new ContactItem("Dr. P Bhavana(CH4)","0832-2580156"),
            new ContactItem("Dr. Priyanka Desai(CH5)","0832-2580128"),
            new ContactItem("Dr. Sanjay K Sahay(CH6)","0832-2580243")

    };
    ContactItem[] academicsCN = new ContactItem[]{};
    ContactItem[] othersCN = new ContactItem[]{

            new ContactItem("Prof. Sasikumar Punnekkat(Director)","0832-2580101"),
            new ContactItem("Prof. Ashwin Srinivasan(Deputy Director)","0832-2580111"),
            new ContactItem("Mr. Chandu Lamani(SAC Incharge)","0832-2580717"),
            new ContactItem("Dr. Anuradha V(Library Incharge)","0832-2580402")


    };
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
        //rvAcademics = (RecyclerView) findViewById(R.id.contacts_categories_academic_RecyclerView);
        rvHostel = (RecyclerView) findViewById(R.id.contacts_categories_hostel_RecyclerView);
        rvOthers = (RecyclerView) findViewById(R.id.contacts_categories_others_RecyclerView);
        rvMisc = (RecyclerView) findViewById(R.id.contacts_categories_misc_RecyclerView);

        adapter1 = new ContactAdapter(this, emergencyCN);
       // adapter2 = new ContactAdapter(this, academicsCN);
        adapter3 = new ContactAdapter(this, hostelCN);
        adapter4 = new ContactAdapter(this, othersCN);
        adapter5 = new ContactAdapter(this, miscCN);

        rvEmergency.setAdapter(adapter1);
        rvEmergency.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvEmergency.setHasFixedSize(false);
        rvEmergency.setItemAnimator(new DefaultItemAnimator());

        /*rvAcademics.setAdapter(adapter2);
        rvAcademics.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAcademics.setHasFixedSize(false);
        rvAcademics.setItemAnimator(new DefaultItemAnimator());*/

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
