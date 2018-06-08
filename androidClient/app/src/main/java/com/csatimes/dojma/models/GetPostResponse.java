package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

/**
 * The format of result of http://bitsherald.org/api/get_post/?id={id}.
 *
 * @author Rushikesh Jogdand.
 */
public class GetPostResponse {
    @SerializedName("status")
    public String status;

    @SerializedName("post")
    public Post post;

    @SerializedName("previous_url")
    public String previousUrl;

    @SerializedName("next_url")
    public String nextUrl;
}
