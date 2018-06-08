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
public class com_csatimes_dojma_models_ImageRealmProxy extends com.csatimes.dojma.models.Image
    implements RealmObjectProxy, com_csatimes_dojma_models_ImageRealmProxyInterface {

    static final class ImageColumnInfo extends ColumnInfo {
        long urlIndex;
        long widthIndex;
        long heightIndex;

        ImageColumnInfo(OsSchemaInfo schemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Image");
            this.urlIndex = addColumnDetails("url", "url", objectSchemaInfo);
            this.widthIndex = addColumnDetails("width", "width", objectSchemaInfo);
            this.heightIndex = addColumnDetails("height", "height", objectSchemaInfo);
        }

        ImageColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ImageColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ImageColumnInfo src = (ImageColumnInfo) rawSrc;
            final ImageColumnInfo dst = (ImageColumnInfo) rawDst;
            dst.urlIndex = src.urlIndex;
            dst.widthIndex = src.widthIndex;
            dst.heightIndex = src.heightIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private ImageColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.Image> proxyState;

    com_csatimes_dojma_models_ImageRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ImageColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.Image>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'url' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$width() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.widthIndex);
    }

    @Override
    public void realmSet$width(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.widthIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.widthIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$height() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.heightIndex);
    }

    @Override
    public void realmSet$height(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.heightIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.heightIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Image", 3, 0);
        builder.addPersistedProperty("url", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("width", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("height", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ImageColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ImageColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Image";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Image";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.Image createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.csatimes.dojma.models.Image obj = null;
        if (update) {
            Table table = realm.getTable(com.csatimes.dojma.models.Image.class);
            ImageColumnInfo columnInfo = (ImageColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Image.class);
            long pkColumnIndex = columnInfo.urlIndex;
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("url")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("url"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Image.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_csatimes_dojma_models_ImageRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("url")) {
                if (json.isNull("url")) {
                    obj = (io.realm.com_csatimes_dojma_models_ImageRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.Image.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_csatimes_dojma_models_ImageRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.Image.class, json.getString("url"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'url'.");
            }
        }

        final com_csatimes_dojma_models_ImageRealmProxyInterface objProxy = (com_csatimes_dojma_models_ImageRealmProxyInterface) obj;
        if (json.has("width")) {
            if (json.isNull("width")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'width' to null.");
            } else {
                objProxy.realmSet$width((int) json.getInt("width"));
            }
        }
        if (json.has("height")) {
            if (json.isNull("height")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'height' to null.");
            } else {
                objProxy.realmSet$height((int) json.getInt("height"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.Image createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.csatimes.dojma.models.Image obj = new com.csatimes.dojma.models.Image();
        final com_csatimes_dojma_models_ImageRealmProxyInterface objProxy = (com_csatimes_dojma_models_ImageRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$url(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("width")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$width((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'width' to null.");
                }
            } else if (name.equals("height")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$height((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'height' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'url'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.csatimes.dojma.models.Image copyOrUpdate(Realm realm, com.csatimes.dojma.models.Image object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.Image) cachedRealmObject;
        }

        com.csatimes.dojma.models.Image realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.csatimes.dojma.models.Image.class);
            ImageColumnInfo columnInfo = (ImageColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Image.class);
            long pkColumnIndex = columnInfo.urlIndex;
            String value = ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$url();
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
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Image.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_csatimes_dojma_models_ImageRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.Image copy(Realm realm, com.csatimes.dojma.models.Image newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.Image) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.Image realmObject = realm.createObjectInternal(com.csatimes.dojma.models.Image.class, ((com_csatimes_dojma_models_ImageRealmProxyInterface) newObject).realmGet$url(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_ImageRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_ImageRealmProxyInterface) newObject;
        com_csatimes_dojma_models_ImageRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_ImageRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$width(realmObjectSource.realmGet$width());
        realmObjectCopy.realmSet$height(realmObjectSource.realmGet$height());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.Image object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.Image.class);
        long tableNativePtr = table.getNativePtr();
        ImageColumnInfo columnInfo = (ImageColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Image.class);
        long pkColumnIndex = columnInfo.urlIndex;
        String primaryKeyValue = ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$url();
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
        Table.nativeSetLong(tableNativePtr, columnInfo.widthIndex, rowIndex, ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$width(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.heightIndex, rowIndex, ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$height(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.Image.class);
        long tableNativePtr = table.getNativePtr();
        ImageColumnInfo columnInfo = (ImageColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Image.class);
        long pkColumnIndex = columnInfo.urlIndex;
        com.csatimes.dojma.models.Image object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.Image) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$url();
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
            Table.nativeSetLong(tableNativePtr, columnInfo.widthIndex, rowIndex, ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$width(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.heightIndex, rowIndex, ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$height(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.Image object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.Image.class);
        long tableNativePtr = table.getNativePtr();
        ImageColumnInfo columnInfo = (ImageColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Image.class);
        long pkColumnIndex = columnInfo.urlIndex;
        String primaryKeyValue = ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$url();
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
        Table.nativeSetLong(tableNativePtr, columnInfo.widthIndex, rowIndex, ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$width(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.heightIndex, rowIndex, ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$height(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.Image.class);
        long tableNativePtr = table.getNativePtr();
        ImageColumnInfo columnInfo = (ImageColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.Image.class);
        long pkColumnIndex = columnInfo.urlIndex;
        com.csatimes.dojma.models.Image object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.Image) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$url();
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
            Table.nativeSetLong(tableNativePtr, columnInfo.widthIndex, rowIndex, ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$width(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.heightIndex, rowIndex, ((com_csatimes_dojma_models_ImageRealmProxyInterface) object).realmGet$height(), false);
        }
    }

    public static com.csatimes.dojma.models.Image createDetachedCopy(com.csatimes.dojma.models.Image realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.Image unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.Image();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.Image) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.Image) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_ImageRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_ImageRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_ImageRealmProxyInterface realmSource = (com_csatimes_dojma_models_ImageRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$url(realmSource.realmGet$url());
        unmanagedCopy.realmSet$width(realmSource.realmGet$width());
        unmanagedCopy.realmSet$height(realmSource.realmGet$height());

        return unmanagedObject;
    }

    static com.csatimes.dojma.models.Image update(Realm realm, com.csatimes.dojma.models.Image realmObject, com.csatimes.dojma.models.Image newObject, Map<RealmModel, RealmObjectProxy> cache) {
        com_csatimes_dojma_models_ImageRealmProxyInterface realmObjectTarget = (com_csatimes_dojma_models_ImageRealmProxyInterface) realmObject;
        com_csatimes_dojma_models_ImageRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_ImageRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$width(realmObjectSource.realmGet$width());
        realmObjectTarget.realmSet$height(realmObjectSource.realmGet$height());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Image = proxy[");
        stringBuilder.append("{url:");
        stringBuilder.append(realmGet$url() != null ? realmGet$url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{width:");
        stringBuilder.append(realmGet$width());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{height:");
        stringBuilder.append(realmGet$height());
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
        com_csatimes_dojma_models_ImageRealmProxy aImage = (com_csatimes_dojma_models_ImageRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aImage.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aImage.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aImage.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
