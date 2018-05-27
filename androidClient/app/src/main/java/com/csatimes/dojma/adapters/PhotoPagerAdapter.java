package com.csatimes.dojma.adapters;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.csatimes.dojma.R;
import com.csatimes.dojma.interfaces.GestureSettingsSetupListener;
import com.csatimes.dojma.models.PosterItem;
import com.csatimes.dojma.utilities.CircleImageDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import io.realm.RealmList;

public class PhotoPagerAdapter
        extends RecyclePagerAdapter<PhotoPagerAdapter.ViewHolder> {

    private static final long PROGRESS_DELAY = 300L;

    private final androidx.viewpager.widget.ViewPager viewPager;
    RealmList<PosterItem> posterItems;
    private GestureSettingsSetupListener setupListener;
    private boolean activated;

    public PhotoPagerAdapter(ViewPager viewPager, RealmList<PosterItem> posterItems) {
        this.posterItems = posterItems;
        this.viewPager = viewPager;
    }

    public static SimpleDraweeView getImage(RecyclePagerAdapter.ViewHolder holder) {
        if (holder instanceof ViewHolder) {
            return ((ViewHolder) holder).image;
        } else {
            return null;
        }
    }


    public void setSetupListener(GestureSettingsSetupListener listener) {
        setupListener = listener;
    }

    /**
     * To prevent ViewPager from holding heavy views (with bitmaps)  while it is not showing
     * we may just pretend there are no items in this adapter ("activate" = false).
     * But once we need to run opening animation we should "activate" this adapter again.<br/>
     * Adapter is not activated by default.
     */
    public void setActivated(boolean activated) {
        if (this.activated != activated) {
            this.activated = activated;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return !activated || posterItems == null ? 0 : posterItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container) {
        final ViewHolder holder = new ViewHolder(container);
        holder.gestureFrameLayout.getController().getSettings().setFillViewport(true).setMaxZoom(3f).setOverzoomFactor(1.5f);
        holder.gestureFrameLayout.getController().enableScrollInViewPager(viewPager);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.image.setImageURI(Uri.parse(posterItems.get(position).getUrl()));

    }

    @Override
    public void onRecycleViewHolder(@NonNull ViewHolder holder) {
        super.onRecycleViewHolder(holder);

        if (holder.gesturesDisabled) {
            holder.gestureFrameLayout.getController().getSettings().enableGestures();
            holder.gesturesDisabled = false;
        }
    }

    static class ViewHolder extends RecyclePagerAdapter.ViewHolder {
        final SimpleDraweeView image;
        final View progress;
        final GestureFrameLayout gestureFrameLayout;
        boolean gesturesDisabled;

        ViewHolder(ViewGroup parent) {
            super(Views.inflate(parent, R.layout.item_photo_full));
            image = Views.find(itemView, R.id.photo_full_image);
            progress = Views.find(itemView, R.id.photo_full_progress);
            gestureFrameLayout = Views.find(itemView, R.id.gesture_frame);
            gestureFrameLayout.getController().getSettings().setOverscrollDistance(0, 0);
            image.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        }
    }

}
