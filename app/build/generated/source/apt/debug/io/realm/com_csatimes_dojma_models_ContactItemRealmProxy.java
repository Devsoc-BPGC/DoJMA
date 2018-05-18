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
public class com_csatimes_dojma_models_ContactItemRealmProxy extends com.csatimes.dojma.models.ContactItem
    implements RealmObjectProxy, com_csatimes_dojma_models_ContactItemRealmProxyInterface {

    static final class ContactItemColumnInfo extends ColumnInfo {
        long typeIndex;
        long nameIndex;
        long numberIndex;
        long emailIndex;
        long sub1Index;
        long sub2Index;
        long iconIndex;
        long idIndex;

        ContactItemColumnInfo(OsSchemaInfo schemaInfo) {
            super(8);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("ContactItem");
            this.typeIndex = addColumnDetails("type", "type", objectSchemaInfo);
            this.nameIndex = addColumnDetails("name", "name", objectSchemaInfo);
            this.numberIndex = addColumnDetails("number", "number", objectSchemaInfo);
            this.emailIndex = addColumnDetails("email", "email", objectSchemaInfo);
            this.sub1Index = addColumnDetails("sub1", "sub1", objectSchemaInfo);
            this.sub2Index = addColumnDetails("sub2", "sub2", objectSchemaInfo);
            this.iconIndex = addColumnDetails("icon", "icon", objectSchemaInfo);
            this.idIndex = addColumnDetails("id", "id", objectSchemaInfo);
        }

        ContactItemColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ContactItemColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ContactItemColumnInfo src = (ContactItemColumnInfo) rawSrc;
            final ContactItemColumnInfo dst = (ContactItemColumnInfo) rawDst;
            dst.typeIndex = src.typeIndex;
            dst.nameIndex = src.nameIndex;
            dst.numberIndex = src.numberIndex;
            dst.emailIndex = src.emailIndex;
            dst.sub1Index = src.sub1Index;
            dst.sub2Index = src.sub2Index;
            dst.iconIndex = src.iconIndex;
            dst.idIndex = src.idIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private ContactItemColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.ContactItem> proxyState;

    com_csatimes_dojma_models_ContactItemRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ContactItemColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.ContactItem>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.typeIndex);
    }

    @Override
    public void realmSet$type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.typeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.typeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.typeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.typeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    @Override
    public void realmSet$name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$number() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.numberIndex);
    }

    @Override
    public void realmSet$number(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.numberIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.numberIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.numberIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.numberIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailIndex);
    }

    @Override
    public void realmSet$email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.emailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$sub1() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.sub1Index);
    }

    @Override
    public void realmSet$sub1(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.sub1Index, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.sub1Index, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.sub1Index);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.sub1Index, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$sub2() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.sub2Index);
    }

    @Override
    public void realmSet$sub2(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.sub2Index, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.sub2Index, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.sub2Index);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.sub2Index, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$icon() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.iconIndex);
    }

    @Override
    public void realmSet$icon(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.iconIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.iconIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.iconIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.iconIndex, value);
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
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.idIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("ContactItem", 8, 0);
        builder.addPersistedProperty("type", RealmFieldType.STRING, !Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("number", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("sub1", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("sub2", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("icon", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ContactItemColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ContactItemColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "ContactItem";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "ContactItem";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.ContactItem createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.csatimes.dojma.models.ContactItem obj = realm.createObjectInternal(com.csatimes.dojma.models.ContactItem.class, true, excludeFields);

        final com_csatimes_dojma_models_ContactItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_ContactItemRealmProxyInterface) obj;
        if (json.has("type")) {
            if (json.isNull("type")) {
                objProxy.realmSet$type(null);
            } else {
                objProxy.realmSet$type((String) json.getString("type"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                objProxy.realmSet$name(null);
            } else {
                objProxy.realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("number")) {
            if (json.isNull("number")) {
                objProxy.realmSet$number(null);
            } else {
                objProxy.realmSet$number((String) json.getString("number"));
            }
        }
        if (json.has("email")) {
            if (json.isNull("email")) {
                objProxy.realmSet$email(null);
            } else {
                objProxy.realmSet$email((String) json.getString("email"));
            }
        }
        if (json.has("sub1")) {
            if (json.isNull("sub1")) {
                objProxy.realmSet$sub1(null);
            } else {
                objProxy.realmSet$sub1((String) json.getString("sub1"));
            }
        }
        if (json.has("sub2")) {
            if (json.isNull("sub2")) {
                objProxy.realmSet$sub2(null);
            } else {
                objProxy.realmSet$sub2((String) json.getString("sub2"));
            }
        }
        if (json.has("icon")) {
            if (json.isNull("icon")) {
                objProxy.realmSet$icon(null);
            } else {
                objProxy.realmSet$icon((String) json.getString("icon"));
            }
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
            } else {
                objProxy.realmSet$id((int) json.getInt("id"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.ContactItem createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.csatimes.dojma.models.ContactItem obj = new com.csatimes.dojma.models.ContactItem();
        final com_csatimes_dojma_models_ContactItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_ContactItemRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$type(null);
                }
            } else if (name.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$name(null);
                }
            } else if (name.equals("number")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$number((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$number(null);
                }
            } else if (name.equals("email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$email(null);
                }
            } else if (name.equals("sub1")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$sub1((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$sub1(null);
                }
            } else if (name.equals("sub2")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$sub2((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$sub2(null);
                }
            } else if (name.equals("icon")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$icon((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$icon(null);
                }
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    public static com.csatimes.dojma.models.ContactItem copyOrUpdate(Realm realm, com.csatimes.dojma.models.ContactItem object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.ContactItem) cachedRealmObject;
        }

        return copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.ContactItem copy(Realm realm, com.csatimes.dojma.models.ContactItem newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.ContactItem) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.ContactItem realmObject = realm.createObjectInternal(com.csatimes.dojma.models.ContactItem.class, false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_ContactItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_ContactItemRealmProxyInterface) newObject;
        com_csatimes_dojma_models_ContactItemRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_ContactItemRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectCopy.realmSet$name(realmObjectSource.realmGet$name());
        realmObjectCopy.realmSet$number(realmObjectSource.realmGet$number());
        realmObjectCopy.realmSet$email(realmObjectSource.realmGet$email());
        realmObjectCopy.realmSet$sub1(realmObjectSource.realmGet$sub1());
        realmObjectCopy.realmSet$sub2(realmObjectSource.realmGet$sub2());
        realmObjectCopy.realmSet$icon(realmObjectSource.realmGet$icon());
        realmObjectCopy.realmSet$id(realmObjectSource.realmGet$id());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.ContactItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.ContactItem.class);
        long tableNativePtr = table.getNativePtr();
        ContactItemColumnInfo columnInfo = (ContactItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.ContactItem.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$type = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
        }
        String realmGet$name = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }
        String realmGet$number = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$number();
        if (realmGet$number != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.numberIndex, rowIndex, realmGet$number, false);
        }
        String realmGet$email = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        }
        String realmGet$sub1 = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$sub1();
        if (realmGet$sub1 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sub1Index, rowIndex, realmGet$sub1, false);
        }
        String realmGet$sub2 = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$sub2();
        if (realmGet$sub2 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sub2Index, rowIndex, realmGet$sub2, false);
        }
        String realmGet$icon = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$icon();
        if (realmGet$icon != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.iconIndex, rowIndex, realmGet$icon, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$id(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.ContactItem.class);
        long tableNativePtr = table.getNativePtr();
        ContactItemColumnInfo columnInfo = (ContactItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.ContactItem.class);
        com.csatimes.dojma.models.ContactItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.ContactItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$type = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
            }
            String realmGet$name = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            }
            String realmGet$number = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$number();
            if (realmGet$number != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.numberIndex, rowIndex, realmGet$number, false);
            }
            String realmGet$email = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
            }
            String realmGet$sub1 = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$sub1();
            if (realmGet$sub1 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.sub1Index, rowIndex, realmGet$sub1, false);
            }
            String realmGet$sub2 = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$sub2();
            if (realmGet$sub2 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.sub2Index, rowIndex, realmGet$sub2, false);
            }
            String realmGet$icon = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$icon();
            if (realmGet$icon != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.iconIndex, rowIndex, realmGet$icon, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$id(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.ContactItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.ContactItem.class);
        long tableNativePtr = table.getNativePtr();
        ContactItemColumnInfo columnInfo = (ContactItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.ContactItem.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$type = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.typeIndex, rowIndex, false);
        }
        String realmGet$name = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }
        String realmGet$number = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$number();
        if (realmGet$number != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.numberIndex, rowIndex, realmGet$number, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.numberIndex, rowIndex, false);
        }
        String realmGet$email = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
        }
        String realmGet$sub1 = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$sub1();
        if (realmGet$sub1 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sub1Index, rowIndex, realmGet$sub1, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sub1Index, rowIndex, false);
        }
        String realmGet$sub2 = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$sub2();
        if (realmGet$sub2 != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sub2Index, rowIndex, realmGet$sub2, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sub2Index, rowIndex, false);
        }
        String realmGet$icon = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$icon();
        if (realmGet$icon != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.iconIndex, rowIndex, realmGet$icon, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.iconIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$id(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.ContactItem.class);
        long tableNativePtr = table.getNativePtr();
        ContactItemColumnInfo columnInfo = (ContactItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.ContactItem.class);
        com.csatimes.dojma.models.ContactItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.ContactItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$type = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.typeIndex, rowIndex, false);
            }
            String realmGet$name = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
            }
            String realmGet$number = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$number();
            if (realmGet$number != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.numberIndex, rowIndex, realmGet$number, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.numberIndex, rowIndex, false);
            }
            String realmGet$email = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
            }
            String realmGet$sub1 = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$sub1();
            if (realmGet$sub1 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.sub1Index, rowIndex, realmGet$sub1, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.sub1Index, rowIndex, false);
            }
            String realmGet$sub2 = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$sub2();
            if (realmGet$sub2 != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.sub2Index, rowIndex, realmGet$sub2, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.sub2Index, rowIndex, false);
            }
            String realmGet$icon = ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$icon();
            if (realmGet$icon != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.iconIndex, rowIndex, realmGet$icon, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.iconIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, ((com_csatimes_dojma_models_ContactItemRealmProxyInterface) object).realmGet$id(), false);
        }
    }

    public static com.csatimes.dojma.models.ContactItem createDetachedCopy(com.csatimes.dojma.models.ContactItem realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.ContactItem unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.ContactItem();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.ContactItem) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.ContactItem) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_ContactItemRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_ContactItemRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_ContactItemRealmProxyInterface realmSource = (com_csatimes_dojma_models_ContactItemRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());
        unmanagedCopy.realmSet$number(realmSource.realmGet$number());
        unmanagedCopy.realmSet$email(realmSource.realmGet$email());
        unmanagedCopy.realmSet$sub1(realmSource.realmGet$sub1());
        unmanagedCopy.realmSet$sub2(realmSource.realmGet$sub2());
        unmanagedCopy.realmSet$icon(realmSource.realmGet$icon());
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("ContactItem = proxy[");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type() != null ? realmGet$type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{number:");
        stringBuilder.append(realmGet$number() != null ? realmGet$number() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email:");
        stringBuilder.append(realmGet$email() != null ? realmGet$email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{sub1:");
        stringBuilder.append(realmGet$sub1() != null ? realmGet$sub1() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{sub2:");
        stringBuilder.append(realmGet$sub2() != null ? realmGet$sub2() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{icon:");
        stringBuilder.append(realmGet$icon() != null ? realmGet$icon() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
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
        com_csatimes_dojma_models_ContactItemRealmProxy aContactItem = (com_csatimes_dojma_models_ContactItemRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aContactItem.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aContactItem.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aContactItem.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
