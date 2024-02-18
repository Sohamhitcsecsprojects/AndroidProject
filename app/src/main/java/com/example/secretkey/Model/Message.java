package com.example.secretkey.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "message")
public class Message implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "ptext")
    String ptext;

    @ColumnInfo(name = "etext")
    String etext;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "creationtime")
    Date creationtime;

    public Message(){

    }

    public Message(String ptext, String etext, Date creationtime) {
        this.ptext = ptext;
        this.etext = etext;
        this.creationtime = creationtime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPtext() {
        return ptext;
    }

    public void setPtext(String ptext) {
        this.ptext = ptext;
    }

    public String getEtext() {
        return etext;
    }

    public void setEtext(String etext) {
        this.etext = etext;
    }

    public Date getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }
}


