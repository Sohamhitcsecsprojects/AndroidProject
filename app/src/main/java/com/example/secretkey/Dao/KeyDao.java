package com.example.secretkey.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.secretkey.Model.Key;

import java.util.List;

@Dao
public interface KeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveItem(Key key);

    @Query("select * from keydb")
    List<Key> getAllKey();

    @Query("delete from keydb")
    Integer deleteAllKey();

    @Query("update keydb set key=:mykey where ID=:id")
    void changeKey(int id, String mykey);

    @Query("update keydb set messagebackup=:status where ID=:id")
    void enableDisable(int id, Boolean status);

    @Query("update keydb set security=:status where ID=:id")
    void enableDisableSecurity(int id,Boolean status);
}
