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
public class com_csatimes_dojma_models_AttachmentRealmProxy extends com.csatimes.dojma.models.Attachment
    implements RealmObjectProxy, com_csatimes_dojma_models_AttachmentRealmProxyInterface {

    static final class AttachmentColumnInfo extends ColumnInfo {
        long idIndex;
        long urlIndex;
        long titleIndex;
        long slugIndex;
        long descriptionIndex;
        long captionIndex;
        long parentIndex;
        long mimeTypeIndex;
        long fullImageIndex;

        AttachmentColumnInfo(OsSchemaInfo schemaInfo) {
            super(9);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Attachment");
            this.idIndex = addColumnDetails("id", "id", objectSchemaInfo);
            this.urlIndex = addColumnDetails("url", "url", objectSchemaInfo);
            this.titleIndex = addColumnDetails("title", "title", objectSchemaInfo);
            this.slugIndex = addColumnDetails("slug", "slug", objectSchemaInfo);
            this.descriptionIndex = addColumnDetails("description", "description", objectSchemaInfo);
            this.captionIndex = addColumnDetails("caption", "caption", objectSchemaInfo);
            this.parentIndex = addColumnDetails("parent", "parent", objectSchemaInfo);
            this.mimeTypeIndex = addColumnDetails("mimeType", "mimeType", objectSchemaInfo);
            this.fullImageIndex = addColumnDetails("fullImage", "fullImage", objectSchemaInfo);
        }

        AttachmentColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new AttachmentColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final AttachmentColumnInfo src = (AttachmentColumnInfo) rawSrc;
            final AttachmentColumnInfo dst = (AttachmentColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.urlIndex = src.urlIndex;
            dst.titleIndex = src.titleIndex;
            dst.slugIndex = src.slugIndex;
            dst.descriptionIndex = src.descriptionIndex;
            dst.captionIndex = src.captionIndex;
            dst.parentIndex = src.parentIndex;
            dst.mimeTypeIndex = src.mimeTypeIndex;
            dst.fullImageIndex = src.fullImageIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private AttachmentColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.Attachment> proxyState;

    com_csatimes_dojma_models_AttachmentRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (AttachmentColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.Attachment>(this);
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
    public String realmGet$slug() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.slugIndex);
    }

    @Override
    public void realmSet$slug(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.slugIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.slugIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.slugIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.slugIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$description() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.descriptionIndex);
    }

    @Override
    public void realmSet$description(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.descriptionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.descriptionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.descriptionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.descriptionIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caption() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.captionIndex);
    }

    @Override
    public void realmSet$caption(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.captionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.captionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.captionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.captionIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$parent() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.parentIndex);
    }

    @Override
    public void realmSet$parent(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.parentIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.parentIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$mimeType() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.mimeTypeIndex);
    }

    @Override
    public void realmSet$mimeType(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.mimeTypeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.mimeTypeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.mimeTypeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.mimeTypeIndex, value);
    }

    @Override
    public com.csatimes.dojma.models.Image realmGet$fullImage() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.fullImageIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.csatimes.dojma.models.Image.class, proxyState.getRow$realm().getLink(columnInfo.fullImageIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$fullImage(com.csatimes.dojma.models.Image value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("fullImage")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.fullImageIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.fullImageIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.fullImageIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.fullImageIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Attachment", 9, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("slug", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("caption", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("parent", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("mimeType", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("fullImage", RealmFieldType.OBJECT, "Image");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static AttachmentColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new AttachmentColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Attachment";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Attachment";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.Attachment createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        com.csatimes.dojma.models.Attachment obj = null;
        if (update) {
            Table table = realm.getTable(com.csatimes.dojma.models.Attachment.class);
            AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Attachment.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Attachment.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_csatimes_dojma_models_AttachmentRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("fullImage")) {
                excludeFields.add("fullImage");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.com_csatimes_dojma_models_AttachmentRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.Attachment.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_csatimes_dojma_models_AttachmentRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.Attachment.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final com_csatimes_dojma_models_AttachmentRealmProxyInterface objProxy = (com_csatimes_dojma_models_AttachmentRealmProxyInterface) obj;
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
        if (json.has("slug")) {
            if (json.isNull("slug")) {
                objProxy.realmSet$slug(null);
            } else {
                objProxy.realmSet$slug((String) json.getString("slug"));
            }
        }
        if (json.has("description")) {
            if (json.isNull("description")) {
                objProxy.realmSet$description(null);
            } else {
                objProxy.realmSet$description((String) json.getString("description"));
            }
        }
        if (json.has("caption")) {
            if (json.isNull("caption")) {
                objProxy.realmSet$caption(null);
            } else {
                objProxy.realmSet$caption((String) json.getString("caption"));
            }
        }
        if (json.has("parent")) {
            if (json.isNull("parent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'parent' to null.");
            } else {
                objProxy.realmSet$parent((int) json.getInt("parent"));
            }
        }
        if (json.has("mimeType")) {
            if (json.isNull("mimeType")) {
                objProxy.realmSet$mimeType(null);
            } else {
                objProxy.realmSet$mimeType((String) json.getString("mimeType"));
            }
        }
        if (json.has("fullImage")) {
            if (json.isNull("fullImage")) {
                objProxy.realmSet$fullImage(null);
            } else {
                com.csatimes.dojma.models.Image fullImageObj = com_csatimes_dojma_models_ImageRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("fullImage"), update);
                objProxy.realmSet$fullImage(fullImageObj);
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.Attachment createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.csatimes.dojma.models.Attachment obj = new com.csatimes.dojma.models.Attachment();
        final com_csatimes_dojma_models_AttachmentRealmProxyInterface objProxy = (com_csatimes_dojma_models_AttachmentRealmProxyInterface) obj;
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
            } else if (name.equals("slug")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$slug((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$slug(null);
                }
            } else if (name.equals("description")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$description((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$description(null);
                }
            } else if (name.equals("caption")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caption((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caption(null);
                }
            } else if (name.equals("parent")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$parent((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'parent' to null.");
                }
            } else if (name.equals("mimeType")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$mimeType((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$mimeType(null);
                }
            } else if (name.equals("fullImage")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$fullImage(null);
                } else {
                    com.csatimes.dojma.models.Image fullImageObj = com_csatimes_dojma_models_ImageRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$fullImage(fullImageObj);
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

    public static com.csatimes.dojma.models.Attachment copyOrUpdate(Realm realm, com.csatimes.dojma.models.Attachment object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.Attachment) cachedRealmObject;
        }

        com.csatimes.dojma.models.Attachment realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.csatimes.dojma.models.Attachment.class);
            AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Attachment.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Attachment.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_csatimes_dojma_models_AttachmentRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.Attachment copy(Realm realm, com.csatimes.dojma.models.Attachment newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.Attachment) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.Attachment realmObject = realm.createObjectInternal(com.csatimes.dojma.models.Attachment.class, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_AttachmentRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_AttachmentRealmProxyInterface) newObject;
        com_csatimes_dojma_models_AttachmentRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_AttachmentRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$url(realmObjectSource.realmGet$url());
        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectCopy.realmSet$slug(realmObjectSource.realmGet$slug());
        realmObjectCopy.realmSet$description(realmObjectSource.realmGet$description());
        realmObjectCopy.realmSet$caption(realmObjectSource.realmGet$caption());
        realmObjectCopy.realmSet$parent(realmObjectSource.realmGet$parent());
        realmObjectCopy.realmSet$mimeType(realmObjectSource.realmGet$mimeType());

        com.csatimes.dojma.models.Image fullImageObj = realmObjectSource.realmGet$fullImage();
        if (fullImageObj == null) {
            realmObjectCopy.realmSet$fullImage(null);
        } else {
            com.csatimes.dojma.models.Image cachefullImage = (com.csatimes.dojma.models.Image) cache.get(fullImageObj);
            if (cachefullImage != null) {
                realmObjectCopy.realmSet$fullImage(cachefullImage);
            } else {
                realmObjectCopy.realmSet$fullImage(com_csatimes_dojma_models_ImageRealmProxy.copyOrUpdate(realm, fullImageObj, update, cache));
            }
        }
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.Attachment object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.Attachment.class);
        long tableNativePtr = table.getNativePtr();
        AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Attachment.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$url = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        }
        String realmGet$title = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        String realmGet$slug = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$slug();
        if (realmGet$slug != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.slugIndex, rowIndex, realmGet$slug, false);
        }
        String realmGet$description = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        }
        String realmGet$caption = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$caption();
        if (realmGet$caption != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.captionIndex, rowIndex, realmGet$caption, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.parentIndex, rowIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$parent(), false);
        String realmGet$mimeType = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$mimeType();
        if (realmGet$mimeType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.mimeTypeIndex, rowIndex, realmGet$mimeType, false);
        }

        com.csatimes.dojma.models.Image fullImageObj = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$fullImage();
        if (fullImageObj != null) {
            Long cachefullImage = cache.get(fullImageObj);
            if (cachefullImage == null) {
                cachefullImage = com_csatimes_dojma_models_ImageRealmProxy.insert(realm, fullImageObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.fullImageIndex, rowIndex, cachefullImage, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.Attachment.class);
        long tableNativePtr = table.getNativePtr();
        AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Attachment.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.csatimes.dojma.models.Attachment object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.Attachment) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$url = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$url();
            if (realmGet$url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
            }
            String realmGet$title = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
            String realmGet$slug = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$slug();
            if (realmGet$slug != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.slugIndex, rowIndex, realmGet$slug, false);
            }
            String realmGet$description = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            }
            String realmGet$caption = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$caption();
            if (realmGet$caption != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.captionIndex, rowIndex, realmGet$caption, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.parentIndex, rowIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$parent(), false);
            String realmGet$mimeType = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$mimeType();
            if (realmGet$mimeType != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.mimeTypeIndex, rowIndex, realmGet$mimeType, false);
            }

            com.csatimes.dojma.models.Image fullImageObj = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$fullImage();
            if (fullImageObj != null) {
                Long cachefullImage = cache.get(fullImageObj);
                if (cachefullImage == null) {
                    cachefullImage = com_csatimes_dojma_models_ImageRealmProxy.insert(realm, fullImageObj, cache);
                }
                table.setLink(columnInfo.fullImageIndex, rowIndex, cachefullImage, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.Attachment object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.Attachment.class);
        long tableNativePtr = table.getNativePtr();
        AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Attachment.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$url = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
        }
        String realmGet$title = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        String realmGet$slug = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$slug();
        if (realmGet$slug != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.slugIndex, rowIndex, realmGet$slug, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.slugIndex, rowIndex, false);
        }
        String realmGet$description = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
        }
        String realmGet$caption = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$caption();
        if (realmGet$caption != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.captionIndex, rowIndex, realmGet$caption, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.captionIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.parentIndex, rowIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$parent(), false);
        String realmGet$mimeType = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$mimeType();
        if (realmGet$mimeType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.mimeTypeIndex, rowIndex, realmGet$mimeType, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.mimeTypeIndex, rowIndex, false);
        }

        com.csatimes.dojma.models.Image fullImageObj = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$fullImage();
        if (fullImageObj != null) {
            Long cachefullImage = cache.get(fullImageObj);
            if (cachefullImage == null) {
                cachefullImage = com_csatimes_dojma_models_ImageRealmProxy.insertOrUpdate(realm, fullImageObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.fullImageIndex, rowIndex, cachefullImage, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.fullImageIndex, rowIndex);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.Attachment.class);
        long tableNativePtr = table.getNativePtr();
        AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Attachment.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.csatimes.dojma.models.Attachment object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.Attachment) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$url = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$url();
            if (realmGet$url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
            }
            String realmGet$title = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
            String realmGet$slug = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$slug();
            if (realmGet$slug != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.slugIndex, rowIndex, realmGet$slug, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.slugIndex, rowIndex, false);
            }
            String realmGet$description = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
            }
            String realmGet$caption = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$caption();
            if (realmGet$caption != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.captionIndex, rowIndex, realmGet$caption, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.captionIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.parentIndex, rowIndex, ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$parent(), false);
            String realmGet$mimeType = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$mimeType();
            if (realmGet$mimeType != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.mimeTypeIndex, rowIndex, realmGet$mimeType, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.mimeTypeIndex, rowIndex, false);
            }

            com.csatimes.dojma.models.Image fullImageObj = ((com_csatimes_dojma_models_AttachmentRealmProxyInterface) object).realmGet$fullImage();
            if (fullImageObj != null) {
                Long cachefullImage = cache.get(fullImageObj);
                if (cachefullImage == null) {
                    cachefullImage = com_csatimes_dojma_models_ImageRealmProxy.insertOrUpdate(realm, fullImageObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.fullImageIndex, rowIndex, cachefullImage, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.fullImageIndex, rowIndex);
            }
        }
    }

    public static com.csatimes.dojma.models.Attachment createDetachedCopy(com.csatimes.dojma.models.Attachment realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.Attachment unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.Attachment();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.Attachment) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.Attachment) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_AttachmentRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_AttachmentRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_AttachmentRealmProxyInterface realmSource = (com_csatimes_dojma_models_AttachmentRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$url(realmSource.realmGet$url());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$slug(realmSource.realmGet$slug());
        unmanagedCopy.realmSet$description(realmSource.realmGet$description());
        unmanagedCopy.realmSet$caption(realmSource.realmGet$caption());
        unmanagedCopy.realmSet$parent(realmSource.realmGet$parent());
        unmanagedCopy.realmSet$mimeType(realmSource.realmGet$mimeType());

        // Deep copy of fullImage
        unmanagedCopy.realmSet$fullImage(com_csatimes_dojma_models_ImageRealmProxy.createDetachedCopy(realmSource.realmGet$fullImage(), currentDepth + 1, maxDepth, cache));

        return unmanagedObject;
    }

    static com.csatimes.dojma.models.Attachment update(Realm realm, com.csatimes.dojma.models.Attachment realmObject, com.csatimes.dojma.models.Attachment newObject, Map<RealmModel, RealmObjectProxy> cache) {
        com_csatimes_dojma_models_AttachmentRealmProxyInterface realmObjectTarget = (com_csatimes_dojma_models_AttachmentRealmProxyInterface) realmObject;
        com_csatimes_dojma_models_AttachmentRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_AttachmentRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$url(realmObjectSource.realmGet$url());
        realmObjectTarget.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectTarget.realmSet$slug(realmObjectSource.realmGet$slug());
        realmObjectTarget.realmSet$description(realmObjectSource.realmGet$description());
        realmObjectTarget.realmSet$caption(realmObjectSource.realmGet$caption());
        realmObjectTarget.realmSet$parent(realmObjectSource.realmGet$parent());
        realmObjectTarget.realmSet$mimeType(realmObjectSource.realmGet$mimeType());
        com.csatimes.dojma.models.Image fullImageObj = realmObjectSource.realmGet$fullImage();
        if (fullImageObj == null) {
            realmObjectTarget.realmSet$fullImage(null);
        } else {
            com.csatimes.dojma.models.Image cachefullImage = (com.csatimes.dojma.models.Image) cache.get(fullImageObj);
            if (cachefullImage != null) {
                realmObjectTarget.realmSet$fullImage(cachefullImage);
            } else {
                realmObjectTarget.realmSet$fullImage(com_csatimes_dojma_models_ImageRealmProxy.copyOrUpdate(realm, fullImageObj, true, cache));
            }
        }
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Attachment = proxy[");
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
        stringBuilder.append("{slug:");
        stringBuilder.append(realmGet$slug() != null ? realmGet$slug() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{description:");
        stringBuilder.append(realmGet$description() != null ? realmGet$description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{caption:");
        stringBuilder.append(realmGet$caption() != null ? realmGet$caption() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{parent:");
        stringBuilder.append(realmGet$parent());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{mimeType:");
        stringBuilder.append(realmGet$mimeType() != null ? realmGet$mimeType() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{fullImage:");
        stringBuilder.append(realmGet$fullImage() != null ? "Image" : "null");
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
        com_csatimes_dojma_models_AttachmentRealmProxy aAttachment = (com_csatimes_dojma_models_AttachmentRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aAttachment.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aAttachment.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aAttachment.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
