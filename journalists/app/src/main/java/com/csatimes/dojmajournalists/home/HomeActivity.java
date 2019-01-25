package com.csatimes.dojmajournalists.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.csatimes.dojmajournalists.R;
import com.csatimes.dojmajournalists.campuswatch.AddCampusWatchActivity;
import com.csatimes.dojmajournalists.events.AddEventActivity;
import com.csatimes.dojmajournalists.events.DeleteEventActivity;
import com.csatimes.dojmajournalists.messmenu.UpdateMessMenuActivity;
import com.csatimes.dojmajournalists.notification.SendNotificationActivity;
import com.csatimes.dojmajournalists.video.AddVideoActivity;

import androidx.appcompat.app.AppCompatActivity;

import static com.csatimes.dojmajournalists.login.LoginActivity.checkLogin;
import static com.csatimes.dojmajournalists.messmenu.UpdateMessMenuActivity.launchMessUpdate;

public class HomeActivity extends AppCompatActivity {

    private View messOptionButtons;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.btn_add_event).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        AddEventActivity.class)));
        findViewById(R.id.btn_add_cw).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        AddCampusWatchActivity.class)));
        findViewById(R.id.btn_del_event).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        DeleteEventActivity.class)));
        findViewById(R.id.add_video).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        AddVideoActivity.class)));
        findViewById(R.id.send_notif).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        SendNotificationActivity.class)));
        messOptionButtons = findViewById(R.id.ll_mess_buttons);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLogin(this);
    }

    /**
     * {@link android.view.View.OnClickListener}s for {@link R.id#btn_a_mess},
     * {@link R.id#btn_c_mess} and {@link R.id#btn_d_mess}.
     *
     * @param view differentiates which mess update to launch.
     */
    public void updateMess(View view) {
        UpdateMessMenuActivity.Mess mess;
        switch (view.getId()) {
            case R.id.btn_a_mess: {
                mess = UpdateMessMenuActivity.Mess.A;
                break;
            }
            case R.id.btn_c_mess: {
                mess = UpdateMessMenuActivity.Mess.C;
                break;
            }
            case R.id.btn_d_mess: {
                mess = UpdateMessMenuActivity.Mess.D;
                break;
            }
            default: {
                return;
            }
        }
        launchMessUpdate(this, mess);
    }

    /**
     * {@link android.view.View.OnClickListener} for {@link R.id#btn_update_menu}
     *
     * @param view ignored.
     */
    public void toggleMessBtnVisibility(View view) {
        if (messOptionButtons.getVisibility() == View.GONE) {
            messOptionButtons.setVisibility(View.VISIBLE);
        } else {
            messOptionButtons.setVisibility(View.GONE);
        }
    }
}
