package com.csatimes.dojma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.csatimes.dojma.adapters.ContactAdapter;
import com.csatimes.dojma.models.ContactItem;
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

public class ContactsActivity extends AppCompatActivity implements ContactAdapter.OnContactItemClicked {

    private RecyclerView contactsRecyclerView;
    private DatabaseReference contactReference;
    private ValueEventListener contactListener;
    private Realm database;
    private List<String> contactTypes;
    private List<RealmList<ContactItem>> dataSet;
    private ContactAdapter adapter;

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }

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


        contactsRecyclerView = (RecyclerView) findViewById(R.id.content_contacts_rv);

        contactReference = FirebaseDatabase.getInstance().getReference("contacts");

        database = Realm.getDefaultInstance();
        contactTypes = new ArrayList<>();

        RealmResults<ContactItem> foo = database.where(ContactItem.class).distinct("type").sort("id", Sort.ASCENDING);
        dataSet = new ArrayList<>();
        for (int i = 0; i < foo.size(); i++) {
            contactTypes.add(foo.get(i).getType());
            RealmList<ContactItem> bar = new RealmList<>();
            bar.addAll(database.where(ContactItem.class).equalTo("type", foo.get(i).getType()).findAll());
            dataSet.add(i, bar);
        }


        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter = new ContactAdapter(dataSet, contactTypes);

        contactsRecyclerView.setAdapter(adapter);
        adapter.setOnContactItemClicked(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        contactListener = returnContactListener();
        contactReference.addValueEventListener(contactListener);

    }

    private ValueEventListener returnContactListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                database.executeTransaction(new Realm.Transaction() {
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
                        database.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                ContactItem cif = realm.createObject(ContactItem.class);
                                cif.setEmail(ci.getEmail());
                                cif.setId(id);
                                cif.setName(ci.getName());
                                cif.setType(type);
                                cif.setNumber(ci.getNumber());
                            }
                        });
                    }

                }

                RealmResults<ContactItem> foo = database.where(ContactItem.class).distinct("type").sort("id", Sort.ASCENDING);

                dataSet.clear();
                contactTypes.clear();

                for (int i = 0; i < foo.size(); i++) {
                    contactTypes.add(foo.get(i).getType());
                    RealmList<ContactItem> bar = new RealmList<>();
                    bar.addAll(database.where(ContactItem.class).equalTo("type", foo.get(i).getType()).findAll());
                    dataSet.add(i, bar);
                }

                //Always call generatePositions after notifyDataSetChanged
                adapter.notifyDataSetChanged();
                adapter.generatePositions();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        contactReference.removeEventListener(contactListener);
        database.close();
    }

    @Override
    public void onCallButtonClicked(String tel) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        startActivity(intent);

    }

    @Override
    public void onEmailButtonClicked(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Override
    public void onContactAddClicked(String name, String tel, String email) {

    }
}
