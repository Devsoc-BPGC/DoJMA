package com.csatimes.dojma.issues;

import android.content.Intent;
import android.view.View;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.HeraldItem;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Rushikesh Jogdand.
 */
public class IssueVh extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final View rootView;
    private final MaterialButton titleMb;
    private String title;

    @SuppressWarnings("WeakerAccess")
    public IssueVh(@NonNull final View itemView) {
        super(itemView);
        rootView = itemView;
        titleMb = rootView.findViewById(R.id.mb_title);
    }

    void populate(@NonNull final String item) {
        this.title = item;
        titleMb.setText(title);
        rootView.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        final Intent intent = new Intent(view.getContext(), IssuesActivity.class);
        intent.putExtra(HeraldItem.CATEGORY, title);
        view.getContext().startActivity(intent);
    }
}
