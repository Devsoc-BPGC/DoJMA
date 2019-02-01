package com.csatimes.dojma.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.SearchAdapter;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.models.TypeItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.csatimes.dojma.utilities.DHC.CONTACTS_SHOW_TAXI_DATA;
import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.CONTACT_ITEM_TYPE_TITLE;

public class UtilitiesContactsActivity extends BaseActivity {

    private Realm mDatabase;
    private List<TypeItem> dataSet;
    private SearchAdapter mContactsAdapter;
    private DatabaseReference mContactReference = FirebaseDatabase.getInstance().getReference("contacts");
    private ValueEventListener mContactListener;
    private TextView errorText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = findViewById(R.id.contacts_toolbar);
        errorText = findViewById(R.id.textView_activity_contacts_error);
        RecyclerView contactsRecyclerView = findViewById(R.id.content_contacts_rv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        mDatabase = Realm.getDefaultInstance();

        dataSet = new ArrayList<>();
        generateContacts();

        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (dataSet.isEmpty()) {
            errorText.setVisibility(View.VISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
        }
        mContactsAdapter = new SearchAdapter(this, dataSet);

        contactsRecyclerView.setAdapter(mContactsAdapter);
    }

    private void generateContacts() {
        boolean showTaxiData = getIntent().getBooleanExtra(CONTACTS_SHOW_TAXI_DATA, false);
        dataSet.clear();
        RealmResults<ContactItem> results = mDatabase.where(ContactItem.class).distinct("type").sort("id", Sort.ASCENDING).findAll();
        for (ContactItem item : results) {
            if (showTaxiData) {
                if (item.type.equalsIgnoreCase("taxi")) {
                    dataSet.add(new TypeItem(CONTACT_ITEM_TYPE_TITLE, item.type));
                    RealmList<ContactItem> bar = new RealmList<>();
                    bar.addAll(mDatabase.where(ContactItem.class).equalTo("type", item.type).findAll());
                    for (int j = 0; j < bar.size(); j++) {
                        dataSet.add(new TypeItem(CONTACT_ITEM_TYPE_CONTACT, bar.get(j)));
                    }
                    if (dataSet.isEmpty()) {
                        errorText.setVisibility(View.VISIBLE);
                    } else {
                        errorText.setVisibility(View.GONE);
                    }
                }
            } else {
                if (!item.type.equalsIgnoreCase("taxi")) {
                    dataSet.add(new TypeItem(CONTACT_ITEM_TYPE_TITLE, item.type));
                    RealmList<ContactItem> bar = new RealmList<>();
                    bar.addAll(mDatabase.where(ContactItem.class).equalTo("type", item.type).findAll());
                    for (int j = 0; j < bar.size(); j++) {
                        dataSet.add(new TypeItem(CONTACT_ITEM_TYPE_CONTACT, bar.get(j)));
                    }
                    if (dataSet.isEmpty()) {
                        errorText.setVisibility(View.VISIBLE);
                    } else {
                        errorText.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContactListener = returnContactListener();
        mContactReference.addListenerForSingleValueEvent(mContactListener);
    }

    private ValueEventListener returnContactListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.executeTransaction(realm -> realm.delete(ContactItem.class));
                for (DataSnapshot childShot : dataSnapshot.getChildren()) {
                    final String type = childShot.child("type").getValue(String.class);
                    final int id = childShot.child("id").getValue(Integer.class);

                    for (DataSnapshot grandChildShot : childShot.child("contacts").getChildren()) {
                        try {
                            final ContactItem ci = grandChildShot.getValue(ContactItem.class);
                            if (ci == null) {
                                continue;
                            }
                            mDatabase.executeTransaction(realm -> {
                                ci.type = type;
                                ci.id = id;
                                realm.insertOrUpdate(ci);
                            });
                        } catch (Exception e) {
                            DHC.log("Contacts format wrong");
                        }
                    }
                }
                generateContacts();
                mContactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.log(databaseError.getMessage());
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        mContactReference.removeEventListener(mContactListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
