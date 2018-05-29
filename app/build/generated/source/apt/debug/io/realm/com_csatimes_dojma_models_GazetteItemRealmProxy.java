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
public class com_csatimes_dojma_models_GazetteItemRealmProxy extends com.csatimes.dojma.models.GazetteItem
    implements RealmObjectProxy, com_csatimes_dojma_models_GazetteItemRealmProxyInterface {

    static final class GazetteItemColumnInfo extends ColumnInfo {
        long titleIndex;
        long urlIndex;
        long dateIndex;
        long imageUrlIndex;
        long timeIndex;
        long keyIndex;

        GazetteItemColumnInfo(OsSchemaInfo schemaInfo) {
            super(6);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("GazetteItem");
            this.titleIndex = addColumnDetails("title", "title", objectSchemaInfo);
            this.urlIndex = addColumnDetails("url", "url", objectSchemaInfo);
            this.dateIndex = addColumnDetails("date", "date", objectSchemaInfo);
            this.imageUrlIndex = addColumnDetails("imageUrl", "imageUrl", objectSchemaInfo);
            this.timeIndex = addColumnDetails("time", "time", objectSchemaInfo);
            this.keyIndex = addColumnDetails("key", "key", objectSchemaInfo);
        }

        GazetteItemColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new GazetteItemColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final GazetteItemColumnInfo src = (GazetteItemColumnInfo) rawSrc;
            final GazetteItemColumnInfo dst = (GazetteItemColumnInfo) rawDst;
            dst.titleIndex = src.titleIndex;
            dst.urlIndex = src.urlIndex;
            dst.dateIndex = src.dateIndex;
            dst.imageUrlIndex = src.imageUrlIndex;
            dst.timeIndex = src.timeIndex;
            dst.keyIndex = src.keyIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private GazetteItemColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.GazetteItem> proxyState;

    com_csatimes_dojma_models_GazetteItemRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (GazetteItemColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.GazetteItem>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
    public String realmGet$imageUrl() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imageUrlIndex);
    }

    @Override
    public void realmSet$imageUrl(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.imageUrlIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.imageUrlIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.imageUrlIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.imageUrlIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$time() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.timeIndex);
    }

    @Override
    public void realmSet$time(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.timeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.timeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$key() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.keyIndex);
    }

    @Override
    public void realmSet$key(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'key' cannot be changed after object was created.");
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("GazetteItem", 6, 0);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("date", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("imageUrl", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("time", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("key", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static GazetteItemColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new GazetteItemColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "GazetteItem";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "GazetteItem";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.GazetteItem createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.csatimes.dojma.models.GazetteItem obj = null;
        if (update) {
            Table table = realm.getTable(com.csatimes.dojma.models.GazetteItem.class);
            GazetteItemColumnInfo columnInfo = (GazetteItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.GazetteItem.class);
            long pkColumnIndex = columnInfo.keyIndex;
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("key")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("key"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.GazetteItem.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("key")) {
                if (json.isNull("key")) {
                    obj = (io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.GazetteItem.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.GazetteItem.class, json.getString("key"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'key'.");
            }
        }

        final com_csatimes_dojma_models_GazetteItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_GazetteItemRealmProxyInterface) obj;
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("url")) {
            if (json.isNull("url")) {
                objProxy.realmSet$url(null);
            } else {
                objProxy.realmSet$url((String) json.getString("url"));
            }
        }
        if (json.has("date")) {
            if (json.isNull("date")) {
                objProxy.realmSet$date(null);
            } else {
                objProxy.realmSet$date((String) json.getString("date"));
            }
        }
        if (json.has("imageUrl")) {
            if (json.isNull("imageUrl")) {
                objProxy.realmSet$imageUrl(null);
            } else {
                objProxy.realmSet$imageUrl((String) json.getString("imageUrl"));
            }
        }
        if (json.has("time")) {
            if (json.isNull("time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
            } else {
                objProxy.realmSet$time((long) json.getLong("time"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.GazetteItem createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.csatimes.dojma.models.GazetteItem obj = new com.csatimes.dojma.models.GazetteItem();
        final com_csatimes_dojma_models_GazetteItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_GazetteItemRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else if (name.equals("url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$url(null);
                }
            } else if (name.equals("date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$date((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$date(null);
                }
            } else if (name.equals("imageUrl")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$imageUrl((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$imageUrl(null);
                }
            } else if (name.equals("time")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$time((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
                }
            } else if (name.equals("key")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$key((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$key(null);
                }
                jsonHasPrimaryKey = true;
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'key'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.csatimes.dojma.models.GazetteItem copyOrUpdate(Realm realm, com.csatimes.dojma.models.GazetteItem object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.GazetteItem) cachedRealmObject;
        }

        com.csatimes.dojma.models.GazetteItem realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.csatimes.dojma.models.GazetteItem.class);
            GazetteItemColumnInfo columnInfo = (GazetteItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.GazetteItem.class);
            long pkColumnIndex = columnInfo.keyIndex;
            String value = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$key();
            long rowIndex = Table.NO_MATCH;
            if (value == null) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, value);
            }
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.GazetteItem.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.GazetteItem copy(Realm realm, com.csatimes.dojma.models.GazetteItem newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.GazetteItem) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.GazetteItem realmObject = realm.createObjectInternal(com.csatimes.dojma.models.GazetteItem.class, ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) newObject).realmGet$key(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_GazetteItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_GazetteItemRealmProxyInterface) newObject;
        com_csatimes_dojma_models_GazetteItemRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_GazetteItemRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectCopy.realmSet$url(realmObjectSource.realmGet$url());
        realmObjectCopy.realmSet$date(realmObjectSource.realmGet$date());
        realmObjectCopy.realmSet$imageUrl(realmObjectSource.realmGet$imageUrl());
        realmObjectCopy.realmSet$time(realmObjectSource.realmGet$time());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.GazetteItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.GazetteItem.class);
        long tableNativePtr = table.getNativePtr();
        GazetteItemColumnInfo columnInfo = (GazetteItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.GazetteItem.class);
        long pkColumnIndex = columnInfo.keyIndex;
        String primaryKeyValue = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$key();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$title = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        String realmGet$url = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        }
        String realmGet$date = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date, false);
        }
        String realmGet$imageUrl = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$imageUrl();
        if (realmGet$imageUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.timeIndex, rowIndex, ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$time(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.GazetteItem.class);
        long tableNativePtr = table.getNativePtr();
        GazetteItemColumnInfo columnInfo = (GazetteItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.GazetteItem.class);
        long pkColumnIndex = columnInfo.keyIndex;
        com.csatimes.dojma.models.GazetteItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.GazetteItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$key();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$title = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
            String realmGet$url = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$url();
            if (realmGet$url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
            }
            String realmGet$date = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date, false);
            }
            String realmGet$imageUrl = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$imageUrl();
            if (realmGet$imageUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.timeIndex, rowIndex, ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$time(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.GazetteItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.GazetteItem.class);
        long tableNativePtr = table.getNativePtr();
        GazetteItemColumnInfo columnInfo = (GazetteItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.GazetteItem.class);
        long pkColumnIndex = columnInfo.keyIndex;
        String primaryKeyValue = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$key();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$title = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        String realmGet$url = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
        }
        String realmGet$date = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
        }
        String realmGet$imageUrl = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$imageUrl();
        if (realmGet$imageUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.timeIndex, rowIndex, ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$time(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.GazetteItem.class);
        long tableNativePtr = table.getNativePtr();
        GazetteItemColumnInfo columnInfo = (GazetteItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.GazetteItem.class);
        long pkColumnIndex = columnInfo.keyIndex;
        com.csatimes.dojma.models.GazetteItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.GazetteItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$key();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$title = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
            String realmGet$url = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$url();
            if (realmGet$url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
            }
            String realmGet$date = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
            }
            String realmGet$imageUrl = ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$imageUrl();
            if (realmGet$imageUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.timeIndex, rowIndex, ((com_csatimes_dojma_models_GazetteItemRealmProxyInterface) object).realmGet$time(), false);
        }
    }

    public static com.csatimes.dojma.models.GazetteItem createDetachedCopy(com.csatimes.dojma.models.GazetteItem realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.GazetteItem unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.GazetteItem();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.GazetteItem) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.GazetteItem) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_GazetteItemRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_GazetteItemRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_GazetteItemRealmProxyInterface realmSource = (com_csatimes_dojma_models_GazetteItemRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$url(realmSource.realmGet$url());
        unmanagedCopy.realmSet$date(realmSource.realmGet$date());
        unmanagedCopy.realmSet$imageUrl(realmSource.realmGet$imageUrl());
        unmanagedCopy.realmSet$time(realmSource.realmGet$time());
        unmanagedCopy.realmSet$key(realmSource.realmGet$key());

        return unmanagedObject;
    }

    static com.csatimes.dojma.models.GazetteItem update(Realm realm, com.csatimes.dojma.models.GazetteItem realmObject, com.csatimes.dojma.models.GazetteItem newObject, Map<RealmModel, RealmObjectProxy> cache) {
        com_csatimes_dojma_models_GazetteItemRealmProxyInterface realmObjectTarget = (com_csatimes_dojma_models_GazetteItemRealmProxyInterface) realmObject;
        com_csatimes_dojma_models_GazetteItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_GazetteItemRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectTarget.realmSet$url(realmObjectSource.realmGet$url());
        realmObjectTarget.realmSet$date(realmObjectSource.realmGet$date());
        realmObjectTarget.realmSet$imageUrl(realmObjectSource.realmGet$imageUrl());
        realmObjectTarget.realmSet$time(realmObjectSource.realmGet$time());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("GazetteItem = proxy[");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{url:");
        stringBuilder.append(realmGet$url() != null ? realmGet$url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(realmGet$date() != null ? realmGet$date() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{imageUrl:");
        stringBuilder.append(realmGet$imageUrl() != null ? realmGet$imageUrl() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time:");
        stringBuilder.append(realmGet$time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{key:");
        stringBuilder.append(realmGet$key() != null ? realmGet$key() : "null");
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
        com_csatimes_dojma_models_GazetteItemRealmProxy aGazetteItem = (com_csatimes_dojma_models_GazetteItemRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aGazetteItem.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aGazetteItem.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aGazetteItem.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
