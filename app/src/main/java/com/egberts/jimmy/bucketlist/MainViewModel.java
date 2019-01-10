package com.egberts.jimmy.bucketlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

class MainViewModel extends ViewModel {
    private BucketListRepository bucketListRepository;
    private LiveData<List<BucketListItem>> bucketListItems;

    MainViewModel(Context context) {
        bucketListRepository = new BucketListRepository(context);
        bucketListItems = bucketListRepository.getAllBucketListItems();
    }

    LiveData<List<BucketListItem>> getAllBucketListItems() {
        return bucketListItems;
    }

    void insert(BucketListItem bucketListItem) {
        bucketListRepository.insert(bucketListItem);
    }

    public void update(BucketListItem bucketListItem) {
        bucketListRepository.update(bucketListItem);
    }

    void delete(BucketListItem bucketListItem) {
        bucketListRepository.delete(bucketListItem);
    }

}
