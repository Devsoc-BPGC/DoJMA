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
public class com_csatimes_dojma_models_EventItemRealmProxy extends com.csatimes.dojma.models.EventItem
    implements RealmObjectProxy, com_csatimes_dojma_models_EventItemRealmProxyInterface {

    static final class EventItemColumnInfo extends ColumnInfo {
        long keyIndex;
        long titleIndex;
        long startDateIndex;
        long startTimeIndex;
        long descIndex;
        long locationIndex;
        long timeIndex;
        long startDateFormattedIndex;
        long startTimeFormattedIndex;
        long startDateObjIndex;

        EventItemColumnInfo(OsSchemaInfo schemaInfo) {
            super(10);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("EventItem");
            this.keyIndex = addColumnDetails("key", "key", objectSchemaInfo);
            this.titleIndex = addColumnDetails("title", "title", objectSchemaInfo);
            this.startDateIndex = addColumnDetails("startDate", "startDate", objectSchemaInfo);
            this.startTimeIndex = addColumnDetails("startTime", "startTime", objectSchemaInfo);
            this.descIndex = addColumnDetails("desc", "desc", objectSchemaInfo);
            this.locationIndex = addColumnDetails("location", "location", objectSchemaInfo);
            this.timeIndex = addColumnDetails("time", "time", objectSchemaInfo);
            this.startDateFormattedIndex = addColumnDetails("startDateFormatted", "startDateFormatted", objectSchemaInfo);
            this.startTimeFormattedIndex = addColumnDetails("startTimeFormatted", "startTimeFormatted", objectSchemaInfo);
            this.startDateObjIndex = addColumnDetails("startDateObj", "startDateObj", objectSchemaInfo);
        }

        EventItemColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new EventItemColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final EventItemColumnInfo src = (EventItemColumnInfo) rawSrc;
            final EventItemColumnInfo dst = (EventItemColumnInfo) rawDst;
            dst.keyIndex = src.keyIndex;
            dst.titleIndex = src.titleIndex;
            dst.startDateIndex = src.startDateIndex;
            dst.startTimeIndex = src.startTimeIndex;
            dst.descIndex = src.descIndex;
            dst.locationIndex = src.locationIndex;
            dst.timeIndex = src.timeIndex;
            dst.startDateFormattedIndex = src.startDateFormattedIndex;
            dst.startTimeFormattedIndex = src.startTimeFormattedIndex;
            dst.startDateObjIndex = src.startDateObjIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private EventItemColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.EventItem> proxyState;

    com_csatimes_dojma_models_EventItemRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (EventItemColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.EventItem>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
                throw new IllegalArgumentException("Trying to set non-nullable field 'title' to null.");
            }
            row.getTable().setString(columnInfo.titleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'title' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.titleIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$startDate() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.startDateIndex);
    }

    @Override
    public void realmSet$startDate(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'startDate' to null.");
            }
            row.getTable().setString(columnInfo.startDateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'startDate' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.startDateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$startTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.startTimeIndex);
    }

    @Override
    public void realmSet$startTime(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'startTime' to null.");
            }
            row.getTable().setString(columnInfo.startTimeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'startTime' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.startTimeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$desc() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.descIndex);
    }

    @Override
    public void realmSet$desc(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.descIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.descIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.descIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.descIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$location() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.locationIndex);
    }

    @Override
    public void realmSet$location(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.locationIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.locationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.locationIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.locationIndex, value);
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
    public String realmGet$startDateFormatted() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.startDateFormattedIndex);
    }

    @Override
    public void realmSet$startDateFormatted(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.startDateFormattedIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.startDateFormattedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.startDateFormattedIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.startDateFormattedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$startTimeFormatted() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.startTimeFormattedIndex);
    }

    @Override
    public void realmSet$startTimeFormatted(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.startTimeFormattedIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.startTimeFormattedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.startTimeFormattedIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.startTimeFormattedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Date realmGet$startDateObj() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.startDateObjIndex)) {
            return null;
        }
        return (java.util.Date) proxyState.getRow$realm().getDate(columnInfo.startDateObjIndex);
    }

    @Override
    public void realmSet$startDateObj(Date value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.startDateObjIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDate(columnInfo.startDateObjIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.startDateObjIndex);
            return;
        }
        proxyState.getRow$realm().setDate(columnInfo.startDateObjIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("EventItem", 10, 0);
        builder.addPersistedProperty("key", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("startDate", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("startTime", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("desc", RealmFieldType.STRING, !Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("location", RealmFieldType.STRING, !Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("time", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("startDateFormatted", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("startTimeFormatted", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("startDateObj", RealmFieldType.DATE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static EventItemColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new EventItemColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "EventItem";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "EventItem";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.EventItem createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.csatimes.dojma.models.EventItem obj = null;
        if (update) {
            Table table = realm.getTable(com.csatimes.dojma.models.EventItem.class);
            EventItemColumnInfo columnInfo = (EventItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.EventItem.class);
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
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.EventItem.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_csatimes_dojma_models_EventItemRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("key")) {
                if (json.isNull("key")) {
                    obj = (io.realm.com_csatimes_dojma_models_EventItemRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.EventItem.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_csatimes_dojma_models_EventItemRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.EventItem.class, json.getString("key"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'key'.");
            }
        }

        final com_csatimes_dojma_models_EventItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_EventItemRealmProxyInterface) obj;
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("startDate")) {
            if (json.isNull("startDate")) {
                objProxy.realmSet$startDate(null);
            } else {
                objProxy.realmSet$startDate((String) json.getString("startDate"));
            }
        }
        if (json.has("startTime")) {
            if (json.isNull("startTime")) {
                objProxy.realmSet$startTime(null);
            } else {
                objProxy.realmSet$startTime((String) json.getString("startTime"));
            }
        }
        if (json.has("desc")) {
            if (json.isNull("desc")) {
                objProxy.realmSet$desc(null);
            } else {
                objProxy.realmSet$desc((String) json.getString("desc"));
            }
        }
        if (json.has("location")) {
            if (json.isNull("location")) {
                objProxy.realmSet$location(null);
            } else {
                objProxy.realmSet$location((String) json.getString("location"));
            }
        }
        if (json.has("time")) {
            if (json.isNull("time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
            } else {
                objProxy.realmSet$time((long) json.getLong("time"));
            }
        }
        if (json.has("startDateFormatted")) {
            if (json.isNull("startDateFormatted")) {
                objProxy.realmSet$startDateFormatted(null);
            } else {
                objProxy.realmSet$startDateFormatted((String) json.getString("startDateFormatted"));
            }
        }
        if (json.has("startTimeFormatted")) {
            if (json.isNull("startTimeFormatted")) {
                objProxy.realmSet$startTimeFormatted(null);
            } else {
                objProxy.realmSet$startTimeFormatted((String) json.getString("startTimeFormatted"));
            }
        }
        if (json.has("startDateObj")) {
            if (json.isNull("startDateObj")) {
                objProxy.realmSet$startDateObj(null);
            } else {
                Object timestamp = json.get("startDateObj");
                if (timestamp instanceof String) {
                    objProxy.realmSet$startDateObj(JsonUtils.stringToDate((String) timestamp));
                } else {
                    objProxy.realmSet$startDateObj(new Date(json.getLong("startDateObj")));
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.EventItem createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.csatimes.dojma.models.EventItem obj = new com.csatimes.dojma.models.EventItem();
        final com_csatimes_dojma_models_EventItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_EventItemRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("key")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$key((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$key(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else if (name.equals("startDate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$startDate((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$startDate(null);
                }
            } else if (name.equals("startTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$startTime((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$startTime(null);
                }
            } else if (name.equals("desc")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$desc((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$desc(null);
                }
            } else if (name.equals("location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$location(null);
                }
            } else if (name.equals("time")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$time((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
                }
            } else if (name.equals("startDateFormatted")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$startDateFormatted((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$startDateFormatted(null);
                }
            } else if (name.equals("startTimeFormatted")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$startTimeFormatted((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$startTimeFormatted(null);
                }
            } else if (name.equals("startDateObj")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$startDateObj(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        objProxy.realmSet$startDateObj(new Date(timestamp));
                    }
                } else {
                    objProxy.realmSet$startDateObj(JsonUtils.stringToDate(reader.nextString()));
                }
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

    public static com.csatimes.dojma.models.EventItem copyOrUpdate(Realm realm, com.csatimes.dojma.models.EventItem object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.EventItem) cachedRealmObject;
        }

        com.csatimes.dojma.models.EventItem realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.csatimes.dojma.models.EventItem.class);
            EventItemColumnInfo columnInfo = (EventItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.EventItem.class);
            long pkColumnIndex = columnInfo.keyIndex;
            String value = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$key();
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
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.EventItem.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_csatimes_dojma_models_EventItemRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.EventItem copy(Realm realm, com.csatimes.dojma.models.EventItem newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.EventItem) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.EventItem realmObject = realm.createObjectInternal(com.csatimes.dojma.models.EventItem.class, ((com_csatimes_dojma_models_EventItemRealmProxyInterface) newObject).realmGet$key(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_EventItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_EventItemRealmProxyInterface) newObject;
        com_csatimes_dojma_models_EventItemRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_EventItemRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectCopy.realmSet$startDate(realmObjectSource.realmGet$startDate());
        realmObjectCopy.realmSet$startTime(realmObjectSource.realmGet$startTime());
        realmObjectCopy.realmSet$desc(realmObjectSource.realmGet$desc());
        realmObjectCopy.realmSet$location(realmObjectSource.realmGet$location());
        realmObjectCopy.realmSet$time(realmObjectSource.realmGet$time());
        realmObjectCopy.realmSet$startDateFormatted(realmObjectSource.realmGet$startDateFormatted());
        realmObjectCopy.realmSet$startTimeFormatted(realmObjectSource.realmGet$startTimeFormatted());
        realmObjectCopy.realmSet$startDateObj(realmObjectSource.realmGet$startDateObj());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.EventItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.EventItem.class);
        long tableNativePtr = table.getNativePtr();
        EventItemColumnInfo columnInfo = (EventItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.EventItem.class);
        long pkColumnIndex = columnInfo.keyIndex;
        String primaryKeyValue = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$key();
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
        String realmGet$title = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        String realmGet$startDate = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDate();
        if (realmGet$startDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.startDateIndex, rowIndex, realmGet$startDate, false);
        }
        String realmGet$startTime = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startTime();
        if (realmGet$startTime != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.startTimeIndex, rowIndex, realmGet$startTime, false);
        }
        String realmGet$desc = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$desc();
        if (realmGet$desc != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descIndex, rowIndex, realmGet$desc, false);
        }
        String realmGet$location = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationIndex, rowIndex, realmGet$location, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.timeIndex, rowIndex, ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$time(), false);
        String realmGet$startDateFormatted = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDateFormatted();
        if (realmGet$startDateFormatted != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.startDateFormattedIndex, rowIndex, realmGet$startDateFormatted, false);
        }
        String realmGet$startTimeFormatted = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startTimeFormatted();
        if (realmGet$startTimeFormatted != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.startTimeFormattedIndex, rowIndex, realmGet$startTimeFormatted, false);
        }
        java.util.Date realmGet$startDateObj = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDateObj();
        if (realmGet$startDateObj != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.startDateObjIndex, rowIndex, realmGet$startDateObj.getTime(), false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.EventItem.class);
        long tableNativePtr = table.getNativePtr();
        EventItemColumnInfo columnInfo = (EventItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.EventItem.class);
        long pkColumnIndex = columnInfo.keyIndex;
        com.csatimes.dojma.models.EventItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.EventItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$key();
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
            String realmGet$title = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
            String realmGet$startDate = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDate();
            if (realmGet$startDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.startDateIndex, rowIndex, realmGet$startDate, false);
            }
            String realmGet$startTime = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startTime();
            if (realmGet$startTime != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.startTimeIndex, rowIndex, realmGet$startTime, false);
            }
            String realmGet$desc = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$desc();
            if (realmGet$desc != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descIndex, rowIndex, realmGet$desc, false);
            }
            String realmGet$location = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationIndex, rowIndex, realmGet$location, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.timeIndex, rowIndex, ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$time(), false);
            String realmGet$startDateFormatted = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDateFormatted();
            if (realmGet$startDateFormatted != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.startDateFormattedIndex, rowIndex, realmGet$startDateFormatted, false);
            }
            String realmGet$startTimeFormatted = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startTimeFormatted();
            if (realmGet$startTimeFormatted != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.startTimeFormattedIndex, rowIndex, realmGet$startTimeFormatted, false);
            }
            java.util.Date realmGet$startDateObj = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDateObj();
            if (realmGet$startDateObj != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.startDateObjIndex, rowIndex, realmGet$startDateObj.getTime(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.EventItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.EventItem.class);
        long tableNativePtr = table.getNativePtr();
        EventItemColumnInfo columnInfo = (EventItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.EventItem.class);
        long pkColumnIndex = columnInfo.keyIndex;
        String primaryKeyValue = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$key();
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
        String realmGet$title = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        String realmGet$startDate = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDate();
        if (realmGet$startDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.startDateIndex, rowIndex, realmGet$startDate, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.startDateIndex, rowIndex, false);
        }
        String realmGet$startTime = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startTime();
        if (realmGet$startTime != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.startTimeIndex, rowIndex, realmGet$startTime, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.startTimeIndex, rowIndex, false);
        }
        String realmGet$desc = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$desc();
        if (realmGet$desc != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descIndex, rowIndex, realmGet$desc, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.descIndex, rowIndex, false);
        }
        String realmGet$location = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationIndex, rowIndex, realmGet$location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.locationIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.timeIndex, rowIndex, ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$time(), false);
        String realmGet$startDateFormatted = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDateFormatted();
        if (realmGet$startDateFormatted != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.startDateFormattedIndex, rowIndex, realmGet$startDateFormatted, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.startDateFormattedIndex, rowIndex, false);
        }
        String realmGet$startTimeFormatted = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startTimeFormatted();
        if (realmGet$startTimeFormatted != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.startTimeFormattedIndex, rowIndex, realmGet$startTimeFormatted, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.startTimeFormattedIndex, rowIndex, false);
        }
        java.util.Date realmGet$startDateObj = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDateObj();
        if (realmGet$startDateObj != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.startDateObjIndex, rowIndex, realmGet$startDateObj.getTime(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.startDateObjIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.EventItem.class);
        long tableNativePtr = table.getNativePtr();
        EventItemColumnInfo columnInfo = (EventItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.EventItem.class);
        long pkColumnIndex = columnInfo.keyIndex;
        com.csatimes.dojma.models.EventItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.EventItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$key();
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
            String realmGet$title = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
            String realmGet$startDate = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDate();
            if (realmGet$startDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.startDateIndex, rowIndex, realmGet$startDate, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.startDateIndex, rowIndex, false);
            }
            String realmGet$startTime = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startTime();
            if (realmGet$startTime != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.startTimeIndex, rowIndex, realmGet$startTime, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.startTimeIndex, rowIndex, false);
            }
            String realmGet$desc = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$desc();
            if (realmGet$desc != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descIndex, rowIndex, realmGet$desc, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.descIndex, rowIndex, false);
            }
            String realmGet$location = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationIndex, rowIndex, realmGet$location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.locationIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.timeIndex, rowIndex, ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$time(), false);
            String realmGet$startDateFormatted = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDateFormatted();
            if (realmGet$startDateFormatted != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.startDateFormattedIndex, rowIndex, realmGet$startDateFormatted, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.startDateFormattedIndex, rowIndex, false);
            }
            String realmGet$startTimeFormatted = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startTimeFormatted();
            if (realmGet$startTimeFormatted != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.startTimeFormattedIndex, rowIndex, realmGet$startTimeFormatted, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.startTimeFormattedIndex, rowIndex, false);
            }
            java.util.Date realmGet$startDateObj = ((com_csatimes_dojma_models_EventItemRealmProxyInterface) object).realmGet$startDateObj();
            if (realmGet$startDateObj != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.startDateObjIndex, rowIndex, realmGet$startDateObj.getTime(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.startDateObjIndex, rowIndex, false);
            }
        }
    }

    public static com.csatimes.dojma.models.EventItem createDetachedCopy(com.csatimes.dojma.models.EventItem realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.EventItem unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.EventItem();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.EventItem) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.EventItem) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_EventItemRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_EventItemRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_EventItemRealmProxyInterface realmSource = (com_csatimes_dojma_models_EventItemRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$key(realmSource.realmGet$key());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$startDate(realmSource.realmGet$startDate());
        unmanagedCopy.realmSet$startTime(realmSource.realmGet$startTime());
        unmanagedCopy.realmSet$desc(realmSource.realmGet$desc());
        unmanagedCopy.realmSet$location(realmSource.realmGet$location());
        unmanagedCopy.realmSet$time(realmSource.realmGet$time());
        unmanagedCopy.realmSet$startDateFormatted(realmSource.realmGet$startDateFormatted());
        unmanagedCopy.realmSet$startTimeFormatted(realmSource.realmGet$startTimeFormatted());
        unmanagedCopy.realmSet$startDateObj(realmSource.realmGet$startDateObj());

        return unmanagedObject;
    }

    static com.csatimes.dojma.models.EventItem update(Realm realm, com.csatimes.dojma.models.EventItem realmObject, com.csatimes.dojma.models.EventItem newObject, Map<RealmModel, RealmObjectProxy> cache) {
        com_csatimes_dojma_models_EventItemRealmProxyInterface realmObjectTarget = (com_csatimes_dojma_models_EventItemRealmProxyInterface) realmObject;
        com_csatimes_dojma_models_EventItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_EventItemRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectTarget.realmSet$startDate(realmObjectSource.realmGet$startDate());
        realmObjectTarget.realmSet$startTime(realmObjectSource.realmGet$startTime());
        realmObjectTarget.realmSet$desc(realmObjectSource.realmGet$desc());
        realmObjectTarget.realmSet$location(realmObjectSource.realmGet$location());
        realmObjectTarget.realmSet$time(realmObjectSource.realmGet$time());
        realmObjectTarget.realmSet$startDateFormatted(realmObjectSource.realmGet$startDateFormatted());
        realmObjectTarget.realmSet$startTimeFormatted(realmObjectSource.realmGet$startTimeFormatted());
        realmObjectTarget.realmSet$startDateObj(realmObjectSource.realmGet$startDateObj());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("EventItem = proxy[");
        stringBuilder.append("{key:");
        stringBuilder.append(realmGet$key() != null ? realmGet$key() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{startDate:");
        stringBuilder.append(realmGet$startDate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{startTime:");
        stringBuilder.append(realmGet$startTime());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{desc:");
        stringBuilder.append(realmGet$desc() != null ? realmGet$desc() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{location:");
        stringBuilder.append(realmGet$location() != null ? realmGet$location() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time:");
        stringBuilder.append(realmGet$time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{startDateFormatted:");
        stringBuilder.append(realmGet$startDateFormatted() != null ? realmGet$startDateFormatted() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{startTimeFormatted:");
        stringBuilder.append(realmGet$startTimeFormatted() != null ? realmGet$startTimeFormatted() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{startDateObj:");
        stringBuilder.append(realmGet$startDateObj() != null ? realmGet$startDateObj() : "null");
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
        com_csatimes_dojma_models_EventItemRealmProxy aEventItem = (com_csatimes_dojma_models_EventItemRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aEventItem.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aEventItem.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aEventItem.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
