package com.csatimes.dojma.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.csatimes.dojma.R;
import com.csatimes.dojma.aboutapp.AboutAppActivity;
import com.csatimes.dojma.campuswatch.ShortsActivity;
import com.csatimes.dojma.favorites.FavouritesFragment;
import com.csatimes.dojma.fragments.EventsFragment;
import com.csatimes.dojma.fragments.UtilitiesFragment;
import com.csatimes.dojma.herald.HeraldFragment;
import com.csatimes.dojma.fragments.VideosFragment;
import com.csatimes.dojma.home.HomeVm;
import com.csatimes.dojma.home.Section;
import com.csatimes.dojma.issues.IssuesFragment;
import com.csatimes.dojma.models.Member;
import com.csatimes.dojma.models.Person;
import com.csatimes.dojma.services.UpdateCheckerService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.realm.Realm;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.EXTRA_TEXT;
import static com.csatimes.dojma.models.Member.insertMembersFromFirebase;
import static com.csatimes.dojma.models.Person.insertContributorsFromFirebase;
import static com.csatimes.dojma.models.ShortsItem.saveFirebaseData;
import static com.csatimes.dojma.utilities.DHC.MIME_TYPE_PLAINTEXT;
import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES;
import static com.csatimes.dojma.utilities.FirebaseKeys.CAMPUS_WATCH;
import static com.csatimes.dojma.utilities.FirebaseKeys.CONTRIB;
import static com.csatimes.dojma.utilities.FirebaseKeys.MEMBER;
import static com.csatimes.dojma.utilities.SpKeys.FIRST_INSTALL;

@SuppressLint("GoogleAppIndexingApiWarning")
public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = TAG_PREFIX + MainActivity.class.getSimpleName();
    private final List<Person> contributors = new ArrayList<>(0);
    private final List<Member> members = new ArrayList<>(0);
    private HomeVm homeVm;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final Intent intent;
        switch (item.getItemId()) {
            case R.id.action_refresh_herald: {
                intent = new Intent(this, UpdateCheckerService.class);
                startService(intent);
                return true;
            }
            case R.id.action_about_dojma: {
                AboutDojmaActivity.launchAboutDojmaActivity(this,
                        members);
                return true;
            }
            case R.id.action_about_us: {
                AboutAppActivity.launchAboutAppActivity(this,
                        getString(R.string.about_us_main),
                        contributors,
                        getString(R.string.app_name));
                return true;
            }
            case R.id.action_shareapp: {
                final Intent targetIntent = new Intent();
                targetIntent.setAction(ACTION_SEND);
                targetIntent.putExtra(EXTRA_TEXT, getString(R.string.message_app_share));
                targetIntent.setType(MIME_TYPE_PLAINTEXT);
                intent = Intent.createChooser(targetIntent,
                        getString(R.string.share_prompt));
                break;
            }
            case R.id.action_search: {
                intent = new Intent(this, SearchableActivity.class);
                break;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);
        homeVm = ViewModelProviders.of(this).get(HomeVm.class);
        setContentView(R.layout.activity_home);
        final SharedPreferences mPreferences = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);

        if (mPreferences.getBoolean(FIRST_INSTALL, true)) {
            final Intent postDlIntent = new Intent(this,
                    POSTDownloaderActivity.class);
            startActivity(postDlIntent);
            finish();
        }

        final Toolbar mToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_flash));
        mToolbar.setNavigationOnClickListener(v -> {
            Intent i = new Intent(this, ShortsActivity.class);
            startActivity(i);
        });

        homeVm.getCurrentSection().observe(this, section -> {
            final Fragment fragment;
            switch (section) {
                case HERALD:
                    fragment = new HeraldFragment();
                    break;

                case ISSUES:
                    fragment = new IssuesFragment();
                    break;

                case VIDEOS:
                fragment = new VideosFragment();
                break;

                case EVENTS:
                    fragment = new EventsFragment();
                    break;

                case UTILITIES:
                    fragment = new UtilitiesFragment();
                    break;

                default:
                    throw new IllegalStateException("There is no such section.");
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_container, fragment)
                    .commit();
        });


        final BottomNavigationView homeBottomNav = findViewById(R.id.nav_view);
        homeBottomNav.setOnNavigationItemSelectedListener(this);
        contributors.clear();
        final Realm db = Realm.getDefaultInstance();
        for (final Person c : db.where(Person.class).findAll()) {
            if (c.isContributor) {
                contributors.add(db.copyFromRealm(c));
            }
        }
        db.close();
        FirebaseDatabase.getInstance().getReference()
                .child(CONTRIB)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        contributors.clear();
                        final Realm db = Realm.getDefaultInstance();
                        db.executeTransaction(realm ->
                                contributors.addAll(insertContributorsFromFirebase(dataSnapshot,
                                        realm)));
                        db.close();
                    }

                    @Override
                    public void onCancelled(final DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
                    }
                });

        members.clear();
        final Realm dbmember = Realm.getDefaultInstance();
        for (final Member c : dbmember.where(Member.class).findAll()) {
            members.add(dbmember.copyFromRealm(c));
        }
        dbmember.close();
        FirebaseDatabase.getInstance().getReference()
                .child(MEMBER)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        members.clear();
                        final Realm dbmember = Realm.getDefaultInstance();
                        dbmember.executeTransaction(realm ->
                                members.addAll(insertMembersFromFirebase(dataSnapshot,
                                        realm)));
                        dbmember.close();
                    }

                    @Override
                    public void onCancelled(final DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
                    }
                });

        FirebaseDatabase.getInstance().getReference(CAMPUS_WATCH).keepSynced(true);
        FirebaseDatabase.getInstance().getReference(CAMPUS_WATCH)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        final Realm db = Realm.getDefaultInstance();
                        db.executeTransaction(realm -> saveFirebaseData(dataSnapshot, realm));
                        db.close();
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onCancelled(final DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
        final Section selectedSection;
        switch (menuItem.getItemId()) {
            case R.id.bottom_herald:
                selectedSection = Section.HERALD;
                break;

            case R.id.bottom_issues:
                selectedSection = Section.ISSUES;
                break;

            case R.id.bottom_videos:
                selectedSection = Section.VIDEOS;
                break;

            case R.id.bottom_events:
                selectedSection = Section.EVENTS;
                break;

            case R.id.bottom_utilities:
                selectedSection = Section.UTILITIES;
                break;

            default:
                return onNavigationItemSelected(menuItem);
        }
        homeVm.getCurrentSection().setValue(selectedSection);
        return true;
    }

    @Override
    protected void onPause() {
        invalidateOptionsMenu();
        super.onPause();
    }
}
