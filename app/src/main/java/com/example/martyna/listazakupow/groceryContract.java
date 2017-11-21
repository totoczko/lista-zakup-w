package com.example.martyna.listazakupow;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Martyna on 2017-11-20.
 */

public final class groceryContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private groceryContract() {}

    /* Inner class that defines the table contents */
    public static class GroceryEntry implements BaseColumns {
        public static final String CONTENT_AUTHORITY = "com.example.martyna.listazakupow";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_GROCERIES = "grocerylist";

        //types
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GROCERIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GROCERIES;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GROCERIES);

        public static final String TABLE_NAME = "grocerylist";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }

        public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GroceryEntry.TABLE_NAME + " (" +
                    GroceryEntry._ID + " INTEGER PRIMARY KEY," +
                    GroceryEntry.COLUMN_NAME_TITLE + " TEXT," +
                    GroceryEntry.COLUMN_NAME_PRICE + " DECIMAL," +
                    GroceryEntry.COLUMN_NAME_QUANTITY + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GroceryEntry.TABLE_NAME;
}

