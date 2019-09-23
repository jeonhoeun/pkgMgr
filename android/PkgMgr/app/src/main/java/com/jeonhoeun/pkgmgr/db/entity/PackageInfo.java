package com.jeonhoeun.pkgmgr.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "package_info")
public class PackageInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="package_name")
    private String packageName;

    @ColumnInfo(name="installer_package_name")
    private String installerPackageName;

    @ColumnInfo(name="version_name")
    private String versionName;

    @ColumnInfo(name="version_code")
    private long versionCode;

    @ColumnInfo(name="last_update_time")
    private String lastUpdateTime;

    @ColumnInfo(name="first_install_time")
    private String firstInstallTime;

    @ColumnInfo(name="public_source_dir")
    private String publicSourceDir;

    @ColumnInfo(name="public_source_size")
    private long publicSourceSize;

    public PackageInfo(@NonNull String packageName, String installerPackageName, String versionName, long versionCode, String lastUpdateTime, String firstInstallTime, String publicSourceDir, long publicSourceSize) {
        this.packageName = packageName;
        this.installerPackageName = installerPackageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.lastUpdateTime = lastUpdateTime;
        this.firstInstallTime = firstInstallTime;
        this.publicSourceDir = publicSourceDir;
        this.publicSourceSize = publicSourceSize;
    }

    @NonNull
    public String getPackageName() {
        return packageName;
    }

    public String getInstallerPackageName() {
        return installerPackageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public String getFirstInstallTime() {
        return firstInstallTime;
    }

    public String getPublicSourceDir() {
        return publicSourceDir;
    }

    public long getPublicSourceSize() {
        return publicSourceSize;
    }
}
