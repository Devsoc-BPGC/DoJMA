package com.csatimes.dojma.aboutapp;

import com.csatimes.dojma.models.AndroidApp;
import com.csatimes.dojma.models.Person;
import com.csatimes.dojma.models.SocialLink;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Rushikesh Jogdand.
 */
public interface MacApi {
    @GET("androidApps")
    Call<List<AndroidApp>> getAndroidApps();

    @GET("socialLinks")
    Call<List<SocialLink>> getSocialLinks();

    @GET("postHolders")
    Call<List<Person>> getPostHolders();
}
