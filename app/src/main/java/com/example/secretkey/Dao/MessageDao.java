package com.example.secretkey.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.secretkey.Model.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveItem(Message message);

    @Query("select * from message order by creationtime desc")
    List<Message> getAllMessages();
}
