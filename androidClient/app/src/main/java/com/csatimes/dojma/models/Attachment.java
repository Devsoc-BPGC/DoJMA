package com.csatimes.dojma.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Model for `attachment` fields in Dojma api.
 *
 * @author Rushikesh Jogdand.
 */
@SuppressWarnings("DuplicateStringLiteralInspection")
public class Attachment extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    public int id;

    @SerializedName("url")
    public String url;

    @SerializedName("title")
    public String title;

    @SerializedName("slug")
    public String slug;

    @SerializedName("description")
    public String description;

    @SerializedName("caption")
    public String caption;

    @SerializedName("parent")
    public int parent;

    @SerializedName("mime_type")
    public String mimeType;

    /**
     * Saving full image as fullImage.
     */
    @Ignore
    @SerializedName("images")
    public HashMap<String, Image> images;

    public Image fullImage;

    /**
     * Convenient method to insert a attachment object in realm with all its linked
     * children.
     *
     * @param attachment the object to be persisted.
     * @param realm         {@link Realm} instance, must be in a transaction.
     *                   (Preferably in asynchronous transaction.)
     *                   {@link Realm#executeTransactionAsync(Realm.Transaction)}
     */
    public static void persistInRealm(@NonNull final Attachment attachment, final Realm realm) {
        if (!realm.isInTransaction()) {
            throw new IllegalStateException("Database must be in transaction.");
        }
        if (attachment.images != null && attachment.images.containsKey("full")) {
            attachment.fullImage = attachment.images.get("full");
            realm.insertOrUpdate(attachment.fullImage);
        }
        realm.insertOrUpdate(attachment);
    }
}
