package com.dxzmpk.libnetwork.cache;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "cache")
public class Cache implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @NotNull
    public String key;

    public byte[] data;

}
