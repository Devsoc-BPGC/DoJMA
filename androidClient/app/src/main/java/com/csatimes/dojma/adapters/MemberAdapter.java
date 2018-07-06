package com.csatimes.dojma.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Member;
import com.csatimes.dojma.viewholders.MemberViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder> {

    private final List<Member> memberList = new ArrayList<>(0);

    public MemberAdapter(final List<Member> members) {
        memberList.clear();
        memberList.addAll(members);
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vh_person, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        holder.populate(memberList.get(position));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }
}
