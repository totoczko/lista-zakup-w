package com.example.martyna.listazakupow;

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

