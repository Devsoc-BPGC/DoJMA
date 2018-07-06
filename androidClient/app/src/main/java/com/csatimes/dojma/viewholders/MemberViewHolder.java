package com.csatimes.dojma.viewholders;

import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Member;
import com.csatimes.dojma.utilities.CustomTextViewRL;
import com.csatimes.dojma.utilities.CustomTextViewRR;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemberViewHolder extends RecyclerView.ViewHolder {

    private final CustomTextViewRR membernameTv;
    private final CustomTextViewRL memberphoneTv;
    private final CustomTextViewRL memberemailTv;
    private final CustomTextViewRL memberpostTv;
    private final SimpleDraweeView memberIv;
    public MemberViewHolder(View itemView) {
        super(itemView);
        memberpostTv = itemView.findViewById(R.id.tv_vh_member_post);
        membernameTv = itemView.findViewById(R.id.tv_vh_member_name);
        memberIv = itemView.findViewById(R.id.iv_member);
        memberphoneTv = itemView.findViewById(R.id.tv_vh_member_phone);
        memberemailTv = itemView.findViewById(R.id.tv_vh_member_email);
    }

    public void populate(@NonNull final Member member) {
        membernameTv.setText(member.name);
        memberemailTv.setText(member.email);
        memberphoneTv.setText(member.phone);
        memberpostTv.setText(member.post);
        memberIv.setImageURI(member.imageUrl);
    }
}
