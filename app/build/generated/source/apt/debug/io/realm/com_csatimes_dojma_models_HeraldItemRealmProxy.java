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
public class com_csatimes_dojma_models_HeraldItemRealmProxy extends com.csatimes.dojma.models.HeraldItem
    implements RealmObjectProxy, com_csatimes_dojma_models_HeraldItemRealmProxyInterface {

    static final class HeraldItemColumnInfo extends ColumnInfo {
        long postIDIndex;
        long titleIndex;
        long title_plainIndex;
        long originalDateIndex;
        long originalTimeIndex;
        long updateDateIndex;
        long updateTimeIndex;
        long thumbnailUrlIndex;
        long urlIndex;
        long contentIndex;
        long excerptIndex;
        long categoryIndex;
        long favIndex;

        HeraldItemColumnInfo(OsSchemaInfo schemaInfo) {
            super(13);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("HeraldItem");
            this.postIDIndex = addColumnDetails("postID", "postID", objectSchemaInfo);
            this.titleIndex = addColumnDetails("title", "title", objectSchemaInfo);
            this.title_plainIndex = addColumnDetails("title_plain", "title_plain", objectSchemaInfo);
            this.originalDateIndex = addColumnDetails("originalDate", "originalDate", objectSchemaInfo);
            this.originalTimeIndex = addColumnDetails("originalTime", "originalTime", objectSchemaInfo);
            this.updateDateIndex = addColumnDetails("updateDate", "updateDate", objectSchemaInfo);
            this.updateTimeIndex = addColumnDetails("updateTime", "updateTime", objectSchemaInfo);
            this.thumbnailUrlIndex = addColumnDetails("thumbnailUrl", "thumbnailUrl", objectSchemaInfo);
            this.urlIndex = addColumnDetails("url", "url", objectSchemaInfo);
            this.contentIndex = addColumnDetails("content", "content", objectSchemaInfo);
            this.excerptIndex = addColumnDetails("excerpt", "excerpt", objectSchemaInfo);
            this.categoryIndex = addColumnDetails("category", "category", objectSchemaInfo);
            this.favIndex = addColumnDetails("fav", "fav", objectSchemaInfo);
        }

        HeraldItemColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new HeraldItemColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final HeraldItemColumnInfo src = (HeraldItemColumnInfo) rawSrc;
            final HeraldItemColumnInfo dst = (HeraldItemColumnInfo) rawDst;
            dst.postIDIndex = src.postIDIndex;
            dst.titleIndex = src.titleIndex;
            dst.title_plainIndex = src.title_plainIndex;
            dst.originalDateIndex = src.originalDateIndex;
            dst.originalTimeIndex = src.originalTimeIndex;
            dst.updateDateIndex = src.updateDateIndex;
            dst.updateTimeIndex = src.updateTimeIndex;
            dst.thumbnailUrlIndex = src.thumbnailUrlIndex;
            dst.urlIndex = src.urlIndex;
            dst.contentIndex = src.contentIndex;
            dst.excerptIndex = src.excerptIndex;
            dst.categoryIndex = src.categoryIndex;
            dst.favIndex = src.favIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private HeraldItemColumnInfo columnInfo;
    private ProxyState<com.csatimes.dojma.models.HeraldItem> proxyState;

    com_csatimes_dojma_models_HeraldItemRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (HeraldItemColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.csatimes.dojma.models.HeraldItem>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$postID() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.postIDIndex);
    }

    @Override
    public void realmSet$postID(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'postID' cannot be changed after object was created.");
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
    public String realmGet$title_plain() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.title_plainIndex);
    }

    @Override
    public void realmSet$title_plain(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.title_plainIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.title_plainIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.title_plainIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.title_plainIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$originalDate() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.originalDateIndex);
    }

    @Override
    public void realmSet$originalDate(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.originalDateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.originalDateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.originalDateIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.originalDateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$originalTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.originalTimeIndex);
    }

    @Override
    public void realmSet$originalTime(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.originalTimeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.originalTimeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.originalTimeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.originalTimeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$updateDate() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.updateDateIndex);
    }

    @Override
    public void realmSet$updateDate(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.updateDateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.updateDateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.updateDateIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.updateDateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$updateTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.updateTimeIndex);
    }

    @Override
    public void realmSet$updateTime(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.updateTimeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.updateTimeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.updateTimeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.updateTimeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$thumbnailUrl() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.thumbnailUrlIndex);
    }

    @Override
    public void realmSet$thumbnailUrl(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.thumbnailUrlIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.thumbnailUrlIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.thumbnailUrlIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.thumbnailUrlIndex, value);
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
    @SuppressWarnings("cast")
    public String realmGet$category() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.categoryIndex);
    }

    @Override
    public void realmSet$category(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.categoryIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.categoryIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.categoryIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.categoryIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$fav() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.favIndex);
    }

    @Override
    public void realmSet$fav(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.favIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.favIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("HeraldItem", 13, 0);
        builder.addPersistedProperty("postID", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("title_plain", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("originalDate", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("originalTime", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("updateDate", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("updateTime", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("thumbnailUrl", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("content", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("excerpt", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("category", RealmFieldType.STRING, !Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("fav", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static HeraldItemColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new HeraldItemColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "HeraldItem";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "HeraldItem";
    }

    @SuppressWarnings("cast")
    public static com.csatimes.dojma.models.HeraldItem createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.csatimes.dojma.models.HeraldItem obj = null;
        if (update) {
            Table table = realm.getTable(com.csatimes.dojma.models.HeraldItem.class);
            HeraldItemColumnInfo columnInfo = (HeraldItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.HeraldItem.class);
            long pkColumnIndex = columnInfo.postIDIndex;
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("postID")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("postID"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.HeraldItem.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("postID")) {
                if (json.isNull("postID")) {
                    obj = (io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.HeraldItem.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy) realm.createObjectInternal(com.csatimes.dojma.models.HeraldItem.class, json.getString("postID"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'postID'.");
            }
        }

        final com_csatimes_dojma_models_HeraldItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_HeraldItemRealmProxyInterface) obj;
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("title_plain")) {
            if (json.isNull("title_plain")) {
                objProxy.realmSet$title_plain(null);
            } else {
                objProxy.realmSet$title_plain((String) json.getString("title_plain"));
            }
        }
        if (json.has("originalDate")) {
            if (json.isNull("originalDate")) {
                objProxy.realmSet$originalDate(null);
            } else {
                objProxy.realmSet$originalDate((String) json.getString("originalDate"));
            }
        }
        if (json.has("originalTime")) {
            if (json.isNull("originalTime")) {
                objProxy.realmSet$originalTime(null);
            } else {
                objProxy.realmSet$originalTime((String) json.getString("originalTime"));
            }
        }
        if (json.has("updateDate")) {
            if (json.isNull("updateDate")) {
                objProxy.realmSet$updateDate(null);
            } else {
                objProxy.realmSet$updateDate((String) json.getString("updateDate"));
            }
        }
        if (json.has("updateTime")) {
            if (json.isNull("updateTime")) {
                objProxy.realmSet$updateTime(null);
            } else {
                objProxy.realmSet$updateTime((String) json.getString("updateTime"));
            }
        }
        if (json.has("thumbnailUrl")) {
            if (json.isNull("thumbnailUrl")) {
                objProxy.realmSet$thumbnailUrl(null);
            } else {
                objProxy.realmSet$thumbnailUrl((String) json.getString("thumbnailUrl"));
            }
        }
        if (json.has("url")) {
            if (json.isNull("url")) {
                objProxy.realmSet$url(null);
            } else {
                objProxy.realmSet$url((String) json.getString("url"));
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
        if (json.has("category")) {
            if (json.isNull("category")) {
                objProxy.realmSet$category(null);
            } else {
                objProxy.realmSet$category((String) json.getString("category"));
            }
        }
        if (json.has("fav")) {
            if (json.isNull("fav")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'fav' to null.");
            } else {
                objProxy.realmSet$fav((boolean) json.getBoolean("fav"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.csatimes.dojma.models.HeraldItem createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.csatimes.dojma.models.HeraldItem obj = new com.csatimes.dojma.models.HeraldItem();
        final com_csatimes_dojma_models_HeraldItemRealmProxyInterface objProxy = (com_csatimes_dojma_models_HeraldItemRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("postID")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$postID((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$postID(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else if (name.equals("title_plain")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title_plain((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title_plain(null);
                }
            } else if (name.equals("originalDate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$originalDate((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$originalDate(null);
                }
            } else if (name.equals("originalTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$originalTime((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$originalTime(null);
                }
            } else if (name.equals("updateDate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$updateDate((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$updateDate(null);
                }
            } else if (name.equals("updateTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$updateTime((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$updateTime(null);
                }
            } else if (name.equals("thumbnailUrl")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$thumbnailUrl((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$thumbnailUrl(null);
                }
            } else if (name.equals("url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$url(null);
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
            } else if (name.equals("category")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$category((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$category(null);
                }
            } else if (name.equals("fav")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$fav((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'fav' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'postID'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.csatimes.dojma.models.HeraldItem copyOrUpdate(Realm realm, com.csatimes.dojma.models.HeraldItem object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.csatimes.dojma.models.HeraldItem) cachedRealmObject;
        }

        com.csatimes.dojma.models.HeraldItem realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.csatimes.dojma.models.HeraldItem.class);
            HeraldItemColumnInfo columnInfo = (HeraldItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.HeraldItem.class);
            long pkColumnIndex = columnInfo.postIDIndex;
            String value = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$postID();
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
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.csatimes.dojma.models.HeraldItem.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.csatimes.dojma.models.HeraldItem copy(Realm realm, com.csatimes.dojma.models.HeraldItem newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.csatimes.dojma.models.HeraldItem) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.csatimes.dojma.models.HeraldItem realmObject = realm.createObjectInternal(com.csatimes.dojma.models.HeraldItem.class, ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) newObject).realmGet$postID(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_csatimes_dojma_models_HeraldItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_HeraldItemRealmProxyInterface) newObject;
        com_csatimes_dojma_models_HeraldItemRealmProxyInterface realmObjectCopy = (com_csatimes_dojma_models_HeraldItemRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectCopy.realmSet$title_plain(realmObjectSource.realmGet$title_plain());
        realmObjectCopy.realmSet$originalDate(realmObjectSource.realmGet$originalDate());
        realmObjectCopy.realmSet$originalTime(realmObjectSource.realmGet$originalTime());
        realmObjectCopy.realmSet$updateDate(realmObjectSource.realmGet$updateDate());
        realmObjectCopy.realmSet$updateTime(realmObjectSource.realmGet$updateTime());
        realmObjectCopy.realmSet$thumbnailUrl(realmObjectSource.realmGet$thumbnailUrl());
        realmObjectCopy.realmSet$url(realmObjectSource.realmGet$url());
        realmObjectCopy.realmSet$content(realmObjectSource.realmGet$content());
        realmObjectCopy.realmSet$excerpt(realmObjectSource.realmGet$excerpt());
        realmObjectCopy.realmSet$category(realmObjectSource.realmGet$category());
        realmObjectCopy.realmSet$fav(realmObjectSource.realmGet$fav());
        return realmObject;
    }

    public static long insert(Realm realm, com.csatimes.dojma.models.HeraldItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.HeraldItem.class);
        long tableNativePtr = table.getNativePtr();
        HeraldItemColumnInfo columnInfo = (HeraldItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.HeraldItem.class);
        long pkColumnIndex = columnInfo.postIDIndex;
        String primaryKeyValue = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$postID();
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
        String realmGet$title = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }
        String realmGet$title_plain = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$title_plain();
        if (realmGet$title_plain != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.title_plainIndex, rowIndex, realmGet$title_plain, false);
        }
        String realmGet$originalDate = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$originalDate();
        if (realmGet$originalDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.originalDateIndex, rowIndex, realmGet$originalDate, false);
        }
        String realmGet$originalTime = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$originalTime();
        if (realmGet$originalTime != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.originalTimeIndex, rowIndex, realmGet$originalTime, false);
        }
        String realmGet$updateDate = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$updateDate();
        if (realmGet$updateDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.updateDateIndex, rowIndex, realmGet$updateDate, false);
        }
        String realmGet$updateTime = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$updateTime();
        if (realmGet$updateTime != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.updateTimeIndex, rowIndex, realmGet$updateTime, false);
        }
        String realmGet$thumbnailUrl = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$thumbnailUrl();
        if (realmGet$thumbnailUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.thumbnailUrlIndex, rowIndex, realmGet$thumbnailUrl, false);
        }
        String realmGet$url = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        }
        String realmGet$content = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$content();
        if (realmGet$content != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.contentIndex, rowIndex, realmGet$content, false);
        }
        String realmGet$excerpt = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$excerpt();
        if (realmGet$excerpt != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.excerptIndex, rowIndex, realmGet$excerpt, false);
        }
        String realmGet$category = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$category();
        if (realmGet$category != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.categoryIndex, rowIndex, realmGet$category, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.favIndex, rowIndex, ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$fav(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.HeraldItem.class);
        long tableNativePtr = table.getNativePtr();
        HeraldItemColumnInfo columnInfo = (HeraldItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.HeraldItem.class);
        long pkColumnIndex = columnInfo.postIDIndex;
        com.csatimes.dojma.models.HeraldItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.HeraldItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$postID();
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
            String realmGet$title = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            }
            String realmGet$title_plain = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$title_plain();
            if (realmGet$title_plain != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.title_plainIndex, rowIndex, realmGet$title_plain, false);
            }
            String realmGet$originalDate = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$originalDate();
            if (realmGet$originalDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.originalDateIndex, rowIndex, realmGet$originalDate, false);
            }
            String realmGet$originalTime = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$originalTime();
            if (realmGet$originalTime != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.originalTimeIndex, rowIndex, realmGet$originalTime, false);
            }
            String realmGet$updateDate = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$updateDate();
            if (realmGet$updateDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.updateDateIndex, rowIndex, realmGet$updateDate, false);
            }
            String realmGet$updateTime = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$updateTime();
            if (realmGet$updateTime != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.updateTimeIndex, rowIndex, realmGet$updateTime, false);
            }
            String realmGet$thumbnailUrl = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$thumbnailUrl();
            if (realmGet$thumbnailUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.thumbnailUrlIndex, rowIndex, realmGet$thumbnailUrl, false);
            }
            String realmGet$url = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$url();
            if (realmGet$url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
            }
            String realmGet$content = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$content();
            if (realmGet$content != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.contentIndex, rowIndex, realmGet$content, false);
            }
            String realmGet$excerpt = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$excerpt();
            if (realmGet$excerpt != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.excerptIndex, rowIndex, realmGet$excerpt, false);
            }
            String realmGet$category = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$category();
            if (realmGet$category != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.categoryIndex, rowIndex, realmGet$category, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.favIndex, rowIndex, ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$fav(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.csatimes.dojma.models.HeraldItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.csatimes.dojma.models.HeraldItem.class);
        long tableNativePtr = table.getNativePtr();
        HeraldItemColumnInfo columnInfo = (HeraldItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.HeraldItem.class);
        long pkColumnIndex = columnInfo.postIDIndex;
        String primaryKeyValue = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$postID();
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
        String realmGet$title = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }
        String realmGet$title_plain = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$title_plain();
        if (realmGet$title_plain != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.title_plainIndex, rowIndex, realmGet$title_plain, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.title_plainIndex, rowIndex, false);
        }
        String realmGet$originalDate = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$originalDate();
        if (realmGet$originalDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.originalDateIndex, rowIndex, realmGet$originalDate, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.originalDateIndex, rowIndex, false);
        }
        String realmGet$originalTime = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$originalTime();
        if (realmGet$originalTime != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.originalTimeIndex, rowIndex, realmGet$originalTime, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.originalTimeIndex, rowIndex, false);
        }
        String realmGet$updateDate = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$updateDate();
        if (realmGet$updateDate != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.updateDateIndex, rowIndex, realmGet$updateDate, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.updateDateIndex, rowIndex, false);
        }
        String realmGet$updateTime = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$updateTime();
        if (realmGet$updateTime != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.updateTimeIndex, rowIndex, realmGet$updateTime, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.updateTimeIndex, rowIndex, false);
        }
        String realmGet$thumbnailUrl = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$thumbnailUrl();
        if (realmGet$thumbnailUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.thumbnailUrlIndex, rowIndex, realmGet$thumbnailUrl, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.thumbnailUrlIndex, rowIndex, false);
        }
        String realmGet$url = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
        }
        String realmGet$content = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$content();
        if (realmGet$content != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.contentIndex, rowIndex, realmGet$content, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.contentIndex, rowIndex, false);
        }
        String realmGet$excerpt = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$excerpt();
        if (realmGet$excerpt != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.excerptIndex, rowIndex, realmGet$excerpt, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.excerptIndex, rowIndex, false);
        }
        String realmGet$category = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$category();
        if (realmGet$category != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.categoryIndex, rowIndex, realmGet$category, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.categoryIndex, rowIndex, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.favIndex, rowIndex, ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$fav(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.csatimes.dojma.models.HeraldItem.class);
        long tableNativePtr = table.getNativePtr();
        HeraldItemColumnInfo columnInfo = (HeraldItemColumnInfo) realm.getSchema().getColumnInfo(com.csatimes.dojma.models.HeraldItem.class);
        long pkColumnIndex = columnInfo.postIDIndex;
        com.csatimes.dojma.models.HeraldItem object = null;
        while (objects.hasNext()) {
            object = (com.csatimes.dojma.models.HeraldItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$postID();
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
            String realmGet$title = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
            }
            String realmGet$title_plain = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$title_plain();
            if (realmGet$title_plain != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.title_plainIndex, rowIndex, realmGet$title_plain, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.title_plainIndex, rowIndex, false);
            }
            String realmGet$originalDate = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$originalDate();
            if (realmGet$originalDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.originalDateIndex, rowIndex, realmGet$originalDate, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.originalDateIndex, rowIndex, false);
            }
            String realmGet$originalTime = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$originalTime();
            if (realmGet$originalTime != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.originalTimeIndex, rowIndex, realmGet$originalTime, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.originalTimeIndex, rowIndex, false);
            }
            String realmGet$updateDate = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$updateDate();
            if (realmGet$updateDate != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.updateDateIndex, rowIndex, realmGet$updateDate, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.updateDateIndex, rowIndex, false);
            }
            String realmGet$updateTime = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$updateTime();
            if (realmGet$updateTime != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.updateTimeIndex, rowIndex, realmGet$updateTime, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.updateTimeIndex, rowIndex, false);
            }
            String realmGet$thumbnailUrl = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$thumbnailUrl();
            if (realmGet$thumbnailUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.thumbnailUrlIndex, rowIndex, realmGet$thumbnailUrl, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.thumbnailUrlIndex, rowIndex, false);
            }
            String realmGet$url = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$url();
            if (realmGet$url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
            }
            String realmGet$content = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$content();
            if (realmGet$content != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.contentIndex, rowIndex, realmGet$content, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.contentIndex, rowIndex, false);
            }
            String realmGet$excerpt = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$excerpt();
            if (realmGet$excerpt != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.excerptIndex, rowIndex, realmGet$excerpt, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.excerptIndex, rowIndex, false);
            }
            String realmGet$category = ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$category();
            if (realmGet$category != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.categoryIndex, rowIndex, realmGet$category, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.categoryIndex, rowIndex, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.favIndex, rowIndex, ((com_csatimes_dojma_models_HeraldItemRealmProxyInterface) object).realmGet$fav(), false);
        }
    }

    public static com.csatimes.dojma.models.HeraldItem createDetachedCopy(com.csatimes.dojma.models.HeraldItem realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.csatimes.dojma.models.HeraldItem unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.csatimes.dojma.models.HeraldItem();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.csatimes.dojma.models.HeraldItem) cachedObject.object;
            }
            unmanagedObject = (com.csatimes.dojma.models.HeraldItem) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_csatimes_dojma_models_HeraldItemRealmProxyInterface unmanagedCopy = (com_csatimes_dojma_models_HeraldItemRealmProxyInterface) unmanagedObject;
        com_csatimes_dojma_models_HeraldItemRealmProxyInterface realmSource = (com_csatimes_dojma_models_HeraldItemRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$postID(realmSource.realmGet$postID());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$title_plain(realmSource.realmGet$title_plain());
        unmanagedCopy.realmSet$originalDate(realmSource.realmGet$originalDate());
        unmanagedCopy.realmSet$originalTime(realmSource.realmGet$originalTime());
        unmanagedCopy.realmSet$updateDate(realmSource.realmGet$updateDate());
        unmanagedCopy.realmSet$updateTime(realmSource.realmGet$updateTime());
        unmanagedCopy.realmSet$thumbnailUrl(realmSource.realmGet$thumbnailUrl());
        unmanagedCopy.realmSet$url(realmSource.realmGet$url());
        unmanagedCopy.realmSet$content(realmSource.realmGet$content());
        unmanagedCopy.realmSet$excerpt(realmSource.realmGet$excerpt());
        unmanagedCopy.realmSet$category(realmSource.realmGet$category());
        unmanagedCopy.realmSet$fav(realmSource.realmGet$fav());

        return unmanagedObject;
    }

    static com.csatimes.dojma.models.HeraldItem update(Realm realm, com.csatimes.dojma.models.HeraldItem realmObject, com.csatimes.dojma.models.HeraldItem newObject, Map<RealmModel, RealmObjectProxy> cache) {
        com_csatimes_dojma_models_HeraldItemRealmProxyInterface realmObjectTarget = (com_csatimes_dojma_models_HeraldItemRealmProxyInterface) realmObject;
        com_csatimes_dojma_models_HeraldItemRealmProxyInterface realmObjectSource = (com_csatimes_dojma_models_HeraldItemRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$title(realmObjectSource.realmGet$title());
        realmObjectTarget.realmSet$title_plain(realmObjectSource.realmGet$title_plain());
        realmObjectTarget.realmSet$originalDate(realmObjectSource.realmGet$originalDate());
        realmObjectTarget.realmSet$originalTime(realmObjectSource.realmGet$originalTime());
        realmObjectTarget.realmSet$updateDate(realmObjectSource.realmGet$updateDate());
        realmObjectTarget.realmSet$updateTime(realmObjectSource.realmGet$updateTime());
        realmObjectTarget.realmSet$thumbnailUrl(realmObjectSource.realmGet$thumbnailUrl());
        realmObjectTarget.realmSet$url(realmObjectSource.realmGet$url());
        realmObjectTarget.realmSet$content(realmObjectSource.realmGet$content());
        realmObjectTarget.realmSet$excerpt(realmObjectSource.realmGet$excerpt());
        realmObjectTarget.realmSet$category(realmObjectSource.realmGet$category());
        realmObjectTarget.realmSet$fav(realmObjectSource.realmGet$fav());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("HeraldItem = proxy[");
        stringBuilder.append("{postID:");
        stringBuilder.append(realmGet$postID() != null ? realmGet$postID() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title_plain:");
        stringBuilder.append(realmGet$title_plain() != null ? realmGet$title_plain() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{originalDate:");
        stringBuilder.append(realmGet$originalDate() != null ? realmGet$originalDate() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{originalTime:");
        stringBuilder.append(realmGet$originalTime() != null ? realmGet$originalTime() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{updateDate:");
        stringBuilder.append(realmGet$updateDate() != null ? realmGet$updateDate() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{updateTime:");
        stringBuilder.append(realmGet$updateTime() != null ? realmGet$updateTime() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{thumbnailUrl:");
        stringBuilder.append(realmGet$thumbnailUrl() != null ? realmGet$thumbnailUrl() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{url:");
        stringBuilder.append(realmGet$url() != null ? realmGet$url() : "null");
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
        stringBuilder.append("{category:");
        stringBuilder.append(realmGet$category() != null ? realmGet$category() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{fav:");
        stringBuilder.append(realmGet$fav());
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
        com_csatimes_dojma_models_HeraldItemRealmProxy aHeraldItem = (com_csatimes_dojma_models_HeraldItemRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aHeraldItem.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aHeraldItem.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aHeraldItem.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
