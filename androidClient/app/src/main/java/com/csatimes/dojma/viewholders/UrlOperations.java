package com.csatimes.dojma.viewholders;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rushikesh Jogdand.
 */
public class UrlOperations {
    private static final String TAG = UrlOperations.class.getSimpleName();

    public static Map<String, String> getQueryMap(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(TAG, String.format("urlString = %s, error = %s", urlString, e.getMessage()), e);
            return null;
        }
        final Map<String, String> queryPairs = new HashMap<>();
        final String query = url.getQuery();
        if (query == null) {
            Log.d(TAG, "getQueryMap: null query for url " + urlString);
            return queryPairs;
        }
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            String kv[] = pair.split("=");
            if (kv.length < 2) {
                continue;
            }
            queryPairs.put(kv[0], kv[1]);
        }
        return queryPairs;
    }

    public static String getYtThumbUrl(String videoId) {
        return String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", videoId);
    }

    public static String getFbVideoThumbUrl(String videoId) {
        return String.format("https://graph.facebook.com/%s/picture", videoId);
    }
}
