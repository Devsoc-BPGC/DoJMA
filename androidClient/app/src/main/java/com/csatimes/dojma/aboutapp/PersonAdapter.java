package com.csatimes.dojma.aboutapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Person;
import com.csatimes.dojma.utilities.Browser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Rushikesh Jogdand.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonVh> {
    private final Browser browser;
    private final List<Person> personList = new ArrayList<>(0);

    public PersonAdapter(final List<Person> people, final Browser browser) {
        personList.clear();
        personList.addAll(people);
        this.browser = browser;
    }

    public void setPersonList(final List<Person> data) {
        personList.clear();
        personList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonVh onCreateViewHolder(@NonNull final ViewGroup parent,
                                       final int viewType) {
        return new PersonVh(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vh_person, parent, false),
                browser);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonVh holder,
                                 final int position) {
        holder.populate(personList.get(position));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
