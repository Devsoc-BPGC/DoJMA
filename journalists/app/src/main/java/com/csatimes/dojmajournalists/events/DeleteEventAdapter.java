package com.csatimes.dojmajournalists.events;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csatimes.dojmajournalists.R;
import com.csatimes.dojmajournalists.home.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DeleteEventAdapter extends RecyclerView.Adapter<DeleteEventAdapter.ViewHolder> {
    private List<EventModel> listItems;
    private Context context;


    public DeleteEventAdapter(List<EventModel> eventList, Context context) {
        this.listItems = eventList;
        this.context = context;
    }

    @Override
    public DeleteEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_recycler, parent, false);
        return new DeleteEventAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DeleteEventAdapter.ViewHolder holder, int position) {
        final EventModel listItem = listItems.get(position);
        holder.titleTv.setText(listItem.title);
        holder.deleteBtn.setOnClickListener(view -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query applesQuery = ref.child("events2").orderByChild("title").equalTo(listItem.title);

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                        notifyItemRemoved(position);
                        Intent i = new Intent(context, HomeActivity.class);
                        context.startActivity(i);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public Button deleteBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.event_name);
            deleteBtn = itemView.findViewById(R.id.delete_but);
        }
    }
}
