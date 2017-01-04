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
import com.csatimes.dojma.viewholders.SimpleTextViewHolder;

import java.util.List;

import io.realm.RealmList;

/**
 * Contacts adapter used in {@link com.csatimes.dojma.ContactsActivity}. This adapter takes in a {@link List}<{@link RealmList}<{@link ContactItem}>>
 * dataset object
 * and a {@link List}<{@link String}> contactTypes object
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_TITLE = 0;
    private final int TYPE_CONTACT = 1;
    private List<RealmList<ContactItem>> mDataSet;
    private List<String> mContactTypes;
    private SparseIntArray mPositions;
    private int mLastTitlePos = 0;
    private OnContactItemClicked mOnContactItemClicked;

    public ContactAdapter(List<RealmList<ContactItem>> mDataSet, List<String> mContactTypes) {
        this.mDataSet = mDataSet;
        this.mContactTypes = mContactTypes;
        mOnContactItemClicked = null;
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
        if (mPositions == null)
            mPositions = new SparseIntArray();
        else mPositions.clear();

        for (int i = 0; i < mContactTypes.size(); i++) {
            int var = i;
            for (int j = 1; j <= i; j++) {
                var += mDataSet.get(j - 1).size();
            }
            mPositions.put(var, i);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_TITLE) {
            SimpleTextViewHolder stvh = (SimpleTextViewHolder) holder;
            stvh.text.setText(mContactTypes.get(mPositions.get(position)));
            mLastTitlePos = mPositions.get(position);
        } else {

            ContactViewHolder cvh = (ContactViewHolder) holder;
            int index = position - (mLastTitlePos + 1);

            for (int i = 1; i <= mLastTitlePos; i++) {
                index -= mDataSet.get(i - 1).size();
            }
            cvh.contactName.setText(mDataSet.get(mLastTitlePos).get(index).getName());
        }


    }

    @Override
    public int getItemCount() {

        int count = mContactTypes.size();

        for (int i = 0; i < mContactTypes.size(); i++) {
            count += mDataSet.get(i).size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) return TYPE_TITLE;

        if (mPositions.get(position) != 0)
            return TYPE_TITLE;

        return TYPE_CONTACT;
    }

    public void setOnContactItemClicked(OnContactItemClicked onContactItemClicked) {
        this.mOnContactItemClicked = onContactItemClicked;
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

            int itemPositionInAdapter = getAdapterPosition();
            int positionsIndex = 0;
            int i;
            for (i = 0; i < mPositions.size(); i++) {
                if (mPositions.keyAt(i) < itemPositionInAdapter) {
                    positionsIndex = mPositions.keyAt(i);
                } else break;
            }
            i--;

            ContactItem ci = mDataSet.get(i).get(itemPositionInAdapter - 1 - positionsIndex);

            if (mOnContactItemClicked != null) {
                if (id == call.getId()) {
                    mOnContactItemClicked.onCallButtonClicked(ci.getNumber());

                } else if (id == email.getId()) {
                    mOnContactItemClicked.onEmailButtonClicked(ci.getEmail());

                } else if (id == add.getId()) {
                    mOnContactItemClicked.onContactAddClicked(ci.getName(), ci.getNumber(), ci.getEmail());
                }
            }

        }
    }
}
