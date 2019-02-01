package com.csatimes.dojma.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ContactItem;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by vikramaditya on 17/1/17.
 */
public class ContactItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ImageButton contactAdd;
    private final Context context;
    public TextView contactName;
    public TextView contactSub1;
    public TextView contactSub2;
    public SimpleDraweeView contactIcon;
    public ContactItem contactItem;
    public ImageButton contactCall;
    public ImageButton contactEmail;

    public ContactItemViewHolder(View itemView, Context context) {
        super(itemView);
        contactName = itemView.findViewById(R.id.item_format_contact_name_tv);
        contactSub1 = itemView.findViewById(R.id.item_format_contact_sub_1_tv);
        contactSub2 = itemView.findViewById(R.id.item_format_contact_sub_2_tv);
        contactCall = itemView.findViewById(R.id.item_format_contact_call_imgbtn);
        contactAdd = itemView.findViewById(R.id.item_format_contact_add_contact_imgbtn);
        contactEmail = itemView.findViewById(R.id.item_format_contact_email_imgbtn);
        contactIcon = itemView.findViewById(R.id.item_format_contact_icon_img);

        this.context = context;

        contactCall.setOnClickListener(this);
        contactEmail.setOnClickListener(this);
        contactAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == contactCall.getId()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactItem.number));
            context.startActivity(intent);

        } else if (id == contactEmail.getId()) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contactItem.email));
            context.startActivity(Intent.createChooser(intent, "Send Email"));

        } else if (id == contactAdd.getId()) {
            // see https://developer.android.com/training/contacts-provider/modify-data.html for more.
            Intent addContactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
            addContactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, contactItem.name);
            addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, contactItem.number);
            addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
            addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, contactItem.email);
            addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
            context.startActivity(addContactIntent);
        }

    }
}
