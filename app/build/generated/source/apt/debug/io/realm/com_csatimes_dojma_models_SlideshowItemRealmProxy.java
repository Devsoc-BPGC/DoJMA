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
public class com_csatimes_dojma_models_SlideshowItemRealmProxy extends com.csatimes.dojma.models.SlideshowItem
    implements RealmObjectProxy, com_csatimes_dojma_models_SlideshowItemRealmProxyInterface {

    static final class SlideshowItemColumnInfo extends ColumnInfo {
        long imageUrlIndex;
        long titleIndex;
        long subTitleIndex;

        SlideshowItemColumnInfo(OsSchemaInfo schemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("SlideshowItem");
            this.imageUrlIndex = addColumnDetails("imageUrl", "imageUrl", objectSchemaInfo);
            this.titleIndex = addColumnDetails("title", "title", objectSchemaInfo);
            this.subTitleIndex = addColumnDetails("subTitle", "subTitle", objectSchemaInfo);
        }

        SlideshowItemColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new SlideshowItemColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final SlideshowItemColumnInfo src = (SlideshowItemColumnInfo) rawSrc;
            final SlideshowItemColumnInfo dst = (SlideshowItemColumnInfo) rawDst;
            dst.imageUrlIndex = src.imageUrlIndex;
            dst.titleIndex = src.titleIndex;
            dst.subTitleIndex = src.subTitleIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private SlideshowItemColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.SlideshowItem> proxyState;

    com_csatimes_dojma_models_SlideshowItemRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (SlideshowItemColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.SlideshowItem>(this);
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

    @Override
    @SuppressWarnings("cast")
    public String realmGet$subTitle() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.subTitleIndex);
    }

    @Override
    public void realmSet$subTitle(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.subTitleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.subTitleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.subTitleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.subTitleIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("SlideshowItem", 3, 0);
        builder.addPersistedProperty("imageUrl", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("subTitle", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static SlideshowItemColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new SlideshowItemColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "SlideshowItem";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "SlideshowItem";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.SlideshowItem createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.csatimes.dojma.models.SlideshowItem obj = realm.createObjectInternal(com.csatimes.dojma.models.SlideshowItem.class, true, excludeFields);

        final com_csatimes_dojma_models_SlideshowItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) obj;
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
        if (json.has("subTitle")) {
            if (json.isNull("subTitle")) {
                objProxy.realmSet$subTitle(null);
            } else {
                objProxy.realmSet$subTitle((String) json.getString("subTitle"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.SlideshowItem createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.csatimes.dojma.models.SlideshowItem obj = new com.csatimes.dojma.models.SlideshowItem();
        final com_csatimes_dojma_models_SlideshowItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) obj;
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
            } else if (name.equals("subTitle")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$subTitle((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$subTitle(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    public static com.csatimes.dojma.models.SlideshowItem copyOrUpdate(Realm realm, com.csatimes.dojma.models.SlideshowItem object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.SlideshowItem) cachedRealmObject;
        }

        return copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.SlideshowItem copy(Realm realm, com.csatimes.dojma.models.SlideshowItem newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.SlideshowItem) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.SlideshowItem realmObject = realm.createObjectInternal(com.csatimes.dojma.models.SlideshowItem.class, false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_SlideshowItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) newObject;
        com_csatimes_dojma_models_SlideshowItemRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$imageUrl(realmObjectSource.realmGet$imageUrl());
        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectCopy.realmSet$subTitle(realmObjectSource.realmGet$subTitle());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.SlideshowItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.SlideshowItem.class);
        long tableNativePtr = table.getNativePtr();
        SlideshowItemColumnInfo columnInfo = (SlideshowItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.SlideshowItem.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$imageUrl = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$imageUrl();
        if (realmGet$imageUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
        }
        String realmGet$title = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        String realmGet$subTitle = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$subTitle();
        if (realmGet$subTitle != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subTitleIndex, rowIndex, realmGet$subTitle, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.SlideshowItem.class);
        long tableNativePtr = table.getNativePtr();
        SlideshowItemColumnInfo columnInfo = (SlideshowItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.SlideshowItem.class);
        com.csatimes.dojma.models.SlideshowItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.SlideshowItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$imageUrl = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$imageUrl();
            if (realmGet$imageUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
            }
            String realmGet$title = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
            String realmGet$subTitle = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$subTitle();
            if (realmGet$subTitle != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.subTitleIndex, rowIndex, realmGet$subTitle, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.SlideshowItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.SlideshowItem.class);
        long tableNativePtr = table.getNativePtr();
        SlideshowItemColumnInfo columnInfo = (SlideshowItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.SlideshowItem.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$imageUrl = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$imageUrl();
        if (realmGet$imageUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, false);
        }
        String realmGet$title = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        String realmGet$subTitle = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$subTitle();
        if (realmGet$subTitle != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subTitleIndex, rowIndex, realmGet$subTitle, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.subTitleIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.SlideshowItem.class);
        long tableNativePtr = table.getNativePtr();
        SlideshowItemColumnInfo columnInfo = (SlideshowItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.SlideshowItem.class);
        com.csatimes.dojma.models.SlideshowItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.SlideshowItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$imageUrl = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$imageUrl();
            if (realmGet$imageUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, realmGet$imageUrl, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imageUrlIndex, rowIndex, false);
            }
            String realmGet$title = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
            String realmGet$subTitle = ((com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) object).realmGet$subTitle();
            if (realmGet$subTitle != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.subTitleIndex, rowIndex, realmGet$subTitle, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.subTitleIndex, rowIndex, false);
            }
        }
    }

    public static com.csatimes.dojma.models.SlideshowItem createDetachedCopy(com.csatimes.dojma.models.SlideshowItem realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.SlideshowItem unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.SlideshowItem();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.SlideshowItem) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.SlideshowItem) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_SlideshowItemRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_SlideshowItemRealmProxyInterface realmSource = (com_csatimes_dojma_models_SlideshowItemRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$imageUrl(realmSource.realmGet$imageUrl());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$subTitle(realmSource.realmGet$subTitle());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("SlideshowItem = proxy[");
        stringBuilder.append("{imageUrl:");
        stringBuilder.append(realmGet$imageUrl() != null ? realmGet$imageUrl() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{subTitle:");
        stringBuilder.append(realmGet$subTitle() != null ? realmGet$subTitle() : "null");
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
        com_csatimes_dojma_models_SlideshowItemRealmProxy aSlideshowItem = (com_csatimes_dojma_models_SlideshowItemRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aSlideshowItem.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aSlideshowItem.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aSlideshowItem.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
