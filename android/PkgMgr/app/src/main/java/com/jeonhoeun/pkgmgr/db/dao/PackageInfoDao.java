package com.jeonhoeun.pkgmgr.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;

import java.util.List;

@Dao
public interface PackageInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PackageInfo> infos);

    @Query("SELECT * FROM package_info")
    List<PackageInfo> getAll();
}
