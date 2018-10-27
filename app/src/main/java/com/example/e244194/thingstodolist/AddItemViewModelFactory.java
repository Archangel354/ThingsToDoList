package com.example.e244194.thingstodolist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.e244194.thingstodolist.database.ItemDatabase;

public class AddItemViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ItemDatabase mDb;
    private final int mItemId;

    public AddItemViewModelFactory(ItemDatabase database, int ItemId) {
        mDb = database;
        mItemId = ItemId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddItemViewModel(mDb, mItemId);
    }
}
