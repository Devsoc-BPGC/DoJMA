package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_csatimes_dojma_models_PostRealmProxy extends com.csatimes.dojma.models.Post
    implements RealmObjectProxy, com_csatimes_dojma_models_PostRealmProxyInterface {

    static final class PostColumnInfo extends ColumnInfo {
        long idIndex;
        long urlIndex;
        long titleIndex;
        long contentIndex;
        long excerptIndex;
        long categoriesIndex;
        long tagsIndex;
        long authorIndex;
        long attachmentsIndex;
        long commentCountIndex;
        long commentStatusIndex;
        long thumbnailIndex;
        long fullThumbnailImageIndex;
        long dateIndex;
        long modifiedIndex;

        PostColumnInfo(OsSchemaInfo schemaInfo) {
            super(15);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Post");
            this.idIndex = addColumnDetails("id", "id", objectSchemaInfo);
            this.urlIndex = addColumnDetails("url", "url", objectSchemaInfo);
            this.titleIndex = addColumnDetails("title", "title", objectSchemaInfo);
            this.contentIndex = addColumnDetails("content", "content", objectSchemaInfo);
            this.excerptIndex = addColumnDetails("excerpt", "excerpt", objectSchemaInfo);
            this.categoriesIndex = addColumnDetails("categories", "categories", objectSchemaInfo);
            this.tagsIndex = addColumnDetails("tags", "tags", objectSchemaInfo);
            this.authorIndex = addColumnDetails("author", "author", objectSchemaInfo);
            this.attachmentsIndex = addColumnDetails("attachments", "attachments", objectSchemaInfo);
            this.commentCountIndex = addColumnDetails("commentCount", "commentCount", objectSchemaInfo);
            this.commentStatusIndex = addColumnDetails("commentStatus", "commentStatus", objectSchemaInfo);
            this.thumbnailIndex = addColumnDetails("thumbnail", "thumbnail", objectSchemaInfo);
            this.fullThumbnailImageIndex = addColumnDetails("fullThumbnailImage", "fullThumbnailImage", objectSchemaInfo);
            this.dateIndex = addColumnDetails("date", "date", objectSchemaInfo);
            this.modifiedIndex = addColumnDetails("modified", "modified", objectSchemaInfo);
        }

        PostColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new PostColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final PostColumnInfo src = (PostColumnInfo) rawSrc;
            final PostColumnInfo dst = (PostColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.urlIndex = src.urlIndex;
            dst.titleIndex = src.titleIndex;
            dst.contentIndex = src.contentIndex;
            dst.excerptIndex = src.excerptIndex;
            dst.categoriesIndex = src.categoriesIndex;
            dst.tagsIndex = src.tagsIndex;
            dst.authorIndex = src.authorIndex;
            dst.attachmentsIndex = src.attachmentsIndex;
            dst.commentCountIndex = src.commentCountIndex;
            dst.commentStatusIndex = src.commentStatusIndex;
            dst.thumbnailIndex = src.thumbnailIndex;
            dst.fullThumbnailImageIndex = src.fullThumbnailImageIndex;
            dst.dateIndex = src.dateIndex;
            dst.modifiedIndex = src.modifiedIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private PostColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.Post> proxyState;
    private RealmList<com.csatimes.dojma.models.Category> categoriesRealmList;
    private RealmList<com.csatimes.dojma.models.Tag> tagsRealmList;
    private RealmList<com.csatimes.dojma.models.Attachment> attachmentsRealmList;

    com_csatimes_dojma_models_PostRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (PostColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.Post>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.urlIndex);
    }

    @Override
    public void realmSet$url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.urlIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.urlIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.urlIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.urlIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$title() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.titleIndex);
    }

    @Override
    public void realmSet$title(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.titleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.titleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.titleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.titleIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$content() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.contentIndex);
    }

    @Override
    public void realmSet$content(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.contentIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.contentIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.contentIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.contentIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$excerpt() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.excerptIndex);
    }

    @Override
    public void realmSet$excerpt(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.excerptIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.excerptIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.excerptIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.excerptIndex, value);
    }

    @Override
    public RealmList<com.csatimes.dojma.models.Category> realmGet$categories() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (categoriesRealmList != null) {
            return categoriesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.categoriesIndex);
            categoriesRealmList = new RealmList<com.csatimes.dojma.models.Category>(com.csatimes.dojma.models.Category.class, osList, proxyState.getRealm$realm());
            return categoriesRealmList;
        }
    }

    @Override
    public void realmSet$categories(RealmList<com.csatimes.dojma.models.Category> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("categories")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.csatimes.dojma.models.Category> original = value;
                value = new RealmList<com.csatimes.dojma.models.Category>();
                for (com.csatimes.dojma.models.Category item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.categoriesIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Category linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Category linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    public RealmList<com.csatimes.dojma.models.Tag> realmGet$tags() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (tagsRealmList != null) {
            return tagsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.tagsIndex);
            tagsRealmList = new RealmList<com.csatimes.dojma.models.Tag>(com.csatimes.dojma.models.Tag.class, osList, proxyState.getRealm$realm());
            return tagsRealmList;
        }
    }

    @Override
    public void realmSet$tags(RealmList<com.csatimes.dojma.models.Tag> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("tags")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.csatimes.dojma.models.Tag> original = value;
                value = new RealmList<com.csatimes.dojma.models.Tag>();
                for (com.csatimes.dojma.models.Tag item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.tagsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Tag linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Tag linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    public com.csatimes.dojma.models.Author realmGet$author() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.authorIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.csatimes.dojma.models.Author.class, proxyState.getRow$realm().getLink(columnInfo.authorIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$author(com.csatimes.dojma.models.Author value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("author")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.authorIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.authorIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.authorIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.authorIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    public RealmList<com.csatimes.dojma.models.Attachment> realmGet$attachments() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (attachmentsRealmList != null) {
            return attachmentsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.attachmentsIndex);
            attachmentsRealmList = new RealmList<com.csatimes.dojma.models.Attachment>(com.csatimes.dojma.models.Attachment.class, osList, proxyState.getRealm$realm());
            return attachmentsRealmList;
        }
    }

    @Override
    public void realmSet$attachments(RealmList<com.csatimes.dojma.models.Attachment> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("attachments")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.csatimes.dojma.models.Attachment> original = value;
                value = new RealmList<com.csatimes.dojma.models.Attachment>();
                for (com.csatimes.dojma.models.Attachment item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.attachmentsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Attachment linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Attachment linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$commentCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.commentCountIndex);
    }

    @Override
    public void realmSet$commentCount(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.commentCountIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.commentCountIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$commentStatus() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.commentStatusIndex);
    }

    @Override
    public void realmSet$commentStatus(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.commentStatusIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.commentStatusIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.commentStatusIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.commentStatusIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$thumbnail() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.thumbnailIndex);
    }

    @Override
    public void realmSet$thumbnail(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.thumbnailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.thumbnailIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.thumbnailIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.thumbnailIndex, value);
    }

    @Override
    public com.csatimes.dojma.models.Image realmGet$fullThumbnailImage() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.fullThumbnailImageIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.csatimes.dojma.models.Image.class, proxyState.getRow$realm().getLink(columnInfo.fullThumbnailImageIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$fullThumbnailImage(com.csatimes.dojma.models.Image value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("fullThumbnailImage")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.fullThumbnailImageIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.fullThumbnailImageIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.fullThumbnailImageIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.fullThumbnailImageIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$date() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dateIndex);
    }

    @Override
    public void realmSet$date(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.dateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$modified() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.modifiedIndex);
    }

    @Override
    public void realmSet$modified(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.modifiedIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.modifiedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.modifiedIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.modifiedIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Post", 15, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("content", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("excerpt", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("categories", RealmFieldType.LIST, "Category");
        builder.addPersistedLinkProperty("tags", RealmFieldType.LIST, "Tag");
        builder.addPersistedLinkProperty("author", RealmFieldType.OBJECT, "Author");
        builder.addPersistedLinkProperty("attachments", RealmFieldType.LIST, "Attachment");
        builder.addPersistedProperty("commentCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("commentStatus", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("thumbnail", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("fullThumbnailImage", RealmFieldType.OBJECT, "Image");
        builder.addPersistedProperty("date", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("modified", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static PostColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new PostColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Post";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Post";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.Post createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(5);
        com.csatimes.dojma.models.Post obj = null;
        if (update) {
            Table table = realm.getTable(com.csatimes.dojma.models.Post.class);
            PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Post.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Post.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_csatimes_dojma_models_PostRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("categories")) {
                excludeFields.add("categories");
            }
            if (json.has("tags")) {
                excludeFields.add("tags");
            }
            if (json.has("author")) {
                excludeFields.add("author");
            }
            if (json.has("attachments")) {
                excludeFields.add("attachments");
            }
            if (json.has("fullThumbnailImage")) {
                excludeFields.add("fullThumbnailImage");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.com_csatimes_dojma_models_PostRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.Post.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_csatimes_dojma_models_PostRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.Post.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final com_csatimes_dojma_models_PostRealmProxyInterface objProxy = (com_csatimes_dojma_models_PostRealmProxyInterface) obj;
        if (json.has("url")) {
            if (json.isNull("url")) {
                objProxy.realmSet$url(null);
            } else {
                objProxy.realmSet$url((String) json.getString("url"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("content")) {
            if (json.isNull("content")) {
                objProxy.realmSet$content(null);
            } else {
                objProxy.realmSet$content((String) json.getString("content"));
            }
        }
        if (json.has("excerpt")) {
            if (json.isNull("excerpt")) {
                objProxy.realmSet$excerpt(null);
            } else {
                objProxy.realmSet$excerpt((String) json.getString("excerpt"));
            }
        }
        if (json.has("categories")) {
            if (json.isNull("categories")) {
                objProxy.realmSet$categories(null);
            } else {
                objProxy.realmGet$categories().clear();
                JSONArray array = json.getJSONArray("categories");
                for (int i = 0; i < array.length(); i++) {
                    com.csatimes.dojma.models.Category item = com_csatimes_dojma_models_CategoryRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$categories().add(item);
                }
            }
        }
        if (json.has("tags")) {
            if (json.isNull("tags")) {
                objProxy.realmSet$tags(null);
            } else {
                objProxy.realmGet$tags().clear();
                JSONArray array = json.getJSONArray("tags");
                for (int i = 0; i < array.length(); i++) {
                    com.csatimes.dojma.models.Tag item = com_csatimes_dojma_models_TagRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$tags().add(item);
                }
            }
        }
        if (json.has("author")) {
            if (json.isNull("author")) {
                objProxy.realmSet$author(null);
            } else {
                com.csatimes.dojma.models.Author authorObj = com_csatimes_dojma_models_AuthorRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("author"), update);
                objProxy.realmSet$author(authorObj);
            }
        }
        if (json.has("attachments")) {
            if (json.isNull("attachments")) {
                objProxy.realmSet$attachments(null);
            } else {
                objProxy.realmGet$attachments().clear();
                JSONArray array = json.getJSONArray("attachments");
                for (int i = 0; i < array.length(); i++) {
                    com.csatimes.dojma.models.Attachment item = com_csatimes_dojma_models_AttachmentRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$attachments().add(item);
                }
            }
        }
        if (json.has("commentCount")) {
            if (json.isNull("commentCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'commentCount' to null.");
            } else {
                objProxy.realmSet$commentCount((int) json.getInt("commentCount"));
            }
        }
        if (json.has("commentStatus")) {
            if (json.isNull("commentStatus")) {
                objProxy.realmSet$commentStatus(null);
            } else {
                objProxy.realmSet$commentStatus((String) json.getString("commentStatus"));
            }
        }
        if (json.has("thumbnail")) {
            if (json.isNull("thumbnail")) {
                objProxy.realmSet$thumbnail(null);
            } else {
                objProxy.realmSet$thumbnail((String) json.getString("thumbnail"));
            }
        }
        if (json.has("fullThumbnailImage")) {
            if (json.isNull("fullThumbnailImage")) {
                objProxy.realmSet$fullThumbnailImage(null);
            } else {
                com.csatimes.dojma.models.Image fullThumbnailImageObj = com_csatimes_dojma_models_ImageRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("fullThumbnailImage"), update);
                objProxy.realmSet$fullThumbnailImage(fullThumbnailImageObj);
            }
        }
        if (json.has("date")) {
            if (json.isNull("date")) {
                objProxy.realmSet$date(null);
            } else {
                objProxy.realmSet$date((String) json.getString("date"));
            }
        }
        if (json.has("modified")) {
            if (json.isNull("modified")) {
                objProxy.realmSet$modified(null);
            } else {
                objProxy.realmSet$modified((String) json.getString("modified"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.Post createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.csatimes.dojma.models.Post obj = new com.csatimes.dojma.models.Post();
        final com_csatimes_dojma_models_PostRealmProxyInterface objProxy = (com_csatimes_dojma_models_PostRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$url(null);
                }
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else if (name.equals("content")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$content((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$content(null);
                }
            } else if (name.equals("excerpt")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$excerpt((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$excerpt(null);
                }
            } else if (name.equals("categories")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$categories(null);
                } else {
                    objProxy.realmSet$categories(new RealmList<com.csatimes.dojma.models.Category>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.csatimes.dojma.models.Category item = com_csatimes_dojma_models_CategoryRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$categories().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("tags")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$tags(null);
                } else {
                    objProxy.realmSet$tags(new RealmList<com.csatimes.dojma.models.Tag>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.csatimes.dojma.models.Tag item = com_csatimes_dojma_models_TagRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$tags().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("author")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$author(null);
                } else {
                    com.csatimes.dojma.models.Author authorObj = com_csatimes_dojma_models_AuthorRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$author(authorObj);
                }
            } else if (name.equals("attachments")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$attachments(null);
                } else {
                    objProxy.realmSet$attachments(new RealmList<com.csatimes.dojma.models.Attachment>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.csatimes.dojma.models.Attachment item = com_csatimes_dojma_models_AttachmentRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$attachments().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("commentCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$commentCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'commentCount' to null.");
                }
            } else if (name.equals("commentStatus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$commentStatus((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$commentStatus(null);
                }
            } else if (name.equals("thumbnail")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$thumbnail((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$thumbnail(null);
                }
            } else if (name.equals("fullThumbnailImage")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$fullThumbnailImage(null);
                } else {
                    com.csatimes.dojma.models.Image fullThumbnailImageObj = com_csatimes_dojma_models_ImageRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$fullThumbnailImage(fullThumbnailImageObj);
                }
            } else if (name.equals("date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$date((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$date(null);
                }
            } else if (name.equals("modified")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$modified((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$modified(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.csatimes.dojma.models.Post copyOrUpdate(Realm realm, com.csatimes.dojma.models.Post object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.Post) cachedRealmObject;
        }

        com.csatimes.dojma.models.Post realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.csatimes.dojma.models.Post.class);
            PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Post.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Post.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_csatimes_dojma_models_PostRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.Post copy(Realm realm, com.csatimes.dojma.models.Post newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.Post) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.Post realmObject = realm.createObjectInternal(com.csatimes.dojma.models.Post.class, ((com_csatimes_dojma_models_PostRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_PostRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_PostRealmProxyInterface) newObject;
        com_csatimes_dojma_models_PostRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_PostRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$url(realmObjectSource.realmGet$url());
        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectCopy.realmSet$content(realmObjectSource.realmGet$content());
        realmObjectCopy.realmSet$excerpt(realmObjectSource.realmGet$excerpt());

        RealmList<com.csatimes.dojma.models.Category> categoriesList = realmObjectSource.realmGet$categories();
        if (categoriesList != null) {
            RealmList<com.csatimes.dojma.models.Category> categoriesRealmList = realmObjectCopy.realmGet$categories();
            categoriesRealmList.clear();
            for (int i = 0; i < categoriesList.size(); i++) {
                com.csatimes.dojma.models.Category categoriesItem = categoriesList.get(i);
                com.csatimes.dojma.models.Category cachecategories = (com.csatimes.dojma.models.Category) cache.get(categoriesItem);
                if (cachecategories != null) {
                    categoriesRealmList.add(cachecategories);
                } else {
                    categoriesRealmList.add(com_csatimes_dojma_models_CategoryRealmProxy.copyOrUpdate(realm, categoriesItem, update, cache));
                }
            }
        }


        RealmList<com.csatimes.dojma.models.Tag> tagsList = realmObjectSource.realmGet$tags();
        if (tagsList != null) {
            RealmList<com.csatimes.dojma.models.Tag> tagsRealmList = realmObjectCopy.realmGet$tags();
            tagsRealmList.clear();
            for (int i = 0; i < tagsList.size(); i++) {
                com.csatimes.dojma.models.Tag tagsItem = tagsList.get(i);
                com.csatimes.dojma.models.Tag cachetags = (com.csatimes.dojma.models.Tag) cache.get(tagsItem);
                if (cachetags != null) {
                    tagsRealmList.add(cachetags);
                } else {
                    tagsRealmList.add(com_csatimes_dojma_models_TagRealmProxy.copyOrUpdate(realm, tagsItem, update, cache));
                }
            }
        }


        com.csatimes.dojma.models.Author authorObj = realmObjectSource.realmGet$author();
        if (authorObj == null) {
            realmObjectCopy.realmSet$author(null);
        } else {
            com.csatimes.dojma.models.Author cacheauthor = (com.csatimes.dojma.models.Author) cache.get(authorObj);
            if (cacheauthor != null) {
                realmObjectCopy.realmSet$author(cacheauthor);
            } else {
                realmObjectCopy.realmSet$author(com_csatimes_dojma_models_AuthorRealmProxy.copyOrUpdate(realm, authorObj, update, cache));
            }
        }

        RealmList<com.csatimes.dojma.models.Attachment> attachmentsList = realmObjectSource.realmGet$attachments();
        if (attachmentsList != null) {
            RealmList<com.csatimes.dojma.models.Attachment> attachmentsRealmList = realmObjectCopy.realmGet$attachments();
            attachmentsRealmList.clear();
            for (int i = 0; i < attachmentsList.size(); i++) {
                com.csatimes.dojma.models.Attachment attachmentsItem = attachmentsList.get(i);
                com.csatimes.dojma.models.Attachment cacheattachments = (com.csatimes.dojma.models.Attachment) cache.get(attachmentsItem);
                if (cacheattachments != null) {
                    attachmentsRealmList.add(cacheattachments);
                } else {
                    attachmentsRealmList.add(com_csatimes_dojma_models_AttachmentRealmProxy.copyOrUpdate(realm, attachmentsItem, update, cache));
                }
            }
        }

        realmObjectCopy.realmSet$commentCount(realmObjectSource.realmGet$commentCount());
        realmObjectCopy.realmSet$commentStatus(realmObjectSource.realmGet$commentStatus());
        realmObjectCopy.realmSet$thumbnail(realmObjectSource.realmGet$thumbnail());

        com.csatimes.dojma.models.Image fullThumbnailImageObj = realmObjectSource.realmGet$fullThumbnailImage();
        if (fullThumbnailImageObj == null) {
            realmObjectCopy.realmSet$fullThumbnailImage(null);
        } else {
            com.csatimes.dojma.models.Image cachefullThumbnailImage = (com.csatimes.dojma.models.Image) cache.get(fullThumbnailImageObj);
            if (cachefullThumbnailImage != null) {
                realmObjectCopy.realmSet$fullThumbnailImage(cachefullThumbnailImage);
            } else {
                realmObjectCopy.realmSet$fullThumbnailImage(com_csatimes_dojma_models_ImageRealmProxy.copyOrUpdate(realm, fullThumbnailImageObj, update, cache));
            }
        }
        realmObjectCopy.realmSet$date(realmObjectSource.realmGet$date());
        realmObjectCopy.realmSet$modified(realmObjectSource.realmGet$modified());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.Post object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.Post.class);
        long tableNativePtr = table.getNativePtr();
        PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Post.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$url = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        }
        String realmGet$title = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        String realmGet$content = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$content();
        if (realmGet$content != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.contentIndex, rowIndex, realmGet$content, false);
        }
        String realmGet$excerpt = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$excerpt();
        if (realmGet$excerpt != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.excerptIndex, rowIndex, realmGet$excerpt, false);
        }

        RealmList<com.csatimes.dojma.models.Category> categoriesList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$categories();
        if (categoriesList != null) {
            OsList categoriesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.categoriesIndex);
            for (com.csatimes.dojma.models.Category categoriesItem : categoriesList) {
                Long cacheItemIndexcategories = cache.get(categoriesItem);
                if (cacheItemIndexcategories == null) {
                    cacheItemIndexcategories = com_csatimes_dojma_models_CategoryRealmProxy.insert(realm, categoriesItem, cache);
                }
                categoriesOsList.addRow(cacheItemIndexcategories);
            }
        }

        RealmList<com.csatimes.dojma.models.Tag> tagsList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$tags();
        if (tagsList != null) {
            OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
            for (com.csatimes.dojma.models.Tag tagsItem : tagsList) {
                Long cacheItemIndextags = cache.get(tagsItem);
                if (cacheItemIndextags == null) {
                    cacheItemIndextags = com_csatimes_dojma_models_TagRealmProxy.insert(realm, tagsItem, cache);
                }
                tagsOsList.addRow(cacheItemIndextags);
            }
        }

        com.csatimes.dojma.models.Author authorObj = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$author();
        if (authorObj != null) {
            Long cacheauthor = cache.get(authorObj);
            if (cacheauthor == null) {
                cacheauthor = com_csatimes_dojma_models_AuthorRealmProxy.insert(realm, authorObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
        }

        RealmList<com.csatimes.dojma.models.Attachment> attachmentsList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$attachments();
        if (attachmentsList != null) {
            OsList attachmentsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.attachmentsIndex);
            for (com.csatimes.dojma.models.Attachment attachmentsItem : attachmentsList) {
                Long cacheItemIndexattachments = cache.get(attachmentsItem);
                if (cacheItemIndexattachments == null) {
                    cacheItemIndexattachments = com_csatimes_dojma_models_AttachmentRealmProxy.insert(realm, attachmentsItem, cache);
                }
                attachmentsOsList.addRow(cacheItemIndexattachments);
            }
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.commentCountIndex, rowIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$commentCount(), false);
        String realmGet$commentStatus = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$commentStatus();
        if (realmGet$commentStatus != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.commentStatusIndex, rowIndex, realmGet$commentStatus, false);
        }
        String realmGet$thumbnail = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$thumbnail();
        if (realmGet$thumbnail != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.thumbnailIndex, rowIndex, realmGet$thumbnail, false);
        }

        com.csatimes.dojma.models.Image fullThumbnailImageObj = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$fullThumbnailImage();
        if (fullThumbnailImageObj != null) {
            Long cachefullThumbnailImage = cache.get(fullThumbnailImageObj);
            if (cachefullThumbnailImage == null) {
                cachefullThumbnailImage = com_csatimes_dojma_models_ImageRealmProxy.insert(realm, fullThumbnailImageObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.fullThumbnailImageIndex, rowIndex, cachefullThumbnailImage, false);
        }
        String realmGet$date = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date, false);
        }
        String realmGet$modified = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$modified();
        if (realmGet$modified != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.modifiedIndex, rowIndex, realmGet$modified, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.Post.class);
        long tableNativePtr = table.getNativePtr();
        PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Post.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.csatimes.dojma.models.Post object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.Post) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$url = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$url();
            if (realmGet$url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
            }
            String realmGet$title = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
            String realmGet$content = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$content();
            if (realmGet$content != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.contentIndex, rowIndex, realmGet$content, false);
            }
            String realmGet$excerpt = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$excerpt();
            if (realmGet$excerpt != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.excerptIndex, rowIndex, realmGet$excerpt, false);
            }

            RealmList<com.csatimes.dojma.models.Category> categoriesList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$categories();
            if (categoriesList != null) {
                OsList categoriesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.categoriesIndex);
                for (com.csatimes.dojma.models.Category categoriesItem : categoriesList) {
                    Long cacheItemIndexcategories = cache.get(categoriesItem);
                    if (cacheItemIndexcategories == null) {
                        cacheItemIndexcategories = com_csatimes_dojma_models_CategoryRealmProxy.insert(realm, categoriesItem, cache);
                    }
                    categoriesOsList.addRow(cacheItemIndexcategories);
                }
            }

            RealmList<com.csatimes.dojma.models.Tag> tagsList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$tags();
            if (tagsList != null) {
                OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
                for (com.csatimes.dojma.models.Tag tagsItem : tagsList) {
                    Long cacheItemIndextags = cache.get(tagsItem);
                    if (cacheItemIndextags == null) {
                        cacheItemIndextags = com_csatimes_dojma_models_TagRealmProxy.insert(realm, tagsItem, cache);
                    }
                    tagsOsList.addRow(cacheItemIndextags);
                }
            }

            com.csatimes.dojma.models.Author authorObj = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$author();
            if (authorObj != null) {
                Long cacheauthor = cache.get(authorObj);
                if (cacheauthor == null) {
                    cacheauthor = com_csatimes_dojma_models_AuthorRealmProxy.insert(realm, authorObj, cache);
                }
                table.setLink(columnInfo.authorIndex, rowIndex, cacheauthor, false);
            }

            RealmList<com.csatimes.dojma.models.Attachment> attachmentsList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$attachments();
            if (attachmentsList != null) {
                OsList attachmentsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.attachmentsIndex);
                for (com.csatimes.dojma.models.Attachment attachmentsItem : attachmentsList) {
                    Long cacheItemIndexattachments = cache.get(attachmentsItem);
                    if (cacheItemIndexattachments == null) {
                        cacheItemIndexattachments = com_csatimes_dojma_models_AttachmentRealmProxy.insert(realm, attachmentsItem, cache);
                    }
                    attachmentsOsList.addRow(cacheItemIndexattachments);
                }
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.commentCountIndex, rowIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$commentCount(), false);
            String realmGet$commentStatus = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$commentStatus();
            if (realmGet$commentStatus != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.commentStatusIndex, rowIndex, realmGet$commentStatus, false);
            }
            String realmGet$thumbnail = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$thumbnail();
            if (realmGet$thumbnail != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.thumbnailIndex, rowIndex, realmGet$thumbnail, false);
            }

            com.csatimes.dojma.models.Image fullThumbnailImageObj = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$fullThumbnailImage();
            if (fullThumbnailImageObj != null) {
                Long cachefullThumbnailImage = cache.get(fullThumbnailImageObj);
                if (cachefullThumbnailImage == null) {
                    cachefullThumbnailImage = com_csatimes_dojma_models_ImageRealmProxy.insert(realm, fullThumbnailImageObj, cache);
                }
                table.setLink(columnInfo.fullThumbnailImageIndex, rowIndex, cachefullThumbnailImage, false);
            }
            String realmGet$date = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date, false);
            }
            String realmGet$modified = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$modified();
            if (realmGet$modified != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.modifiedIndex, rowIndex, realmGet$modified, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.Post object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.Post.class);
        long tableNativePtr = table.getNativePtr();
        PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Post.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$url = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
        }
        String realmGet$title = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        String realmGet$content = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$content();
        if (realmGet$content != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.contentIndex, rowIndex, realmGet$content, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.contentIndex, rowIndex, false);
        }
        String realmGet$excerpt = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$excerpt();
        if (realmGet$excerpt != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.excerptIndex, rowIndex, realmGet$excerpt, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.excerptIndex, rowIndex, false);
        }

        OsList categoriesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.categoriesIndex);
        RealmList<com.csatimes.dojma.models.Category> categoriesList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$categories();
        if (categoriesList != null && categoriesList.size() == categoriesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = categoriesList.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Category categoriesItem = categoriesList.get(i);
                Long cacheItemIndexcategories = cache.get(categoriesItem);
                if (cacheItemIndexcategories == null) {
                    cacheItemIndexcategories = com_csatimes_dojma_models_CategoryRealmProxy.insertOrUpdate(realm, categoriesItem, cache);
                }
                categoriesOsList.setRow(i, cacheItemIndexcategories);
            }
        } else {
            categoriesOsList.removeAll();
            if (categoriesList != null) {
                for (com.csatimes.dojma.models.Category categoriesItem : categoriesList) {
                    Long cacheItemIndexcategories = cache.get(categoriesItem);
                    if (cacheItemIndexcategories == null) {
                        cacheItemIndexcategories = com_csatimes_dojma_models_CategoryRealmProxy.insertOrUpdate(realm, categoriesItem, cache);
                    }
                    categoriesOsList.addRow(cacheItemIndexcategories);
                }
            }
        }


        OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
        RealmList<com.csatimes.dojma.models.Tag> tagsList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$tags();
        if (tagsList != null && tagsList.size() == tagsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = tagsList.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Tag tagsItem = tagsList.get(i);
                Long cacheItemIndextags = cache.get(tagsItem);
                if (cacheItemIndextags == null) {
                    cacheItemIndextags = com_csatimes_dojma_models_TagRealmProxy.insertOrUpdate(realm, tagsItem, cache);
                }
                tagsOsList.setRow(i, cacheItemIndextags);
            }
        } else {
            tagsOsList.removeAll();
            if (tagsList != null) {
                for (com.csatimes.dojma.models.Tag tagsItem : tagsList) {
                    Long cacheItemIndextags = cache.get(tagsItem);
                    if (cacheItemIndextags == null) {
                        cacheItemIndextags = com_csatimes_dojma_models_TagRealmProxy.insertOrUpdate(realm, tagsItem, cache);
                    }
                    tagsOsList.addRow(cacheItemIndextags);
                }
            }
        }


        com.csatimes.dojma.models.Author authorObj = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$author();
        if (authorObj != null) {
            Long cacheauthor = cache.get(authorObj);
            if (cacheauthor == null) {
                cacheauthor = com_csatimes_dojma_models_AuthorRealmProxy.insertOrUpdate(realm, authorObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.authorIndex, rowIndex);
        }

        OsList attachmentsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.attachmentsIndex);
        RealmList<com.csatimes.dojma.models.Attachment> attachmentsList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$attachments();
        if (attachmentsList != null && attachmentsList.size() == attachmentsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = attachmentsList.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Attachment attachmentsItem = attachmentsList.get(i);
                Long cacheItemIndexattachments = cache.get(attachmentsItem);
                if (cacheItemIndexattachments == null) {
                    cacheItemIndexattachments = com_csatimes_dojma_models_AttachmentRealmProxy.insertOrUpdate(realm, attachmentsItem, cache);
                }
                attachmentsOsList.setRow(i, cacheItemIndexattachments);
            }
        } else {
            attachmentsOsList.removeAll();
            if (attachmentsList != null) {
                for (com.csatimes.dojma.models.Attachment attachmentsItem : attachmentsList) {
                    Long cacheItemIndexattachments = cache.get(attachmentsItem);
                    if (cacheItemIndexattachments == null) {
                        cacheItemIndexattachments = com_csatimes_dojma_models_AttachmentRealmProxy.insertOrUpdate(realm, attachmentsItem, cache);
                    }
                    attachmentsOsList.addRow(cacheItemIndexattachments);
                }
            }
        }

        Table.nativeSetLong(tableNativePtr, columnInfo.commentCountIndex, rowIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$commentCount(), false);
        String realmGet$commentStatus = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$commentStatus();
        if (realmGet$commentStatus != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.commentStatusIndex, rowIndex, realmGet$commentStatus, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.commentStatusIndex, rowIndex, false);
        }
        String realmGet$thumbnail = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$thumbnail();
        if (realmGet$thumbnail != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.thumbnailIndex, rowIndex, realmGet$thumbnail, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.thumbnailIndex, rowIndex, false);
        }

        com.csatimes.dojma.models.Image fullThumbnailImageObj = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$fullThumbnailImage();
        if (fullThumbnailImageObj != null) {
            Long cachefullThumbnailImage = cache.get(fullThumbnailImageObj);
            if (cachefullThumbnailImage == null) {
                cachefullThumbnailImage = com_csatimes_dojma_models_ImageRealmProxy.insertOrUpdate(realm, fullThumbnailImageObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.fullThumbnailImageIndex, rowIndex, cachefullThumbnailImage, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.fullThumbnailImageIndex, rowIndex);
        }
        String realmGet$date = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
        }
        String realmGet$modified = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$modified();
        if (realmGet$modified != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.modifiedIndex, rowIndex, realmGet$modified, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.modifiedIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.Post.class);
        long tableNativePtr = table.getNativePtr();
        PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Post.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.csatimes.dojma.models.Post object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.Post) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$url = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$url();
            if (realmGet$url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
            }
            String realmGet$title = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
            String realmGet$content = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$content();
            if (realmGet$content != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.contentIndex, rowIndex, realmGet$content, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.contentIndex, rowIndex, false);
            }
            String realmGet$excerpt = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$excerpt();
            if (realmGet$excerpt != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.excerptIndex, rowIndex, realmGet$excerpt, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.excerptIndex, rowIndex, false);
            }

            OsList categoriesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.categoriesIndex);
            RealmList<com.csatimes.dojma.models.Category> categoriesList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$categories();
            if (categoriesList != null && categoriesList.size() == categoriesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = categoriesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.csatimes.dojma.models.Category categoriesItem = categoriesList.get(i);
                    Long cacheItemIndexcategories = cache.get(categoriesItem);
                    if (cacheItemIndexcategories == null) {
                        cacheItemIndexcategories = com_csatimes_dojma_models_CategoryRealmProxy.insertOrUpdate(realm, categoriesItem, cache);
                    }
                    categoriesOsList.setRow(i, cacheItemIndexcategories);
                }
            } else {
                categoriesOsList.removeAll();
                if (categoriesList != null) {
                    for (com.csatimes.dojma.models.Category categoriesItem : categoriesList) {
                        Long cacheItemIndexcategories = cache.get(categoriesItem);
                        if (cacheItemIndexcategories == null) {
                            cacheItemIndexcategories = com_csatimes_dojma_models_CategoryRealmProxy.insertOrUpdate(realm, categoriesItem, cache);
                        }
                        categoriesOsList.addRow(cacheItemIndexcategories);
                    }
                }
            }


            OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
            RealmList<com.csatimes.dojma.models.Tag> tagsList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$tags();
            if (tagsList != null && tagsList.size() == tagsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = tagsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.csatimes.dojma.models.Tag tagsItem = tagsList.get(i);
                    Long cacheItemIndextags = cache.get(tagsItem);
                    if (cacheItemIndextags == null) {
                        cacheItemIndextags = com_csatimes_dojma_models_TagRealmProxy.insertOrUpdate(realm, tagsItem, cache);
                    }
                    tagsOsList.setRow(i, cacheItemIndextags);
                }
            } else {
                tagsOsList.removeAll();
                if (tagsList != null) {
                    for (com.csatimes.dojma.models.Tag tagsItem : tagsList) {
                        Long cacheItemIndextags = cache.get(tagsItem);
                        if (cacheItemIndextags == null) {
                            cacheItemIndextags = com_csatimes_dojma_models_TagRealmProxy.insertOrUpdate(realm, tagsItem, cache);
                        }
                        tagsOsList.addRow(cacheItemIndextags);
                    }
                }
            }


            com.csatimes.dojma.models.Author authorObj = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$author();
            if (authorObj != null) {
                Long cacheauthor = cache.get(authorObj);
                if (cacheauthor == null) {
                    cacheauthor = com_csatimes_dojma_models_AuthorRealmProxy.insertOrUpdate(realm, authorObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.authorIndex, rowIndex);
            }

            OsList attachmentsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.attachmentsIndex);
            RealmList<com.csatimes.dojma.models.Attachment> attachmentsList = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$attachments();
            if (attachmentsList != null && attachmentsList.size() == attachmentsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = attachmentsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.csatimes.dojma.models.Attachment attachmentsItem = attachmentsList.get(i);
                    Long cacheItemIndexattachments = cache.get(attachmentsItem);
                    if (cacheItemIndexattachments == null) {
                        cacheItemIndexattachments = com_csatimes_dojma_models_AttachmentRealmProxy.insertOrUpdate(realm, attachmentsItem, cache);
                    }
                    attachmentsOsList.setRow(i, cacheItemIndexattachments);
                }
            } else {
                attachmentsOsList.removeAll();
                if (attachmentsList != null) {
                    for (com.csatimes.dojma.models.Attachment attachmentsItem : attachmentsList) {
                        Long cacheItemIndexattachments = cache.get(attachmentsItem);
                        if (cacheItemIndexattachments == null) {
                            cacheItemIndexattachments = com_csatimes_dojma_models_AttachmentRealmProxy.insertOrUpdate(realm, attachmentsItem, cache);
                        }
                        attachmentsOsList.addRow(cacheItemIndexattachments);
                    }
                }
            }

            Table.nativeSetLong(tableNativePtr, columnInfo.commentCountIndex, rowIndex, ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$commentCount(), false);
            String realmGet$commentStatus = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$commentStatus();
            if (realmGet$commentStatus != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.commentStatusIndex, rowIndex, realmGet$commentStatus, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.commentStatusIndex, rowIndex, false);
            }
            String realmGet$thumbnail = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$thumbnail();
            if (realmGet$thumbnail != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.thumbnailIndex, rowIndex, realmGet$thumbnail, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.thumbnailIndex, rowIndex, false);
            }

            com.csatimes.dojma.models.Image fullThumbnailImageObj = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$fullThumbnailImage();
            if (fullThumbnailImageObj != null) {
                Long cachefullThumbnailImage = cache.get(fullThumbnailImageObj);
                if (cachefullThumbnailImage == null) {
                    cachefullThumbnailImage = com_csatimes_dojma_models_ImageRealmProxy.insertOrUpdate(realm, fullThumbnailImageObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.fullThumbnailImageIndex, rowIndex, cachefullThumbnailImage, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.fullThumbnailImageIndex, rowIndex);
            }
            String realmGet$date = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
            }
            String realmGet$modified = ((com_csatimes_dojma_models_PostRealmProxyInterface) object).realmGet$modified();
            if (realmGet$modified != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.modifiedIndex, rowIndex, realmGet$modified, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.modifiedIndex, rowIndex, false);
            }
        }
    }

    public static com.csatimes.dojma.models.Post createDetachedCopy(com.csatimes.dojma.models.Post realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.Post unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.Post();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.Post) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.Post) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_PostRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_PostRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_PostRealmProxyInterface realmSource = (com_csatimes_dojma_models_PostRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$url(realmSource.realmGet$url());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$content(realmSource.realmGet$content());
        unmanagedCopy.realmSet$excerpt(realmSource.realmGet$excerpt());

        // Deep copy of categories
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$categories(null);
        } else {
            RealmList<com.csatimes.dojma.models.Category> managedcategoriesList = realmSource.realmGet$categories();
            RealmList<com.csatimes.dojma.models.Category> unmanagedcategoriesList = new RealmList<com.csatimes.dojma.models.Category>();
            unmanagedCopy.realmSet$categories(unmanagedcategoriesList);
            int nextDepth = currentDepth + 1;
            int size = managedcategoriesList.size();
            for (int i = 0; i < size; i++) {
                com.csatimes.dojma.models.Category item = com_csatimes_dojma_models_CategoryRealmProxy.createDetachedCopy(managedcategoriesList.get(i), nextDepth, maxDepth, cache);
                unmanagedcategoriesList.add(item);
            }
        }

        // Deep copy of tags
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$tags(null);
        } else {
            RealmList<com.csatimes.dojma.models.Tag> managedtagsList = realmSource.realmGet$tags();
            RealmList<com.csatimes.dojma.models.Tag> unmanagedtagsList = new RealmList<com.csatimes.dojma.models.Tag>();
            unmanagedCopy.realmSet$tags(unmanagedtagsList);
            int nextDepth = currentDepth + 1;
            int size = managedtagsList.size();
            for (int i = 0; i < size; i++) {
                com.csatimes.dojma.models.Tag item = com_csatimes_dojma_models_TagRealmProxy.createDetachedCopy(managedtagsList.get(i), nextDepth, maxDepth, cache);
                unmanagedtagsList.add(item);
            }
        }

        // Deep copy of author
        unmanagedCopy.realmSet$author(com_csatimes_dojma_models_AuthorRealmProxy.createDetachedCopy(realmSource.realmGet$author(), currentDepth + 1, maxDepth, cache));

        // Deep copy of attachments
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$attachments(null);
        } else {
            RealmList<com.csatimes.dojma.models.Attachment> managedattachmentsList = realmSource.realmGet$attachments();
            RealmList<com.csatimes.dojma.models.Attachment> unmanagedattachmentsList = new RealmList<com.csatimes.dojma.models.Attachment>();
            unmanagedCopy.realmSet$attachments(unmanagedattachmentsList);
            int nextDepth = currentDepth + 1;
            int size = managedattachmentsList.size();
            for (int i = 0; i < size; i++) {
                com.csatimes.dojma.models.Attachment item = com_csatimes_dojma_models_AttachmentRealmProxy.createDetachedCopy(managedattachmentsList.get(i), nextDepth, maxDepth, cache);
                unmanagedattachmentsList.add(item);
            }
        }
        unmanagedCopy.realmSet$commentCount(realmSource.realmGet$commentCount());
        unmanagedCopy.realmSet$commentStatus(realmSource.realmGet$commentStatus());
        unmanagedCopy.realmSet$thumbnail(realmSource.realmGet$thumbnail());

        // Deep copy of fullThumbnailImage
        unmanagedCopy.realmSet$fullThumbnailImage(com_csatimes_dojma_models_ImageRealmProxy.createDetachedCopy(realmSource.realmGet$fullThumbnailImage(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$date(realmSource.realmGet$date());
        unmanagedCopy.realmSet$modified(realmSource.realmGet$modified());

        return unmanagedObject;
    }

    static com.csatimes.dojma.models.Post update(Realm realm, com.csatimes.dojma.models.Post realmObject, com.csatimes.dojma.models.Post newObject, Map<RealmModel, RealmObjectProxy> cache) {
        com_csatimes_dojma_models_PostRealmProxyInterface realmObjectTarget = (com_csatimes_dojma_models_PostRealmProxyInterface) realmObject;
        com_csatimes_dojma_models_PostRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_PostRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$url(realmObjectSource.realmGet$url());
        realmObjectTarget.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectTarget.realmSet$content(realmObjectSource.realmGet$content());
        realmObjectTarget.realmSet$excerpt(realmObjectSource.realmGet$excerpt());
        RealmList<com.csatimes.dojma.models.Category> categoriesList = realmObjectSource.realmGet$categories();
        RealmList<com.csatimes.dojma.models.Category> categoriesRealmList = realmObjectTarget.realmGet$categories();
        if (categoriesList != null && categoriesList.size() == categoriesRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = categoriesList.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Category categoriesItem = categoriesList.get(i);
                com.csatimes.dojma.models.Category cachecategories = (com.csatimes.dojma.models.Category) cache.get(categoriesItem);
                if (cachecategories != null) {
                    categoriesRealmList.set(i, cachecategories);
                } else {
                    categoriesRealmList.set(i, com_csatimes_dojma_models_CategoryRealmProxy.copyOrUpdate(realm, categoriesItem, true, cache));
                }
            }
        } else {
            categoriesRealmList.clear();
            if (categoriesList != null) {
                for (int i = 0; i < categoriesList.size(); i++) {
                    com.csatimes.dojma.models.Category categoriesItem = categoriesList.get(i);
                    com.csatimes.dojma.models.Category cachecategories = (com.csatimes.dojma.models.Category) cache.get(categoriesItem);
                    if (cachecategories != null) {
                        categoriesRealmList.add(cachecategories);
                    } else {
                        categoriesRealmList.add(com_csatimes_dojma_models_CategoryRealmProxy.copyOrUpdate(realm, categoriesItem, true, cache));
                    }
                }
            }
        }
        RealmList<com.csatimes.dojma.models.Tag> tagsList = realmObjectSource.realmGet$tags();
        RealmList<com.csatimes.dojma.models.Tag> tagsRealmList = realmObjectTarget.realmGet$tags();
        if (tagsList != null && tagsList.size() == tagsRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = tagsList.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Tag tagsItem = tagsList.get(i);
                com.csatimes.dojma.models.Tag cachetags = (com.csatimes.dojma.models.Tag) cache.get(tagsItem);
                if (cachetags != null) {
                    tagsRealmList.set(i, cachetags);
                } else {
                    tagsRealmList.set(i, com_csatimes_dojma_models_TagRealmProxy.copyOrUpdate(realm, tagsItem, true, cache));
                }
            }
        } else {
            tagsRealmList.clear();
            if (tagsList != null) {
                for (int i = 0; i < tagsList.size(); i++) {
                    com.csatimes.dojma.models.Tag tagsItem = tagsList.get(i);
                    com.csatimes.dojma.models.Tag cachetags = (com.csatimes.dojma.models.Tag) cache.get(tagsItem);
                    if (cachetags != null) {
                        tagsRealmList.add(cachetags);
                    } else {
                        tagsRealmList.add(com_csatimes_dojma_models_TagRealmProxy.copyOrUpdate(realm, tagsItem, true, cache));
                    }
                }
            }
        }
        com.csatimes.dojma.models.Author authorObj = realmObjectSource.realmGet$author();
        if (authorObj == null) {
            realmObjectTarget.realmSet$author(null);
        } else {
            com.csatimes.dojma.models.Author cacheauthor = (com.csatimes.dojma.models.Author) cache.get(authorObj);
            if (cacheauthor != null) {
                realmObjectTarget.realmSet$author(cacheauthor);
            } else {
                realmObjectTarget.realmSet$author(com_csatimes_dojma_models_AuthorRealmProxy.copyOrUpdate(realm, authorObj, true, cache));
            }
        }
        RealmList<com.csatimes.dojma.models.Attachment> attachmentsList = realmObjectSource.realmGet$attachments();
        RealmList<com.csatimes.dojma.models.Attachment> attachmentsRealmList = realmObjectTarget.realmGet$attachments();
        if (attachmentsList != null && attachmentsList.size() == attachmentsRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = attachmentsList.size();
            for (int i = 0; i < objects; i++) {
                com.csatimes.dojma.models.Attachment attachmentsItem = attachmentsList.get(i);
                com.csatimes.dojma.models.Attachment cacheattachments = (com.csatimes.dojma.models.Attachment) cache.get(attachmentsItem);
                if (cacheattachments != null) {
                    attachmentsRealmList.set(i, cacheattachments);
                } else {
                    attachmentsRealmList.set(i, com_csatimes_dojma_models_AttachmentRealmProxy.copyOrUpdate(realm, attachmentsItem, true, cache));
                }
            }
        } else {
            attachmentsRealmList.clear();
            if (attachmentsList != null) {
                for (int i = 0; i < attachmentsList.size(); i++) {
                    com.csatimes.dojma.models.Attachment attachmentsItem = attachmentsList.get(i);
                    com.csatimes.dojma.models.Attachment cacheattachments = (com.csatimes.dojma.models.Attachment) cache.get(attachmentsItem);
                    if (cacheattachments != null) {
                        attachmentsRealmList.add(cacheattachments);
                    } else {
                        attachmentsRealmList.add(com_csatimes_dojma_models_AttachmentRealmProxy.copyOrUpdate(realm, attachmentsItem, true, cache));
                    }
                }
            }
        }
        realmObjectTarget.realmSet$commentCount(realmObjectSource.realmGet$commentCount());
        realmObjectTarget.realmSet$commentStatus(realmObjectSource.realmGet$commentStatus());
        realmObjectTarget.realmSet$thumbnail(realmObjectSource.realmGet$thumbnail());
        com.csatimes.dojma.models.Image fullThumbnailImageObj = realmObjectSource.realmGet$fullThumbnailImage();
        if (fullThumbnailImageObj == null) {
            realmObjectTarget.realmSet$fullThumbnailImage(null);
        } else {
            com.csatimes.dojma.models.Image cachefullThumbnailImage = (com.csatimes.dojma.models.Image) cache.get(fullThumbnailImageObj);
            if (cachefullThumbnailImage != null) {
                realmObjectTarget.realmSet$fullThumbnailImage(cachefullThumbnailImage);
            } else {
                realmObjectTarget.realmSet$fullThumbnailImage(com_csatimes_dojma_models_ImageRealmProxy.copyOrUpdate(realm, fullThumbnailImageObj, true, cache));
            }
        }
        realmObjectTarget.realmSet$date(realmObjectSource.realmGet$date());
        realmObjectTarget.realmSet$modified(realmObjectSource.realmGet$modified());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Post = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{url:");
        stringBuilder.append(realmGet$url() != null ? realmGet$url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{content:");
        stringBuilder.append(realmGet$content() != null ? realmGet$content() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{excerpt:");
        stringBuilder.append(realmGet$excerpt() != null ? realmGet$excerpt() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{categories:");
        stringBuilder.append("RealmList<Category>[").append(realmGet$categories().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tags:");
        stringBuilder.append("RealmList<Tag>[").append(realmGet$tags().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{author:");
        stringBuilder.append(realmGet$author() != null ? "Author" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{attachments:");
        stringBuilder.append("RealmList<Attachment>[").append(realmGet$attachments().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{commentCount:");
        stringBuilder.append(realmGet$commentCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{commentStatus:");
        stringBuilder.append(realmGet$commentStatus() != null ? realmGet$commentStatus() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{thumbnail:");
        stringBuilder.append(realmGet$thumbnail() != null ? realmGet$thumbnail() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{fullThumbnailImage:");
        stringBuilder.append(realmGet$fullThumbnailImage() != null ? "Image" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(realmGet$date() != null ? realmGet$date() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{modified:");
        stringBuilder.append(realmGet$modified() != null ? realmGet$modified() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_csatimes_dojma_models_PostRealmProxy aPost = (com_csatimes_dojma_models_PostRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aPost.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aPost.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aPost.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
