package com.csatimes.dojma;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Vikramaditya Kukreja on 07-06-2016.
 * DojmaHelper Class
 */
public class DHC {

    public static final String dojmaFolderName = "dojmaImages";
    public static final String ROOT_DIRECTORY = Environment.getExternalStorageDirectory()
            .toString();
    public static final String defaultImageURL = "-1";
    public static final String directory = ROOT_DIRECTORY + "/" + dojmaFolderName;
    public static final String USER_PREFERENCES = "USER_PREFS";
    public static final String REALM_DOJMA_DATABASE = "DOJMA_DATABASE";
    public static final String REALM_DOJMA_LINKS_DATABASE = "DOJMA_LINKS_DATABASE";
    static final String FILTER_SUFFIX = "filter_";
    static final String SORT_SUFFIX = "sort_";
    public static int NUMBEROFPAGES = 4;
    public static int PAGENUMBER = 0;
    public static String address = "http://csatimes.co.in/dojma/";
    public static int noOfArticlesPerPage = 16;
    public static int UPDATE_SERVICE_PENDING_INTENT_CODE = 243;
    public static int UPDATE_SERVICE_NOTIFICATION_CODE = 42;
    public static final String eventsAddress= "https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/DoJMA-Assets-Repo/master/Events/events.txt";

    // DP <-> PX static converter method
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int blendColors(int from, int to, float ratio) {
        final float inverseRation = 1f - ratio;
        final float r = Color.red(from) * ratio + Color.red(to) * inverseRation;
        final float g = Color.green(from) * ratio + Color.green(to) * inverseRation;
        final float b = Color.blue(from) * ratio + Color.blue(to) * inverseRation;
        return Color.rgb((int) r, (int) g, (int) b);
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


    //This method takes in a bitmap and the unique post id
    //It stores the image to the ROOT_DIRECTORY directory and the folder you specify
    //image is store in webp format
    public static void saveImage(Bitmap finalBitmap, String id) {

        File imageDownloadFolder = new File(directory + "/");
        imageDownloadFolder.mkdirs();
        String downloadedImageName = id + ".jpeg";
        File file = new File(imageDownloadFolder, downloadedImageName);
        if (!file.exists())
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    //This method takes in unique postid checks whether that image is available offline or not.
    public static boolean doesImageExists(String id) {
        File imageDownloadFolder = new File(ROOT_DIRECTORY + "/" + dojmaFolderName +
                "/");
        imageDownloadFolder.mkdirs();
        String downloadedImageName = id + ".jpeg";
        File file = new File(imageDownloadFolder, downloadedImageName);
        return file.exists();
    }
}
