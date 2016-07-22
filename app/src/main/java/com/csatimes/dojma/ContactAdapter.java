package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Vikramaditya Kukreja on 20-07-2016.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    ContactItem[] array;
    Context context;

    public ContactAdapter(Context context, ContactItem[] array) {
        this.array = array;
        this.context = context;
        Log.e("TAG", this.array.length + " length ");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflate the custom layout
        View contact_format = inflater.inflate(R.layout.utilities_contact_format, parent, false);
        // Return a new holder instance
        return new ViewHolder(contact_format);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (getItemCount() != 0 && position == getItemCount() - 1) {
            holder.divider.setVisibility(View.GONE);
        }

        holder.contactName.setText(array[position].getName());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + array[holder.getAdapterPosition()].getNumber()));
                context.startActivity(intent);

            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, array[holder.getAdapterPosition()].getName());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, array[holder.getAdapterPosition()].getNumber());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        ImageButton call;
        ImageButton add;
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.utilities_adapter_format_name);
            call = (ImageButton) itemView.findViewById(R.id.utitlities_adapter_format_call);
            add = (ImageButton) itemView.findViewById(R.id.utitlities_adapter_format_add);
            divider = itemView.findViewById(R.id.view_horizontal);
        }
    }
}
