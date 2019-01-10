package com.egberts.jimmy.bucketlist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BucketListDao {

    @Query("SELECT * FROM bucketList")
    LiveData<List<BucketListItem>> getAllBucketListItems();

    @Insert
    void insertBucketListItem(BucketListItem bucketListItem);

    @Delete
    void deleteBucketListItem(BucketListItem bucketListItem);

    @Update
    void updateBucketListItem(BucketListItem bucketListItem);
}
