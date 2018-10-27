package com.example.e244194.thingstodolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.e244194.thingstodolist.database.ItemDatabase;
import com.example.e244194.thingstodolist.database.ItemEntry;

public class AddItemViewModel extends ViewModel {

    private LiveData<ItemEntry> item;

    public AddItemViewModel(ItemDatabase database, int itemId) {
        item = database.itemDao().loadItemById(itemId);
    }

    public LiveData<ItemEntry> getItem() {
        return item;
    }
}
