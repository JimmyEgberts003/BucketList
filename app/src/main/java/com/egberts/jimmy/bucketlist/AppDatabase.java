package com.egberts.jimmy.bucketlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {BucketListItem.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {

    abstract BucketListDao bucketListDao();
    private final static String DATABASE_NAME = "bucket_list_db";
    private static AppDatabase appDatabaseInstance;

    static AppDatabase getInstance(Context context) {
        if (appDatabaseInstance == null) {
            appDatabaseInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return appDatabaseInstance;
    }
}
