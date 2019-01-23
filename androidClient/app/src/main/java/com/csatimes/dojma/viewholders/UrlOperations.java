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
    private static final String ERROR_TAG = UrlOperations.class.getSimpleName();

    public static Map<String, String> getQueryMap(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(ERROR_TAG, String.format("urlString = %s, error = %s", urlString, e.getMessage()), e);
            return null;
        }
        final Map<String, String> query_pairs = new HashMap<>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            String kv[] = pair.split("=");
            if (kv.length < 2) {
                continue;
            }
            query_pairs.put(kv[0], kv[1]);
        }
        return query_pairs;
    }

    public static String getYtThumbUrl(String videoId) {
        return String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", videoId);
    }

    public static String getFbVideoThumbUrl(String videoId) {
        return String.format("https://graph.facebook.com/%s/picture", videoId);
    }
}
