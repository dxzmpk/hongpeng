package com.dxzmpk.libnetwork.cache;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dxzmpk.libcommon.global.AppGlobals;


@Database(entities = {Cache.class}, version = 1)
public abstract class CacheDatabase extends RoomDatabase {

    private static final CacheDatabase cacheDatabase;

    static {
        cacheDatabase = Room.databaseBuilder(AppGlobals.getApplication(), CacheDatabase.class, "ppjoke_cache")
                .allowMainThreadQueries()
                .build();
    }

    public abstract CacheDao getCache();

    public static CacheDatabase get(){
        return cacheDatabase;
    }
}
