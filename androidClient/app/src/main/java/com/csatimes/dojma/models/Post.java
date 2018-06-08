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

    /**
     * Convenient method to insert a post object in realm with all its linked
     * children.
     *
     * @param post  the object to be persisted.
     * @param realm {@link Realm} instance, must be in a transaction.
     *              (Preferably in asynchronous transaction.)
     *              {@link Realm#executeTransactionAsync(Realm.Transaction)}
     */
    public static void persistInRealm(@NonNull final Post post, final Realm realm) {
        if (!realm.isInTransaction()) {
            throw new IllegalStateException("Database must be in transaction.");
        }
        for (final Category category : post.categories) {
            realm.insertOrUpdate(category);
        }
        for (final Tag tag : post.tags) {
            realm.insertOrUpdate(tag);
        }
        realm.insertOrUpdate(post.author);
        for (final Attachment a : post.attachments) {
            Attachment.persistInRealm(a, realm);
        }
        if (post.thumbnailImages != null && post.thumbnailImages.containsKey("full")) {
            post.fullThumbnailImage = post.thumbnailImages.get("full");
            realm.insertOrUpdate(post.fullThumbnailImage);
        }
        realm.insertOrUpdate(post);
    }
}
