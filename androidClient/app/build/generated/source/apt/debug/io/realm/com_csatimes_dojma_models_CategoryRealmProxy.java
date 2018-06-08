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
public class com_csatimes_dojma_models_CategoryRealmProxy extends com.csatimes.dojma.models.Category
    implements RealmObjectProxy, com_csatimes_dojma_models_CategoryRealmProxyInterface {

    static final class CategoryColumnInfo extends ColumnInfo {
        long idIndex;
        long titleIndex;
        long descriptionIndex;
        long postCountIndex;

        CategoryColumnInfo(OsSchemaInfo schemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Category");
            this.idIndex = addColumnDetails("id", "id", objectSchemaInfo);
            this.titleIndex = addColumnDetails("title", "title", objectSchemaInfo);
            this.descriptionIndex = addColumnDetails("description", "description", objectSchemaInfo);
            this.postCountIndex = addColumnDetails("postCount", "postCount", objectSchemaInfo);
        }

        CategoryColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new CategoryColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final CategoryColumnInfo src = (CategoryColumnInfo) rawSrc;
            final CategoryColumnInfo dst = (CategoryColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.titleIndex = src.titleIndex;
            dst.descriptionIndex = src.descriptionIndex;
            dst.postCountIndex = src.postCountIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private CategoryColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.Category> proxyState;

    com_csatimes_dojma_models_CategoryRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (CategoryColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.Category>(this);
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
    public String realmGet$postCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.postCountIndex);
    }

    @Override
    public void realmSet$postCount(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.postCountIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.postCountIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.postCountIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.postCountIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Category", 4, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("postCount", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static CategoryColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new CategoryColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Category";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Category";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.Category createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.csatimes.dojma.models.Category obj = null;
        if (update) {
            Table table = realm.getTable(com.csatimes.dojma.models.Category.class);
            CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Category.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Category.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_csatimes_dojma_models_CategoryRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.com_csatimes_dojma_models_CategoryRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.Category.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_csatimes_dojma_models_CategoryRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.Category.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final com_csatimes_dojma_models_CategoryRealmProxyInterface objProxy = (com_csatimes_dojma_models_CategoryRealmProxyInterface) obj;
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("description")) {
            if (json.isNull("description")) {
                objProxy.realmSet$description(null);
            } else {
                objProxy.realmSet$description((String) json.getString("description"));
            }
        }
        if (json.has("postCount")) {
            if (json.isNull("postCount")) {
                objProxy.realmSet$postCount(null);
            } else {
                objProxy.realmSet$postCount((String) json.getString("postCount"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.Category createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.csatimes.dojma.models.Category obj = new com.csatimes.dojma.models.Category();
        final com_csatimes_dojma_models_CategoryRealmProxyInterface objProxy = (com_csatimes_dojma_models_CategoryRealmProxyInterface) obj;
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
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else if (name.equals("description")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$description((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$description(null);
                }
            } else if (name.equals("postCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$postCount((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$postCount(null);
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

    public static com.csatimes.dojma.models.Category copyOrUpdate(Realm realm, com.csatimes.dojma.models.Category object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.Category) cachedRealmObject;
        }

        com.csatimes.dojma.models.Category realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.csatimes.dojma.models.Category.class);
            CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Category.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Category.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_csatimes_dojma_models_CategoryRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.Category copy(Realm realm, com.csatimes.dojma.models.Category newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.Category) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.Category realmObject = realm.createObjectInternal(com.csatimes.dojma.models.Category.class, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_CategoryRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_CategoryRealmProxyInterface) newObject;
        com_csatimes_dojma_models_CategoryRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_CategoryRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectCopy.realmSet$description(realmObjectSource.realmGet$description());
        realmObjectCopy.realmSet$postCount(realmObjectSource.realmGet$postCount());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.Category object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.Category.class);
        long tableNativePtr = table.getNativePtr();
        CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Category.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$title = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        String realmGet$description = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        }
        String realmGet$postCount = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$postCount();
        if (realmGet$postCount != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.postCountIndex, rowIndex, realmGet$postCount, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.Category.class);
        long tableNativePtr = table.getNativePtr();
        CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Category.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.csatimes.dojma.models.Category object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.Category) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$title = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
            String realmGet$description = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            }
            String realmGet$postCount = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$postCount();
            if (realmGet$postCount != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.postCountIndex, rowIndex, realmGet$postCount, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.Category object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.Category.class);
        long tableNativePtr = table.getNativePtr();
        CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Category.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$title = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        String realmGet$description = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
        }
        String realmGet$postCount = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$postCount();
        if (realmGet$postCount != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.postCountIndex, rowIndex, realmGet$postCount, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.postCountIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.Category.class);
        long tableNativePtr = table.getNativePtr();
        CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Category.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.csatimes.dojma.models.Category object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.Category) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$title = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
            String realmGet$description = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
            }
            String realmGet$postCount = ((com_csatimes_dojma_models_CategoryRealmProxyInterface) object).realmGet$postCount();
            if (realmGet$postCount != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.postCountIndex, rowIndex, realmGet$postCount, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.postCountIndex, rowIndex, false);
            }
        }
    }

    public static com.csatimes.dojma.models.Category createDetachedCopy(com.csatimes.dojma.models.Category realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.Category unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.Category();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.Category) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.Category) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_CategoryRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_CategoryRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_CategoryRealmProxyInterface realmSource = (com_csatimes_dojma_models_CategoryRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$description(realmSource.realmGet$description());
        unmanagedCopy.realmSet$postCount(realmSource.realmGet$postCount());

        return unmanagedObject;
    }

    static com.csatimes.dojma.models.Category update(Realm realm, com.csatimes.dojma.models.Category realmObject, com.csatimes.dojma.models.Category newObject, Map<RealmModel, RealmObjectProxy> cache) {
        com_csatimes_dojma_models_CategoryRealmProxyInterface realmObjectTarget = (com_csatimes_dojma_models_CategoryRealmProxyInterface) realmObject;
        com_csatimes_dojma_models_CategoryRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_CategoryRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectTarget.realmSet$description(realmObjectSource.realmGet$description());
        realmObjectTarget.realmSet$postCount(realmObjectSource.realmGet$postCount());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Category = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{description:");
        stringBuilder.append(realmGet$description() != null ? realmGet$description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{postCount:");
        stringBuilder.append(realmGet$postCount() != null ? realmGet$postCount() : "null");
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
        com_csatimes_dojma_models_CategoryRealmProxy aCategory = (com_csatimes_dojma_models_CategoryRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCategory.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCategory.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCategory.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
