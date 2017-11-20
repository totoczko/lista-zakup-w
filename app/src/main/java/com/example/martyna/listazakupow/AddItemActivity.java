package com.example.martyna.listazakupow;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends parentActivity {

    EditText addNameEditText;
    EditText addPriceEditText;
    EditText addQuantityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addNameEditText = (EditText) findViewById(R.id.addNewName);
        addPriceEditText = (EditText) findViewById(R.id.addNewPrice);
        addQuantityEditText = (EditText) findViewById(R.id.addNewQuantity);
    }

    public void insertNewItemRow(View v) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String groceryName = addNameEditText.getText().toString().trim();
        String groceryPrice = addPriceEditText.getText().toString().trim();
        String groceryQuantity = addQuantityEditText.getText().toString().trim();

        // Create database helper
        DBHelper mDbHelper = new DBHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(groceryContract.GroceryEntry.COLUMN_NAME_TITLE, groceryName);
        values.put(groceryContract.GroceryEntry.COLUMN_NAME_PRICE, groceryPrice);
        values.put(groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY, groceryQuantity);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(groceryContract.GroceryEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving item", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Item saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
