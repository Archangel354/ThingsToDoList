package com.example.e244194.thingstodolist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.e244194.thingstodolist.database.ItemDatabase;
import com.example.e244194.thingstodolist.database.ItemEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<ItemEntry>> items;


    public MainViewModel(Application application) {
        super(application);
        ItemDatabase database = ItemDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the items from the DataBase");
        items = database.itemDao().loadAllItems();
    }

    public LiveData<List<ItemEntry>> getItems() {
        return items;
    }

}
