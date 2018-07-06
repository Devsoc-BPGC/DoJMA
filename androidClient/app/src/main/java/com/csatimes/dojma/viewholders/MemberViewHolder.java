package com.csatimes.dojma.viewholders;

import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Member;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemberViewHolder extends RecyclerView.ViewHolder {

    private final TextView membernameTv;
    private final TextView memberphoneTv;
    private final TextView memberemailTv;
    private final SimpleDraweeView memberIv;
    public MemberViewHolder(View itemView) {
        super(itemView);
        membernameTv = itemView.findViewById(R.id.tv_vh_developer_name);
        memberIv = itemView.findViewById(R.id.iv_avatar);
        memberphoneTv = itemView.findViewById(R.id.tv_vh_developer_phone);
        memberemailTv = itemView.findViewById(R.id.tv_vh_developer_email);
    }

    public void populate(@NonNull final Member member) {
        membernameTv.setText(member.name);
        memberemailTv.setText(member.email);
        memberphoneTv.setText(member.phone);
        memberIv.setImageURI(member.imageUrl);
    }
}
