package com.csatimes.dojma;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import io.realm.RealmList;

public class PhotoPagerAdapter
        extends RecyclePagerAdapter<PhotoPagerAdapter.ViewHolder> {

    private static final long PROGRESS_DELAY = 300L;

    private final ViewPager viewPager;
    private RealmList<HeraldNewsItemFormat> realmList;
    private GestureSettingsSetupListener setupListener;
    private Context context;

    private boolean activated;

    public PhotoPagerAdapter(Context context, ViewPager viewPager, RealmList<HeraldNewsItemFormat>
            realmList) {
        this.context = context;
        this.realmList = realmList;
        this.viewPager = viewPager;
    }

    public static GestureImageView getImage(RecyclePagerAdapter.ViewHolder holder) {
        return ((ViewHolder) holder).image;
    }

    public void setRealmList(RealmList<HeraldNewsItemFormat> realmList) {
        this.realmList = realmList;
        notifyDataSetChanged();
    }

    public HeraldNewsItemFormat getRealmList(int pos) {
        return realmList == null || pos < 0 || pos >= realmList.size() ? null : realmList.get(pos);
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
        return !activated || realmList == null ? 0 : realmList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container) {
        final ViewHolder holder = new ViewHolder(container);
        holder.image.getController().getSettings().setFillViewport(true).setMaxZoom(3f);
        holder.image.getController().enableScrollInViewPager(viewPager);
        holder.image.getPositionAnimator().addPositionUpdateListener(
                new ViewPositionAnimator.PositionUpdateListener() {
                    @Override
                    public void onPositionUpdate(float state, boolean isLeaving) {
                        holder.progress.setVisibility(state == 1f ? View.VISIBLE : View.INVISIBLE);
                    }
                });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (setupListener != null) {
            setupListener.onSetupGestureView(holder.image);
        }
        Log.e("TAG", "onBindPager");
        // Temporary disabling touch controls
        if (!holder.gesturesDisabled) {
            holder.image.getController().getSettings().disableGestures();
            holder.gesturesDisabled = true;
        }
        holder.progress.setVisibility(View.VISIBLE);
        holder.progress.animate().setStartDelay(PROGRESS_DELAY).alpha(1f);

        HeraldNewsItemFormat item = realmList.get(position);
        Log.e("TAG", "calling picasso downloader");
        if (true/*!DHC.doesImageExists(item.getPostID())*/) {
            Picasso.with(context).load(item.getImageURL()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Log.e("TAG", "success in bitmap");
                    final int pos = position;
                    DHC.saveImage(bitmap, realmList.get(pos).getPostID());
                    holder.progress.animate().cancel();
                    holder.progress.animate().alpha(0f);
                    // Re-enabling touch controls
                    if (holder.gesturesDisabled) {
                        holder.image.getController().getSettings().enableGestures();
                        holder.image.getController().getSettings().setFitMethod(Settings.Fit.INSIDE);
                        holder.gesturesDisabled = false;
                    }
                    holder.image.setImageBitmap(bitmap);
                    holder.progress.animate().alpha(0f);
                    holder.progress.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                    holder.progress.animate().alpha(0f);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        } else {
            holder.progress.animate().cancel();
            holder.progress.animate().alpha(0f);
            // Re-enabling touch controls
            if (holder.gesturesDisabled) {
                holder.image.getController().getSettings().enableGestures();
                holder.image.getController().getSettings().setFitMethod(Settings.Fit.INSIDE);
                holder.gesturesDisabled = false;
            }
            holder.image.setImageBitmap(DHC.decodeSampledBitmapFromFile((DHC.directory + item
                    .getPostID() + ".jpeg"), 100, 100));
            holder.progress.animate().alpha(0f);
            Log.e("TAG", DHC.directory + "/" + item.getPostID() + ".jpeg");
            holder.progress.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public void onRecycleViewHolder(@NonNull ViewHolder holder) {
        super.onRecycleViewHolder(holder);

        if (holder.gesturesDisabled) {
            holder.image.getController().getSettings().enableGestures();
            holder.gesturesDisabled = false;
        }
        holder.progress.animate().cancel();
        holder.progress.setAlpha(0f);

        holder.image.setImageDrawable(null);
    }

    static class ViewHolder extends RecyclePagerAdapter.ViewHolder {
        final GestureImageView image;
        final View progress;

        boolean gesturesDisabled;

        ViewHolder(ViewGroup parent) {
            super(Views.inflate(parent, R.layout.item_photo_full));
            image = Views.find(itemView, R.id.photo_full_image);
            progress = Views.find(itemView, R.id.photo_full_progress);
        }
    }

}
