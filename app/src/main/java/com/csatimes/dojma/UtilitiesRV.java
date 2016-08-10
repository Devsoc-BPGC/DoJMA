package com.csatimes.dojma;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesRV extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    boolean hasWritePermission = false;
    Activity activity;
    Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UtilitiesRV(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        sharedPreferences=context.getSharedPreferences(DHC.USER_PREFERENCES,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.viewholder_utilities_contact, parent, false);
                viewHolder = new UtilitiesViewHolder1(v1, parent.getContext());
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.viewholder_utilities_mess_menu, parent, false);
                viewHolder = new UtilitiesViewHolder2(v2, parent.getContext(), hasWritePermission, activity);
                break;
            case 2:
                View v3 = inflater.inflate(R.layout.viewholder_utilities_links, parent, false);
                viewHolder = new UtilitiesViewHolder3(v3, parent.getContext());
                break;
            case 3:
                View v4 = inflater.inflate(R.layout.viewholder_utilities_misc, parent, false);
                viewHolder = new UtilitiesViewHolder4(v4);
                break;
            case 4:
                View v5 = inflater.inflate(R.layout.viewholder_utilities_map, parent, false);
                viewHolder = new UtilitiesViewHolder5(v5, parent.getContext());
                break;


        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 3) {
            final UtilitiesViewHolder4 vh = (UtilitiesViewHolder4) holder;
            DatabaseReference miscRef = FirebaseDatabase.getInstance().getReference().child("miscCard");
            vh.text.setText(sharedPreferences.getString("misc",context.getString(R.string.UTILITIES_MISC_text)));

            miscRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    vh.text.setText(dataSnapshot.getValue(String.class));

                    editor.putString("misc",vh.text.getText().toString());
                    editor.apply();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    vh.text.setText(sharedPreferences.getString("misc","-"));
                }
            });
        }
    }




    @Override
    public int getItemCount() {
        return 5;
    }


    public void setHasWritePermission(boolean hasWritePermission) {
        this.hasWritePermission = hasWritePermission;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else if (position == 1)
            return 1;
        else if (position == 2)
            return 2;
        else if (position == 3)
            return 3;
        else return 4;
    }
}
