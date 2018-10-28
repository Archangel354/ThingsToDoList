package com.example.e244194.thingstodolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.e244194.thingstodolist.database.ItemDatabase;
import com.example.e244194.thingstodolist.database.ItemEntry;

public class AddItemViewModel extends ViewModel {

    // Item member variable for the ItemEntry object wrapped in a LiveData
    private LiveData<ItemEntry> item;

    // Note: The constructor should receive the database and the taskId
    public AddItemViewModel(ItemDatabase database, int itemId) {
        item = database.itemDao().loadItemById(itemId);
    }

    //This is the getter for the item variable
    public LiveData<ItemEntry> getItem() {
        return item;
    }
}
