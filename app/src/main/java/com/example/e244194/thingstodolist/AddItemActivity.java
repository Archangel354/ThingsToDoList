package com.example.e244194.thingstodolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.e244194.thingstodolist.database.ItemDatabase;
import com.example.e244194.thingstodolist.database.ItemEntry;

import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    // Extra for the item ID to be received in the intent
    public static final String EXTRA_ITEM_ID = "extraItemId";
    // Extra for the item ID to be received after rotation
    public static final String INSTANCE_ITEM_ID = "instanceItemId";
    // Constants for priority
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_ITEM_ID = -1;
    // Constant for logging
    private static final String TAG = AddItemActivity.class.getSimpleName();
    // Fields for views
    EditText mEditText;
    RadioGroup mRadioGroup;
    Button mButton;

    private int mItemID = DEFAULT_ITEM_ID;

    // Member variable for the Database
    private ItemDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initViews();

        mDb = ItemDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ITEM_ID)) {
            mItemID = savedInstanceState.getInt(INSTANCE_ITEM_ID, DEFAULT_ITEM_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_ITEM_ID)) {
            mButton.setText(R.string.update_button);
            if (mItemID == DEFAULT_ITEM_ID) {
                // populate the UI
                mItemID = intent.getIntExtra(EXTRA_ITEM_ID, DEFAULT_ITEM_ID);

                AddItemViewModelFactory factory = new AddItemViewModelFactory(mDb, mItemID);

                // for that use the factory created above AddTaskViewModel
                final AddItemViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddItemViewModel.class);
                // TODO (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getItem().observe(this, new Observer<ItemEntry>() {
                    @Override
                    public void onChanged(@Nullable ItemEntry itemEntry) {
                        viewModel.getItem().removeObserver(this);
                        populateUI(itemEntry);
                    }
                    });
                } {

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_ITEM_ID, mItemID);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mEditText = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);

        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param item the taskEntry to populate the UI
     */
    private void populateUI(ItemEntry item) {
        if (item == null) {
            return;
        }

        mEditText.setText(item.getDescription());
        setPriorityInViews(item.getPriority());
    }
    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new item data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String description = mEditText.getText().toString();
        int priority = getPriorityFromViews();
        Date date = new Date();

        final ItemEntry item = new ItemEntry(description, priority, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mItemID == DEFAULT_ITEM_ID) {
                    // insert new task
                    mDb.itemDao().insertItem(item);
                } else {
                    //update task
                    item.setId(mItemID);
                    mDb.itemDao().updateItem(item);
                }
                finish();
            }
        });
    }

    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }

    /**
     * setPriority is called when we receive a task from MainActivity
     *
     * @param priority the priority value
     */
    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }
}
