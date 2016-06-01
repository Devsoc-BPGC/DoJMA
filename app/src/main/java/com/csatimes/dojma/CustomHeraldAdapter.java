package com.csatimes.dojma;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Vikramaditya Kukreja on 17-05-2016.
 */
public class CustomHeraldAdapter extends BaseAdapter {

    Context context;
    List<Herald.HeraldItemObject> itemsList;
    String directory = Herald.ROOT_DIRECTORY + "/" + Herald.dojmaFolderName;

    public CustomHeraldAdapter(Context context, List<Herald.HeraldItemObject> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    //This method returns final optimised bitmap
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    //Same method but for a bitmap file save in images directory
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    //This method is only used to optimise bitmap loading to imageview to avoid
    //memory overflow
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.herald_lv_item_format, null);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.herald_lv_item_title);
            holder.author = (TextView) view.findViewById(R.id.herald_lv_item_author);
            holder.date = (TextView) view.findViewById(R.id.herald_lv_item_date);
            holder.image = (ImageView) view.findViewById(R.id.herald_lv_item_image);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(itemsList.get(position).title);
        holder.author.setText(itemsList.get(position).author);
        holder.date.setText(itemsList.get(position).date.subSequence(0, 10));
        holder.image.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.drawable
                .no_image_info, HomeActivity.dpToPx(100), HomeActivity.dpToPx
                (100)));
        try {
            new LoadHolderImage(itemsList.get(position)).execute(holder.image).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;

    }

    //This method takes in a bitmap and the unique post id
    //It stores the image to the ROOT_DIRECTORY directory and the folder you specify
    //image is store in webp format
    private void SaveImage(Bitmap finalBitmap, String id) {

        File imageDownloadFolder = new File(Herald.ROOT_DIRECTORY + "/" + Herald.dojmaFolderName +
                "/");
        imageDownloadFolder.mkdirs();
        String downloadedImageName = id + ".jpeg";
        File file = new File(imageDownloadFolder, downloadedImageName);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap setImage(Context con, Herald.HeraldItemObject obj) {

        Bitmap bitmap = decodeSampledBitmapFromResource(con.getResources(), R.drawable
                .no_image_info, HomeActivity.dpToPx(100), HomeActivity.dpToPx
                (100));
        Bitmap defaultBitmap = bitmap;
        //Check if file exists

        File file = new File(directory, obj.postID + ".jpeg");

        if (file.exists()) {
            bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), HomeActivity.dpToPx(100),
                    HomeActivity.dpToPx(100));
            return bitmap;
        } else {
            if (obj.imageURL.compareTo("-1") == 0) {

                return defaultBitmap;
            } else {
                try {
                    Log.e("NEW", "Calling downloader");

                    bitmap = new DownloadImage().execute(obj.imageURL).get();
                    if (bitmap == null) {
                        Log.e("NEW", "download failed");

                        return defaultBitmap;

                    } else {
                        Log.e("NEW", "download success!. saving");
                        //Method to save downloaded image by its post id
                        SaveImage(bitmap, obj.postID);
                        Log.e("NEW", "saved");

                    }


                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();

                }
            }

        }

        return bitmap;
    }

    private static class ViewHolder {
        TextView title;
        TextView author;
        TextView date;
        ImageView image;
    }

    private class LoadHolderImage extends AsyncTask<ImageView, Void, Void> {

        Herald.HeraldItemObject foo;
        private ImageView v;
        private Bitmap bitmap;

        public LoadHolderImage(Herald.HeraldItemObject foo) {
            this.foo = foo;
            Log.e("NEW", "Async loader called");
        }

        @Override
        protected Void doInBackground(ImageView... params) {
            v = params[0];
            Log.e("NEW", "Calling image getter");
            bitmap = setImage(context, foo);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            v.setImageBitmap(bitmap);
        }
    }

    //Async method to download image from its link
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            if (params[0].compareTo("-1") == 0)
                return null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }


    }

}
