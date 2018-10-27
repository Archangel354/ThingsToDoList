package com.example.e244194.thingstodolist.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM items ORDER BY priority")
    LiveData<List<ItemEntry>> loadAllItems();

    @Insert
    void insertItem(ItemEntry itemEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(ItemEntry itemEntry);

    @Delete
    void deleteItem(ItemEntry itemEntry);

    @Query("SELECT * FROM items WHERE id = :id")
    LiveData<ItemEntry> loadItemById(int id);

}
