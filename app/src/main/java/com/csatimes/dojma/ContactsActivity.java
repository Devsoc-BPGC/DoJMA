package com.csatimes.dojma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
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

    private DatabaseReference mContactReference;
    private ValueEventListener mContactListener;
    private Realm database;
    private List<String> contactTypes;
    private List<RealmList<ContactItem>> dataSet;
    private ContactAdapter adapter;

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

        mContactListener = returnContactListener();
        mContactReference.addValueEventListener(mContactListener);

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

    /**
     * Remove the reference to Firebase Database once the activity is not visible any more
     * we use onStop because reference was added in {@link ContactsActivity#onStart()}
     */
    @Override
    protected void onStop() {

        super.onStop();
        mContactReference.removeEventListener(mContactListener);

    }

    /**
     * Destroy the database reference as soon as activity is destroyed
     * Since the reference was created in the onCreate method, it is best that
     * it is closed in {@link ContactsActivity#onDestroy()}
     */
    @Override
    protected void onDestroy() {

        super.onDestroy();
        database.close();

    }

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }

    /**
     * @param tel Telephone number as a String object
     */
    @Override
    public void onCallButtonClicked(String tel) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        startActivity(intent);

    }

    /**
     * @param email Email ID provided as a String object
     */
    @Override
    public void onEmailButtonClicked(String email) {

        Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:" + email));
        startActivity(Intent.createChooser(intent, "Send Email"));

    }

    /**
     * @param name  Person's name
     * @param tel   Telephone number as a string
     * @param email Also as a string
     * @see <a href = "https://developer.android.com/training/contacts-provider/modify-data.html">Modifying Data Documentation</a> for more
     */
    @Override
    public void onContactAddClicked(String name, String tel, String email) {

        Intent addContactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        addContactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, tel);
        addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        startActivity(addContactIntent);

    }
}
