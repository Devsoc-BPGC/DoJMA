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
public class com_csatimes_dojma_models_MessItemRealmProxy extends com.csatimes.dojma.models.MessItem
    implements RealmObjectProxy, com_csatimes_dojma_models_MessItemRealmProxyInterface {

    static final class MessItemColumnInfo extends ColumnInfo {
        long imageUrlIndex;
        long titleIndex;

        MessItemColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("MessItem");
            this.imageUrlIndex = addColumnDetails("imageUrl", "imageUrl", objectSchemaInfo);
            this.titleIndex = addColumnDetails("title", "title", objectSchemaInfo);
        }

        MessItemColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new MessItemColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final MessItemColumnInfo src = (MessItemColumnInfo) rawSrc;
            final MessItemColumnInfo dst = (MessItemColumnInfo) rawDst;
            dst.imageUrlIndex = src.imageUrlIndex;
            dst.titleIndex = src.titleIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private MessItemColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.MessItem> proxyState;

    com_csatimes_dojma_models_MessItemRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (MessItemColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.MessItem>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("MessItem", 2, 0);
        builder.addPersistedProperty("imageUrl", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static MessItemColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new MessItemColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "MessItem";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "MessItem";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.MessItem createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.csatimes.dojma.models.MessItem obj = realm.createObjectInternal(com.csatimes.dojma.models.MessItem.class, true, excludeFields);

        final com_csatimes_dojma_models_MessItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_MessItemRealmProxyInterface) obj;
        if (json.has("imageUrl")) {
            if (json.isNull("imageUrl")) {
                objProxy.realmSet$imageUrl(null);
            } else {
                objProxy.realmSet$imageUrl((String) json.getString("imageUrl"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.MessItem createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.csatimes.dojma.models.MessItem obj = new com.csatimes.dojma.models.MessItem();
        final com_csatimes_dojma_models_MessItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_MessItemRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("imageUrl")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$imageUrl((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$imageUrl(null);
                }
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    public static com.csatimes.dojma.models.MessItem copyOrUpdate(Realm realm, com.csatimes.dojma.models.MessItem object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.MessItem) cachedRealmObject;
        }

        return copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.MessItem copy(Realm realm, com.csatimes.dojma.models.MessItem newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.MessItem) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.MessItem realmObject = realm.createObjectInternal(com.csatimes.dojma.models.MessItem.class, false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_MessItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_MessItemRealmProxyInterface) newObject;
        com_csatimes_dojma_models_MessItemRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_MessItemRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$imageUrl(realmObjectSource.realmGet$imageUrl());
        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.MessItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.MessItem.class);
        long tableNativePtr = table.getNativePtr();
        MessItemColumnInfo columnInfo = (MessItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.MessItem.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$imageUrl = ((com_csatimes_dojma_models_MessItemRealmProxyInterface) object).realmGet$imageUrl();
        if (realmGet$imageUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
        }
        String realmGet$title = ((com_csatimes_dojma_models_MessItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.MessItem.class);
        long tableNativePtr = table.getNativePtr();
        MessItemColumnInfo columnInfo = (MessItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.MessItem.class);
        com.csatimes.dojma.models.MessItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.MessItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$imageUrl = ((com_csatimes_dojma_models_MessItemRealmProxyInterface) object).realmGet$imageUrl();
            if (realmGet$imageUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
            }
            String realmGet$title = ((com_csatimes_dojma_models_MessItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.MessItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.MessItem.class);
        long tableNativePtr = table.getNativePtr();
        MessItemColumnInfo columnInfo = (MessItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.MessItem.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$imageUrl = ((com_csatimes_dojma_models_MessItemRealmProxyInterface) object).realmGet$imageUrl();
        if (realmGet$imageUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, false);
        }
        String realmGet$title = ((com_csatimes_dojma_models_MessItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.MessItem.class);
        long tableNativePtr = table.getNativePtr();
        MessItemColumnInfo columnInfo = (MessItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.MessItem.class);
        com.csatimes.dojma.models.MessItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.MessItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$imageUrl = ((com_csatimes_dojma_models_MessItemRealmProxyInterface) object).realmGet$imageUrl();
            if (realmGet$imageUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, false);
            }
            String realmGet$title = ((com_csatimes_dojma_models_MessItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
        }
    }

    public static com.csatimes.dojma.models.MessItem createDetachedCopy(com.csatimes.dojma.models.MessItem realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.MessItem unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.MessItem();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.MessItem) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.MessItem) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_MessItemRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_MessItemRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_MessItemRealmProxyInterface realmSource = (com_csatimes_dojma_models_MessItemRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$imageUrl(realmSource.realmGet$imageUrl());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("MessItem = proxy[");
        stringBuilder.append("{imageUrl:");
        stringBuilder.append(realmGet$imageUrl() != null ? realmGet$imageUrl() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
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
        com_csatimes_dojma_models_MessItemRealmProxy aMessItem = (com_csatimes_dojma_models_MessItemRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aMessItem.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aMessItem.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aMessItem.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
