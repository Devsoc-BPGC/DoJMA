package com.csatimes.dojma;

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
    SimpleDraweeView amessSDV;
    SimpleDraweeView cmessSDV;
    SimpleDraweeView foodKingSDV;
    SimpleDraweeView persianCourtSDV;
    SimpleDraweeView gajaSDV;
    SimpleDraweeView icespiceSDV;
    View background;
    SimpleDraweeView large;
    GestureFrameLayout frame;

    Uri amessURI = Uri.parse("res://com.csatimes.dojma"
            + "/" + R.drawable.calm);
    Uri cmessURI = Uri.parse("res://com.csatimes.dojma"
            + "/" + R.drawable.calm);
    Uri foodKingURI = Uri.parse("res://com.csatimes.dojma"
            + "/" + R.drawable.calm);
    Uri persianCourtURI = Uri.parse("res://com.csatimes.dojma"
            + "/" + R.drawable.calm);
    Uri gajaURI = Uri.parse("res://com.csatimes.dojma"
            + "/" + R.drawable.calm);
    Uri icespiceURI = Uri.parse("res://com.csatimes.dojma"
            + "/" + R.drawable.calm);

    GestureSettingsMenu settingsMenu;
    Toolbar fake_toolbar;
    AppBarLayout fake_app_bar;
    boolean zoom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fake_toolbar = (Toolbar) findViewById(R.id.mess_fake_toolbar);
        fake_app_bar = (AppBarLayout) findViewById(R.id.mess_fake_appbar);
        setSupportActionBar(toolbar);


        amessSDV = (SimpleDraweeView) findViewById(R.id.mess_a_mess);
        cmessSDV = (SimpleDraweeView) findViewById(R.id.mess_c_mess);
        foodKingSDV = (SimpleDraweeView) findViewById(R.id.mess_foodking_menu);
        persianCourtSDV = (SimpleDraweeView) findViewById(R.id.mess_persian_menu);
        gajaSDV = (SimpleDraweeView) findViewById(R.id.mess_gaja_menu);
        icespiceSDV = (SimpleDraweeView) findViewById(R.id.mess_icespice_menu);
        background = findViewById(R.id.mess_black_background);
        large = (SimpleDraweeView) findViewById(R.id.mess_large_image);
        frame = (GestureFrameLayout) findViewById(R.id.mess_gesture_frame);

        amessSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        cmessSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        foodKingSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        persianCourtSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        gajaSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        icespiceSDV.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        settingsMenu = new GestureSettingsMenu();

        amessSDV.setOnClickListener(this);
        cmessSDV.setOnClickListener(this);
        foodKingSDV.setOnClickListener(this);
        persianCourtSDV.setOnClickListener(this);
        gajaSDV.setOnClickListener(this);
        icespiceSDV.setOnClickListener(this);

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
                onBackPressed();
            }
        });
        amessSDV.setImageURI(amessURI);
        cmessSDV.setImageURI(cmessURI);
        foodKingSDV.setImageURI(foodKingURI);
        persianCourtSDV.setImageURI(persianCourtURI);
        gajaSDV.setImageURI(gajaURI);
        icespiceSDV.setImageURI(icespiceURI);
    }

    @Override
    protected void onStart() {
        super.onStart();

        amess.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                amessSDV.setImageURI(uri);
                MessMenus.this.amessURI = uri;
                Toast.makeText(MessMenus.this, "Downloading all menus", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        cmess.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                cmessSDV.setImageURI(uri);
                MessMenus.this.cmessURI = uri;
            }
        });

        foodking.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                foodKingSDV.setImageURI(uri);
                MessMenus.this.foodKingURI = uri;
            }
        });

        persian.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                persianCourtSDV.setImageURI(uri);
                MessMenus.this.persianCourtURI = uri;
            }
        });

        icespice.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                icespiceSDV.setImageURI(uri);
                MessMenus.this.icespiceURI = uri;
            }
        });

        gaja.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                gajaSDV.setImageURI(uri);
                MessMenus.this.gajaURI = uri;
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
            large.setImageURI(amessURI);
            fake_toolbar.setVisibility(View.VISIBLE);
            fake_app_bar.setVisibility(View.VISIBLE);

        } else if (id == cmessSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);
            large.setImageURI(cmessURI);
            fake_app_bar.setVisibility(View.VISIBLE);


        } else if (id == foodKingSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            large.setImageURI(foodKingURI);
            fake_app_bar.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);

        } else if (id == persianCourtSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            large.setImageURI(persianCourtURI);
            fake_toolbar.setVisibility(View.VISIBLE);
            fake_app_bar.setVisibility(View.VISIBLE);

        } else if (id == icespiceSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);
            large.setImageURI(icespiceURI);
            fake_app_bar.setVisibility(View.VISIBLE);


        } else if (id == gajaSDV.getId()) {
            zoom = true;
            background.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
            fake_toolbar.setVisibility(View.VISIBLE);
            fake_app_bar.setVisibility(View.VISIBLE);

            large.setImageURI(gajaURI);

        }
    }
}
