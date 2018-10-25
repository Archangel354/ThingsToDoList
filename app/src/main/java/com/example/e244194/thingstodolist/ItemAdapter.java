package com.example.e244194.thingstodolist;

import android.content.Context;

import com.example.e244194.thingstodolist.database.ItemEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ItemAdapter {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<ItemEntry> mItemEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public ItemAdapter(ItemClickListener listener, Context context) {
        mItemClickListener = listener;
        mContext = context;
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }
}
