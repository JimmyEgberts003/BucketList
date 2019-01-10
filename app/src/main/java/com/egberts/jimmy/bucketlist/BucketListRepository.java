package com.egberts.jimmy.bucketlist;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class BucketListRepository {

    private BucketListDao bucketListDao;
    private LiveData<List<BucketListItem>> bucketListItems;
    private Executor executor = Executors.newSingleThreadExecutor();

    BucketListRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        bucketListDao = appDatabase.bucketListDao();
        bucketListItems = bucketListDao.getAllBucketListItems();
    }
    
    LiveData<List<BucketListItem>> getAllBucketListItems() {
        return bucketListItems;
    }

    void insert(final BucketListItem bucketListItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bucketListDao.insertBucketListItem(bucketListItem);
            }
        });

    }

    void update(final BucketListItem bucketListItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bucketListDao.updateBucketListItem(bucketListItem);
            }
        });

    }

    void delete(final BucketListItem bucketListItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bucketListDao.deleteBucketListItem(bucketListItem);
            }
        });
    }
}
