package com.example.martyna.listazakupow;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends parentActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    /** Identifier for the pet data loader */
    private static final int EXISTING_GROCERY_LOADER = 0;
    /** Content URI for the existing pet (null if it's a new pet) */
    private Uri mCurrentUri;

    EditText addNameEditText;
    EditText addPriceEditText;
    EditText addQuantityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new pet or editing an existing one.
        Intent intent = getIntent();
        mCurrentUri = intent.getData();
        // If the intent DOES NOT contain a pet content URI, then we know that we are
        // creating a new pet.
        if (mCurrentUri == null) {
            setTitle(R.string.editor_activity_title_new_item);
        }else{
            setTitle(R.string.editor_activity_title_edit_item);
            // Initialize a loader to read the pet data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_GROCERY_LOADER, null, this);
        }

        addNameEditText = (EditText) findViewById(R.id.addNewName);
        addPriceEditText = (EditText) findViewById(R.id.addNewPrice);
        addQuantityEditText = (EditText) findViewById(R.id.addNewQuantity);
    }

    public void saveItemRow(View v) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String groceryName = addNameEditText.getText().toString().trim();
        String groceryPrice = addPriceEditText.getText().toString().trim();
        String groceryQuantity = addQuantityEditText.getText().toString().trim();

        // Create database helper
        //DBHelper mDbHelper = new DBHelper(this);

        // Gets the database in write mode
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(groceryContract.GroceryEntry.COLUMN_NAME_TITLE, groceryName);
        values.put(groceryContract.GroceryEntry.COLUMN_NAME_PRICE, groceryPrice);
        values.put(groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY, groceryQuantity);

        //Uri newUri = getContentResolver().insert(groceryContract.GroceryEntry.CONTENT_URI, values);
        // Insert a new row for pet in the database, returning the ID of that new row.
//        long newRowId = db.insert(groceryContract.GroceryEntry.TABLE_NAME, null, values);


        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if(mCurrentUri == null){
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(groceryContract.GroceryEntry.CONTENT_URI,values);

            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                 // If the row ID is -1, then there was an error with insertion.
                 Toast.makeText(this, "Error with saving item", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast with the row ID.
                Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT).show();
            }
        }else{
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentUri,values,null,null);
            // Show a toast message depending on whether or not the update was successful.
            if(rowsAffected == 0){
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "Error with updating item", Toast.LENGTH_SHORT).show();
            }else{
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                groceryContract.GroceryEntry._ID,
                groceryContract.GroceryEntry.COLUMN_NAME_TITLE,
                groceryContract.GroceryEntry.COLUMN_NAME_PRICE,
                groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentUri,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if(cursor == null || cursor.getCount() < 1){
            return;
        }

        if (cursor.moveToFirst()){
            // Find the columns of pet attributes that we're interested in
            int titleColIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_TITLE);
            int priceColIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_PRICE);
            int quantityColIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY);


            // Extract out the value from the Cursor for the given column index
            String title = cursor.getString(titleColIndex);
            String price = cursor.getString(priceColIndex);
            String quantity = cursor.getString(quantityColIndex);

            // Update the views on the screen with the values from the database
            addNameEditText.setText(title);
            addPriceEditText.setText(price);
            addQuantityEditText.setText(quantity);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        addNameEditText.setText("");
        addPriceEditText.setText("");
        addQuantityEditText.setText("");
    }
}