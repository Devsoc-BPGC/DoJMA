package com.csatimes.dojma.activities;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.csatimes.dojma.R;
import com.csatimes.dojma.aboutapp.AboutAppActivity;
import com.csatimes.dojma.favorites.FavouritesFragment;
import com.csatimes.dojma.fragments.EventsFragment;
import com.csatimes.dojma.fragments.Utilities;
import com.csatimes.dojma.herald.HeraldFragment;
import com.csatimes.dojma.issues.IssuesFragment;
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
import io.realm.Realm;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.EXTRA_TEXT;
import static com.csatimes.dojma.models.Person.FIELD_NAME;
import static com.csatimes.dojma.utilities.DHC.MIME_TYPE_PLAINTEXT;
import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES;
import static com.csatimes.dojma.utilities.FirebaseKeys.CONTRIB;

@SuppressLint("GoogleAppIndexingApiWarning")
public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = TAG_PREFIX + MainActivity.class.getSimpleName();
    private final List<Person> contributors = new ArrayList<>(0);
    private final Realm realm = Realm.getDefaultInstance();

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
                intent = new Intent(this, AboutDojmaActivity.class);
                break;
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
            case R.id.action_settings: {
                intent = new Intent(this, SettingsActivity.class);
                break;
            }
            case R.id.action_search: {
                intent = new Intent(this, SearchableActivity.class);
                break;
            }
            case R.id.action_shorts:
                intent = new Intent(this, ShortsActivity.class);
                startActivity(intent);
                return true;
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
        startActivity(intent);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final SharedPreferences mPreferences = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);

        if (mPreferences.getBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), true)) {
            final Intent postDlIntent = new Intent(this,
                    POSTDownloaderActivity.class);
            startActivity(postDlIntent);
            finish();
        }

        final Toolbar mToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_container, new HeraldFragment())
                .commit();

        final BottomNavigationView homeBottomNav = findViewById(R.id.nav_view);
        homeBottomNav.setOnNavigationItemSelectedListener(this);
        contributors.clear();
        for (final Person c : realm.where(Person.class).findAll()) {
            if (c.isContributor) {
                contributors.add(realm.copyFromRealm(c));
            }
        }
        FirebaseDatabase.getInstance().getReference()
                .child(CONTRIB)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        realm.executeTransaction(realm -> {
                            contributors.clear();
                            for (final DataSnapshot child :
                                    dataSnapshot.getChildren()) {
                                final Person person = child.getValue(Person.class);
                                final Person oldValue = realm.where(Person.class)
                                        .equalTo(FIELD_NAME, person.name)
                                        .findFirst();
                                if (oldValue != null) {
                                    person.postName = oldValue.postName;
                                }
                                person.isContributor = true;
                                realm.insertOrUpdate(person);
                                contributors.add(person);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(final DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
        final Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.bottom_herald: {
                fragment = new HeraldFragment();
                break;
            }
            case R.id.bottom_issues: {
                fragment = new IssuesFragment();
                break;
            }
            case R.id.bottom_favourites: {
                fragment = new FavouritesFragment();
                break;
            }
            case R.id.bottom_events: {
                fragment = new EventsFragment();
                break;
            }
            case R.id.bottom_utilities: {
                fragment = new Utilities();
                break;
            }
            default: {
                return onNavigationItemSelected(menuItem);
            }
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_container, fragment)
                .commit();
        return true;
    }
}
