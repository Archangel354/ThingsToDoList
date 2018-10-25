package com.example.e244194.thingstodolist.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {ItemEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class ItemDatabase extends RoomDatabase {

    private static final String LOG_TAG = ItemDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todolist";
    private static ItemDatabase sInstance;

    public static ItemDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        ItemDatabase.class, ItemDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract ItemDao taskDao();

}
