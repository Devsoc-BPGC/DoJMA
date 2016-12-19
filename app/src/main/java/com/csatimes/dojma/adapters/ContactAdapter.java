package com.csatimes.dojma.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.viewholders.SimpleTextViewHolder;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Vikramaditya Kukreja on 20-07-2016.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CONTACT = 1;
    private List<RealmList<ContactItem>> dataSet;
    private List<String> contactTypes;
    private SparseIntArray positions;
    private int lastTitlePos = 0;
    private OnContactItemClicked onContactItemClicked;

    public ContactAdapter(List<RealmList<ContactItem>> dataSet, List<String> contactTypes) {
        this.dataSet = dataSet;
        this.contactTypes = contactTypes;
        onContactItemClicked = null;
        generatePositions();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflate the custom layout
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == TYPE_TITLE)
            viewHolder = new SimpleTextViewHolder(inflater.inflate(R.layout.viewholder_simple_text, parent, false));
        else
            viewHolder = new ContactViewHolder(inflater.inflate(R.layout.item_format_contact, parent, false));

        return viewHolder;

    }

    public void generatePositions() {

        DHC.log("dataset size" + dataSet.size() + " contacttpe size " + contactTypes.size());

        for (int i = 0; i < dataSet.size(); i++) {
            DHC.log(i + 1 + " size is " + dataSet.get(i).size());
        }


        if (positions == null)
            positions = new SparseIntArray();
        else positions.clear();

        for (int i = 0; i < contactTypes.size(); i++) {
            int var = i;
            for (int j = 1; j <= i; j++) {
                var += dataSet.get(j - 1).size();
            }
            DHC.log("title at index " + var);
            positions.put(var, i);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DHC.log("position " + position);
        if (holder.getItemViewType() == TYPE_TITLE) {
            SimpleTextViewHolder stvh = (SimpleTextViewHolder) holder;
            stvh.text.setText(contactTypes.get(positions.get(position)));
            lastTitlePos = positions.get(position);
        } else {

            ContactViewHolder cvh = (ContactViewHolder) holder;
            int index = position - (lastTitlePos + 1);

            for (int i = 1; i <= lastTitlePos; i++) {
                index -= dataSet.get(i - 1).size();
            }
            DHC.log("contact index is " + index);

            cvh.contactName.setText(dataSet.get(lastTitlePos).get(index).getName());
        }


    }

    @Override
    public int getItemCount() {

        int count = contactTypes.size();

        for (int i = 0; i < contactTypes.size(); i++) {
            count += dataSet.get(i).size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) return TYPE_TITLE;

        if (positions.get(position) != 0)
            return TYPE_TITLE;

        return TYPE_CONTACT;
    }

    public void setOnContactItemClicked(OnContactItemClicked onContactItemClicked) {
        this.onContactItemClicked = onContactItemClicked;
    }

    public interface OnContactItemClicked {
        void onCallButtonClicked(String tel);

        void onEmailButtonClicked(String email);

        void onContactAddClicked(String name, String tel, String email);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contactName;
        ImageButton call;
        ImageButton add;
        ImageButton email;

        ContactViewHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.item_format_contact_name);
            call = (ImageButton) itemView.findViewById(R.id.item_format_contact_call);
            add = (ImageButton) itemView.findViewById(R.id.item_format_contact_add);
            email = (ImageButton) itemView.findViewById(R.id.item_format_contact_email);

            call.setOnClickListener(this);
            email.setOnClickListener(this);
            add.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            int index = getAdapterPosition() - (lastTitlePos + 1);
            for (int i = 1; i <= lastTitlePos; i++) {
                index -= dataSet.get(i - 1).size();
            }
            DHC.log("lastpos " + lastTitlePos);
            try {
                ContactItem ci = dataSet.get(lastTitlePos).get(index);

                if (onContactItemClicked != null) {
                    if (id == call.getId()) {
                        onContactItemClicked.onCallButtonClicked(ci.getNumber());

                    } else if (id == email.getId()) {
                        onContactItemClicked.onEmailButtonClicked(ci.getEmail());

                    } else if (id == email.getId()) {
                        onContactItemClicked.onContactAddClicked(ci.getName(), ci.getNumber(), ci.getEmail());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
