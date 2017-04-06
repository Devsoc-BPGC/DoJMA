package com.csatimes.dojma.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ContactItem;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by vikramaditya on 17/1/17.
 */
public class ContactItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView contactName;
    public TextView contactSub1;
    public TextView contactSub2;
    public SimpleDraweeView contactIcon;
    public ContactItem contactItem;
    public ImageButton contactCall;
    public ImageButton contactEmail;
    private ImageButton contactAdd;
    private Context context;

    public ContactItemViewHolder(View itemView, Context context) {
        super(itemView);
        contactName = (TextView) itemView.findViewById(R.id.item_format_contact_name_tv);
        contactSub1 = (TextView) itemView.findViewById(R.id.item_format_contact_sub_1_tv);
        contactSub2 = (TextView) itemView.findViewById(R.id.item_format_contact_sub_2_tv);
        contactCall = (ImageButton) itemView.findViewById(R.id.item_format_contact_call_imgbtn);
        contactAdd = (ImageButton) itemView.findViewById(R.id.item_format_contact_add_contact_imgbtn);
        contactEmail = (ImageButton) itemView.findViewById(R.id.item_format_contact_email_imgbtn);
        contactIcon = (SimpleDraweeView) itemView.findViewById(R.id.item_format_contact_icon_img);

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
            intent.setData(Uri.parse("tel:" + contactItem.getNumber()));
            context.startActivity(intent);

        } else if (id == contactEmail.getId()) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contactItem.getEmail()));
            context.startActivity(Intent.createChooser(intent, "Send Email"));

        } else if (id == contactAdd.getId()) {
            /**
             * @see <a href = "https://developer.android.com/training/contacts-provider/modify-data.html">Modifying Data Documentation</a> for more
             */
            Intent addContactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
            addContactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, contactItem.getName());
            addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, contactItem.getNumber());
            addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
            addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, contactItem.getEmail());
            addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
            context.startActivity(addContactIntent);
        }

    }
}