package com.csatimes.dojma;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MessMenus extends AppCompatActivity implements View.OnClickListener {
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference amess = firebaseStorage.getReference().child("images").child("mess").child
            ("amess.jpg");

    StorageReference cmess = firebaseStorage.getReference().child("images").child("mess").child
            ("cmess.jpg");

    StorageReference foodking = firebaseStorage.getReference().child("images").child("mess").child
            ("foodking.jpg");

    StorageReference persian = firebaseStorage.getReference().child("images").child("mess").child
            ("persian.jpg");

    StorageReference icespice = firebaseStorage.getReference().child("images").child("mess").child
            ("icespice.jpg");

    StorageReference gaja = firebaseStorage.getReference().child("images").child("mess").child
            ("gaja.jpg");
    StorageReference mongi = firebaseStorage.getReference().child("images").child("mess").child
            ("mongi.jpg");
    StorageReference ccd = firebaseStorage.getReference().child("images").child("mess").child
            ("ccd.jpg");

    SimpleDraweeView amessSDV;
    SimpleDraweeView cmessSDV;
    SimpleDraweeView foodKingSDV;
    SimpleDraweeView persianCourtSDV;
    SimpleDraweeView gajaSDV;
    SimpleDraweeView icespiceSDV;
    SimpleDraweeView mongiSDV;
    SimpleDraweeView ccdSDV;
    View background;
    SimpleDraweeView large;
    GestureFrameLayout frame;

    String sprefamessName = "amess";
    String sprefcmessName = "cmess";
    String spreffoodkingName = "foodking";
    String sprefpersianName = "persian";
    String spreficespiceName = "icespice";
    String sprefgajaName = "gaja";
    String sprefmongi = "mongi";
    String sprefccd="ccd";


    String keepCalm = "res://com.csatimes.dojma"
            + "/" + R.drawable.calm;
    Toolbar fake_toolbar;
    AppBarLayout fake_app_bar;
    boolean zoom = false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fake_toolbar = (Toolbar) findViewById(R.id.mess_fake_toolbar);
        fake_app_bar = (AppBarLayout) findViewById(R.id.mess_fake_appbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        amessSDV = (SimpleDraweeView) findViewById(R.id.mess_a_mess);
        cmessSDV = (SimpleDraweeView) findViewById(R.id.mess_c_mess);
        foodKingSDV = (SimpleDraweeView) findViewById(R.id.mess_foodking_menu);
        persianCourtSDV = (SimpleDraweeView) findViewById(R.id.mess_persian_menu);
        gajaSDV = (SimpleDraweeView) findViewById(R.id.mess_gaja_menu);
        icespiceSDV = (SimpleDraweeView) findViewById(R.id.mess_icespice_menu);
        mongiSDV = (SimpleDraweeView) findViewById(R.id.mess_mongi_menu);
        ccdSDV=(SimpleDraweeView) findViewById(R.id.mess_ccd_menu);
        background = findViewById(R.id.mess_black_background);
        large = (SimpleDraweeView) findViewById(R.id.mess_large_image);
        frame = (GestureFrameLayout) findViewById(R.id.mess_gesture_frame);

        amessSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        cmessSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        foodKingSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        persianCourtSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        gajaSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        icespiceSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        mongiSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        ccdSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());

        amessSDV.setOnClickListener(this);
        cmessSDV.setOnClickListener(this);
        foodKingSDV.setOnClickListener(this);
        persianCourtSDV.setOnClickListener(this);
        gajaSDV.setOnClickListener(this);
        icespiceSDV.setOnClickListener(this);
        mongiSDV.setOnClickListener(this);
        ccdSDV.setOnClickListener(this);
        fake_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        fake_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessMenus.super.onBackPressed();
            }
        });
        com.alexvasilkov.gestures.Settings settingsMenu = frame.getController().getSettings();

        settingsMenu.setZoomEnabled(true);
        settingsMenu.setOverzoomFactor(2f);
        settingsMenu.setMaxZoom(5f);
        settingsMenu.setOverscrollDistance(this, 0, 0);
        settingsMenu.setRestrictBounds(true);
        settingsMenu.setRestrictRotation(true);


        amessSDV.setImageURI(Uri.parse(sharedPreferences.getString(sprefamessName, keepCalm)));
        cmessSDV.setImageURI(Uri.parse(sharedPreferences.getString(sprefcmessName, keepCalm)));
        foodKingSDV.setImageURI(Uri.parse(sharedPreferences.getString(spreffoodkingName, keepCalm)));
        persianCourtSDV.setImageURI(Uri.parse(sharedPreferences.getString(sprefpersianName, keepCalm)));
        gajaSDV.setImageURI(Uri.parse(sharedPreferences.getString(sprefgajaName, keepCalm)));
        icespiceSDV.setImageURI(Uri.parse(sharedPreferences.getString(spreficespiceName, keepCalm)));
        mongiSDV.setImageURI(Uri.parse(sharedPreferences.getString(sprefmongi, keepCalm)));
        ccdSDV.setImageURI(Uri.parse(sharedPreferences.getString(sprefccd,keepCalm)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        amess.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                amessSDV.setImageURI(uri);
                editor.putString(sprefamessName, uri.toString());
                editor.apply();
                Toast.makeText(MessMenus.this, "Checking for updates", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        cmess.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                editor.putString(sprefcmessName, uri.toString());
                editor.apply();
                cmessSDV.setImageURI(uri);
            }
        });

        foodking.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                foodKingSDV.setImageURI(uri);
                editor.putString(spreffoodkingName, uri.toString());
                editor.apply();
            }
        });

        persian.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                persianCourtSDV.setImageURI(uri);
                editor.putString(sprefpersianName, uri.toString());
                editor.apply();
            }
        });

        icespice.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                icespiceSDV.setImageURI(uri);

                editor.putString(spreficespiceName, uri.toString());
                editor.apply();
            }
        });

        gaja.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                gajaSDV.setImageURI(uri);

                editor.putString(sprefgajaName, uri.toString());
                editor.apply();
            }
        });

        mongi.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mongiSDV.setImageURI(uri);

                editor.putString(sprefmongi, uri.toString());
                editor.apply();
            }
        });

        ccd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ccdSDV.setImageURI(uri);

                editor.putString(sprefccd, uri.toString());
                editor.apply();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        zoom = false;
        background.setVisibility(View.GONE);
        frame.setVisibility(View.GONE);
        fake_app_bar.setVisibility(View.GONE);
        fake_toolbar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (zoom) {
            background.setVisibility(View.GONE);
            frame.setVisibility(View.GONE);
            fake_app_bar.setVisibility(View.GONE);
            fake_toolbar.setVisibility(View.GONE);
            zoom = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == amessSDV.getId()) {

            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            large.setImageURI(Uri.parse(sharedPreferences.getString(sprefamessName, keepCalm)));
            fake_toolbar.setVisibility(View.VISIBLE);
            fake_app_bar.setVisibility(View.VISIBLE);

        } else if (id == cmessSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);
            large.setImageURI(Uri.parse(sharedPreferences.getString(sprefcmessName, keepCalm)));
            fake_app_bar.setVisibility(View.VISIBLE);


        } else if (id == foodKingSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            large.setImageURI(Uri.parse(sharedPreferences.getString(spreffoodkingName, keepCalm)));
            fake_app_bar.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);

        } else if (id == persianCourtSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            large.setImageURI(Uri.parse(sharedPreferences.getString(sprefpersianName, keepCalm)));
            fake_toolbar.setVisibility(View.VISIBLE);
            fake_app_bar.setVisibility(View.VISIBLE);

        } else if (id == icespiceSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);
            large.setImageURI(Uri.parse(sharedPreferences.getString(spreficespiceName, keepCalm)));
            fake_app_bar.setVisibility(View.VISIBLE);


        } else if (id == gajaSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);
            fake_app_bar.setVisibility(View.VISIBLE);

            large.setImageURI(Uri.parse(sharedPreferences.getString(sprefgajaName, keepCalm)));

        } else if (id == mongiSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);
            fake_app_bar.setVisibility(View.VISIBLE);

            large.setImageURI(Uri.parse(sharedPreferences.getString(sprefmongi, keepCalm)));

        }

        else if (id == ccdSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);
            fake_app_bar.setVisibility(View.VISIBLE);

            large.setImageURI(Uri.parse(sharedPreferences.getString(sprefccd, keepCalm)));

        }
    }
}
