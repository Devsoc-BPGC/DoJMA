package com.csatimes.dojma.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.SearchAdapter;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.models.TypeItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_TITLE;

public class UtilitiesContactsActivity extends AppCompatActivity {

    private DatabaseReference mContactReference;
    private ValueEventListener mContactListener;
    private Realm mDatabase;
    private List<TypeItem> dataSet;
    private SearchAdapter mContactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme();
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


        RecyclerView contactsRecyclerView = (RecyclerView) findViewById(R.id.content_contacts_rv);

        mContactReference = FirebaseDatabase.getInstance().getReference("contacts");

        mDatabase = Realm.getDefaultInstance();

        dataSet = new ArrayList<>();
        generateContacts();

        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mContactsAdapter = new SearchAdapter(this,dataSet);

        contactsRecyclerView.setAdapter(mContactsAdapter);

    }

    private void generateContacts() {
        dataSet.clear();
        RealmResults<ContactItem> foo = mDatabase.where(ContactItem.class).distinct("type").sort("id", Sort.ASCENDING);
        for (int i = 0; i < foo.size(); i++) {
            dataSet.add(new TypeItem(CONTACT_ITEM_TYPE_TITLE,foo.get(i).getType()));
            RealmList<ContactItem> bar = new RealmList<>();
            bar.addAll(mDatabase.where(ContactItem.class).equalTo("type", foo.get(i).getType()).findAll());
            for (int j=0;j<bar.size();j++)
            dataSet.add(new TypeItem(CONTACT_ITEM_TYPE_CONTACT,bar.get(j)));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContactListener = returnContactListener();
        mContactReference.addValueEventListener(mContactListener);
    }

    private ValueEventListener returnContactListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(ContactItem.class);
                    }
                });
                for (DataSnapshot childShot : dataSnapshot.getChildren()
                        ) {
                    final String type = childShot.child("type").getValue(String.class);
                    final int id = childShot.child("id").getValue(Integer.class);

                    for (DataSnapshot grandChildShot : childShot.child("contact").getChildren()) {
                        final ContactItem ci = grandChildShot.getValue(ContactItem.class);
                        mDatabase.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                ContactItem cif = realm.createObject(ContactItem.class);
                                cif.setEmail(ci.getEmail());
                                cif.setId(id);
                                cif.setName(ci.getName());
                                cif.setType(type);
                                cif.setNumber(ci.getNumber());
                                cif.setSub1(ci.getSub1());
                                cif.setSub2(ci.getSub2());
                                cif.setIcon(ci.getIcon());
                            }
                        });
                    }
                }
                generateContacts();
                mContactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    /**
     * Remove the reference to Firebase Database once the activity is not visible any more
     * we use onStop because reference was added in {@link UtilitiesContactsActivity#onStart()}
     */
    @Override
    protected void onStop() {
        super.onStop();
        mContactReference.removeEventListener(mContactListener);
    }

    /**
     * Destroy the mDatabase reference as soon as activity is destroyed
     * Since the reference was created in the onCreate method, it is best that
     * it is closed in {@link UtilitiesContactsActivity#onDestroy()}
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }
}
