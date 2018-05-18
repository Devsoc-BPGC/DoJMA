package io.realm;


import android.util.JsonReader;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>(8);
        modelClasses.add(com.csatimes.dojma.models.GazetteItem.class);
        modelClasses.add(com.csatimes.dojma.models.PosterItem.class);
        modelClasses.add(com.csatimes.dojma.models.LinkItem.class);
        modelClasses.add(com.csatimes.dojma.models.HeraldItem.class);
        modelClasses.add(com.csatimes.dojma.models.SlideshowItem.class);
        modelClasses.add(com.csatimes.dojma.models.ContactItem.class);
        modelClasses.add(com.csatimes.dojma.models.MessItem.class);
        modelClasses.add(com.csatimes.dojma.models.EventItem.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        Map<Class<? extends RealmModel>, OsObjectSchemaInfo> infoMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>(8);
        infoMap.put(com.csatimes.dojma.models.GazetteItem.class, io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.csatimes.dojma.models.PosterItem.class, io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.csatimes.dojma.models.LinkItem.class, io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.csatimes.dojma.models.HeraldItem.class, io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.csatimes.dojma.models.SlideshowItem.class, io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.csatimes.dojma.models.ContactItem.class, io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.csatimes.dojma.models.MessItem.class, io.realm.com_csatimes_dojma_models_MessItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.csatimes.dojma.models.EventItem.class, io.realm.com_csatimes_dojma_models_EventItemRealmProxy.getExpectedObjectSchemaInfo());
        return infoMap;
    }

    @Override
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> clazz, OsSchemaInfo schemaInfo) {
        checkClass(clazz);

        if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
            return io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
            return io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
            return io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
            return io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
            return io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
            return io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
            return io.realm.com_csatimes_dojma_models_MessItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
            return io.realm.com_csatimes_dojma_models_EventItemRealmProxy.createColumnInfo(schemaInfo);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public String getSimpleClassNameImpl(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
            return "GazetteItem";
        }
        if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
            return "PosterItem";
        }
        if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
            return "LinkItem";
        }
        if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
            return "HeraldItem";
        }
        if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
            return "SlideshowItem";
        }
        if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
            return "ContactItem";
        }
        if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
            return "MessItem";
        }
        if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
            return "EventItem";
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
                return clazz.cast(new io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy());
            }
            if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
                return clazz.cast(new io.realm.com_csatimes_dojma_models_PosterItemRealmProxy());
            }
            if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
                return clazz.cast(new io.realm.com_csatimes_dojma_models_LinkItemRealmProxy());
            }
            if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
                return clazz.cast(new io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy());
            }
            if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
                return clazz.cast(new io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy());
            }
            if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
                return clazz.cast(new io.realm.com_csatimes_dojma_models_ContactItemRealmProxy());
            }
            if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
                return clazz.cast(new io.realm.com_csatimes_dojma_models_MessItemRealmProxy());
            }
            if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
                return clazz.cast(new io.realm.com_csatimes_dojma_models_EventItemRealmProxy());
            }
            throw getMissingProxyClassException(clazz);
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.copyOrUpdate(realm, (com.csatimes.dojma.models.GazetteItem) obj, update, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.copyOrUpdate(realm, (com.csatimes.dojma.models.PosterItem) obj, update, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.copyOrUpdate(realm, (com.csatimes.dojma.models.LinkItem) obj, update, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.copyOrUpdate(realm, (com.csatimes.dojma.models.HeraldItem) obj, update, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.copyOrUpdate(realm, (com.csatimes.dojma.models.SlideshowItem) obj, update, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.copyOrUpdate(realm, (com.csatimes.dojma.models.ContactItem) obj, update, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_MessItemRealmProxy.copyOrUpdate(realm, (com.csatimes.dojma.models.MessItem) obj, update, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_EventItemRealmProxy.copyOrUpdate(realm, (com.csatimes.dojma.models.EventItem) obj, update, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
            io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.insert(realm, (com.csatimes.dojma.models.GazetteItem) object, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
            io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.insert(realm, (com.csatimes.dojma.models.PosterItem) object, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
            io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.insert(realm, (com.csatimes.dojma.models.LinkItem) object, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
            io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.insert(realm, (com.csatimes.dojma.models.HeraldItem) object, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
            io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.insert(realm, (com.csatimes.dojma.models.SlideshowItem) object, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
            io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.insert(realm, (com.csatimes.dojma.models.ContactItem) object, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
            io.realm.com_csatimes_dojma_models_MessItemRealmProxy.insert(realm, (com.csatimes.dojma.models.MessItem) object, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
            io.realm.com_csatimes_dojma_models_EventItemRealmProxy.insert(realm, (com.csatimes.dojma.models.EventItem) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
                io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.insert(realm, (com.csatimes.dojma.models.GazetteItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
                io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.insert(realm, (com.csatimes.dojma.models.PosterItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
                io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.insert(realm, (com.csatimes.dojma.models.LinkItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
                io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.insert(realm, (com.csatimes.dojma.models.HeraldItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
                io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.insert(realm, (com.csatimes.dojma.models.SlideshowItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
                io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.insert(realm, (com.csatimes.dojma.models.ContactItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
                io.realm.com_csatimes_dojma_models_MessItemRealmProxy.insert(realm, (com.csatimes.dojma.models.MessItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
                io.realm.com_csatimes_dojma_models_EventItemRealmProxy.insert(realm, (com.csatimes.dojma.models.EventItem) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
                    io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
                    io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
                    io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
                    io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
                    io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
                    io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
                    io.realm.com_csatimes_dojma_models_MessItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
                    io.realm.com_csatimes_dojma_models_EventItemRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
            io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.GazetteItem) obj, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
            io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.PosterItem) obj, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
            io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.LinkItem) obj, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
            io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.HeraldItem) obj, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
            io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.SlideshowItem) obj, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
            io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.ContactItem) obj, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
            io.realm.com_csatimes_dojma_models_MessItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.MessItem) obj, cache);
        } else if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
            io.realm.com_csatimes_dojma_models_EventItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.EventItem) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
                io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.GazetteItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
                io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.PosterItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
                io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.LinkItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
                io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.HeraldItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
                io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.SlideshowItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
                io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.ContactItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
                io.realm.com_csatimes_dojma_models_MessItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.MessItem) object, cache);
            } else if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
                io.realm.com_csatimes_dojma_models_EventItemRealmProxy.insertOrUpdate(realm, (com.csatimes.dojma.models.EventItem) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
                    io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
                    io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
                    io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
                    io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
                    io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
                    io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
                    io.realm.com_csatimes_dojma_models_MessItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
                    io.realm.com_csatimes_dojma_models_EventItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_MessItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_EventItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_MessItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_EventItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(com.csatimes.dojma.models.GazetteItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_GazetteItemRealmProxy.createDetachedCopy((com.csatimes.dojma.models.GazetteItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.PosterItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_PosterItemRealmProxy.createDetachedCopy((com.csatimes.dojma.models.PosterItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.LinkItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_LinkItemRealmProxy.createDetachedCopy((com.csatimes.dojma.models.LinkItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.HeraldItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_HeraldItemRealmProxy.createDetachedCopy((com.csatimes.dojma.models.HeraldItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.SlideshowItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_SlideshowItemRealmProxy.createDetachedCopy((com.csatimes.dojma.models.SlideshowItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.ContactItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_ContactItemRealmProxy.createDetachedCopy((com.csatimes.dojma.models.ContactItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.MessItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_MessItemRealmProxy.createDetachedCopy((com.csatimes.dojma.models.MessItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.csatimes.dojma.models.EventItem.class)) {
            return clazz.cast(io.realm.com_csatimes_dojma_models_EventItemRealmProxy.createDetachedCopy((com.csatimes.dojma.models.EventItem) realmObject, 0, maxDepth, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

}
