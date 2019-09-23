package com.jeonhoeun.pkgmgr.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jeonhoeun.pkgmgr.db.dao.PackageInfoDao;
import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;

@Database(entities = {PackageInfo.class}, version = 1)
public abstract class PkgDatabase extends RoomDatabase {
    private static PkgDatabase inst;
    public abstract PackageInfoDao packageInfoDao();

    public static PkgDatabase getInstance(Context context){
        if( inst == null){
            inst = Room.databaseBuilder(context.getApplicationContext(),
                    PkgDatabase.class, "pkg_database.db")
                    .build();
        }
        return inst;
    }
}
