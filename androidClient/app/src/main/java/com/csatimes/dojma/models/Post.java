package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Model for `post` field of DoJMA api.
 *
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
public class Post extends RealmObject {

    @Ignore
    public static final String FIELD_ID = "id";

    @Ignore
    public static final String FIELD_TITLE = "title";

    @Ignore
    public static final String FIELD_CONTENT = "content";

    @PrimaryKey
    @SerializedName("id")
    public int id;

    @SerializedName("url")
    public String url;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @SerializedName("excerpt")
    public String excerpt;

    @SerializedName("categories")
    public RealmList<Category> categories;

    @SerializedName("tags")
    public RealmList<Tag> tags;

    @SerializedName("author")
    public Author author;

    @SerializedName("attachments")
    public RealmList<Attachment> attachments;

    @SerializedName("comment_count")
    public int commentCount;

    @SerializedName("comment_status")
    public String commentStatus;

    @SerializedName("thumbnail")
    public String thumbnail;

    /**
     * Realm doesn't currently support HashMap. So, we will save only full
     * header image in db as fullThumbnailImage.
     */
    @Ignore
    @SerializedName("thumbnail_images")
    public HashMap<String, Image> thumbnailImages;

    public Image fullThumbnailImage;

    @SerializedName("date")
    public String date;

    @SerializedName("modified")
    public String modified;

    public static void persistInRealm(@NonNull final Post post, final Realm db) {
        if (post.thumbnailImages != null && post.thumbnailImages.containsKey("full")) {
            post.fullThumbnailImage = post.thumbnailImages.get("full");
        }
        for (final Attachment a : post.attachments) {
            if (a.images != null && a.images.containsKey("full")) {
                a.fullImage = a.images.get("full");
            }
        }
        db.executeTransactionAsync(realm -> {
            for (final Category category : post.categories) {
                realm.insertOrUpdate(category);
            }
            for (final Tag tag : post.tags) {
                realm.insertOrUpdate(tag);
            }
            realm.insertOrUpdate(post.author);
            for (final Attachment attachment : post.attachments) {
                if (attachment.fullImage != null) {
                    realm.insertOrUpdate(attachment.fullImage);
                }
                realm.insertOrUpdate(attachment);
            }
            if (post.fullThumbnailImage != null) {
                realm.insertOrUpdate(post.fullThumbnailImage);
            }
            realm.insertOrUpdate(post);
        });
    }
}
