package com.csatimes.dojma.aboutapp;

import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Person;
import com.csatimes.dojma.utilities.Browser;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Rushikesh Jogdand.
 */
public class PersonVh extends RecyclerView.ViewHolder {
    private final TextView nameTv;
    private final TextView phoneTv;
    private final TextView emailTv;
    private final SimpleDraweeView avatarIv;
    private final Browser browser;

    public PersonVh(final View itemView, @NonNull final Browser browser) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.tv_vh_developer_name);
        avatarIv = itemView.findViewById(R.id.iv_avatar);
        phoneTv = itemView.findViewById(R.id.tv_vh_developer_phone);
        emailTv = itemView.findViewById(R.id.tv_vh_developer_email);
        this.browser = browser;
    }

    public void populate(@NonNull final Person person) {
        nameTv.setText(person.name);
        emailTv.setText(person.email);
        phoneTv.setText(person.phone);
        avatarIv.setImageURI(person.imageUrl);
        if (person.homePage != null) {
            itemView.setOnClickListener(view -> browser.launchUrl(person.homePage));
        }
    }
}
