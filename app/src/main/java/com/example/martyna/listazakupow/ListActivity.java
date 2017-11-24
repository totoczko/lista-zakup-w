package com.example.martyna.listazakupow;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.martyna.listazakupow.groceryContract.GroceryEntry;

public class ListActivity extends parentActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    /** Identifier for the pet data loader */
    private static final int GROCERY_LOADER = 0;

    /** Adapter for the ListView */
    GroceryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
      //  displayDatabaseInfo();
        ListView groceryList = (ListView) findViewById(R.id.groceryList);
        mCursorAdapter = new GroceryCursorAdapter(this, null);
        groceryList.setAdapter(mCursorAdapter);

        groceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, AddItemActivity.class);
                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link PetEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.
                Uri currentUri = ContentUris.withAppendedId(GroceryEntry.CONTENT_URI, id);
                System.out.println("currentPetUri = " + currentUri);

                // Set the URI on the data field of the intent
                intent.setData(currentUri);

                // Launch the {@link AddItemActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(GROCERY_LOADER, null, this);

    }

    protected void onResume(){
        super.onResume();
        //displayDatabaseInfo();
    }

//    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //DBHelper mDbHelper = new DBHelper(this);

        // Create and/or open a database to read from it
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //reading from db
        //which column are we interested in
//        String[] projection = {
//                groceryContract.GroceryEntry._ID,
//                groceryContract.GroceryEntry.COLUMN_NAME_TITLE,
//                groceryContract.GroceryEntry.COLUMN_NAME_PRICE,
//                groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY
//        };

        //selection, selectionArgs - which row are we interested in

//        Cursor cursor = db.query(
//                groceryContract.GroceryEntry.TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null);

        //use ContentProvider
        //Cursor cursor = getContentResolver().query(groceryContract.GroceryEntry.CONTENT_URI, projection, null, null, null, null);

        //display the list items
        //TextView displayView = (TextView) findViewById(R.id.checkDB);

//        try {
//            // Display the number of rows in the Cursor (which reflects the number of rows in the
//            // pets table in the database).
//            displayView.setText("Number of rows in grocery database table: " + cursor.getCount());
//
//            int idColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry._ID);
//            int titleColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_TITLE);
//            int priceColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_PRICE);
//            int quantityColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY);
//
//            while(cursor.moveToNext()){
//                int currentID = cursor.getInt(idColumnIndex);
//                String currentTitle = cursor.getString(titleColumnIndex);
//                String currentPrice = cursor.getString(priceColumnIndex);
//                int currentQuantity = cursor.getInt(quantityColumnIndex);
//                displayView.append("\n" + currentID + " - " + currentTitle + " - " + currentPrice + " - " + currentQuantity);
//            }
//        } finally {
//            // Always close the cursor when you're done reading from it. This releases all its
//            // resources and makes it invalid.
//            cursor.close();
//        }

        //ListView groceryList = (ListView) findViewById(R.id.groceryList);

        //       // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        //GroceryCursorAdapter adapter = new GroceryCursorAdapter(this, cursor);

        // Attach the adapter to the ListView.
        //groceryList.setAdapter(adapter);




    public void goToAddNew(View v){
        Intent intent_add = new Intent(this, AddItemActivity.class);
        startActivity(intent_add);
    }

    public void editItem(View v){
//        Intent intent_edit = new Intent(ListActivity.this, AddItemActivity.class);
//        // Form the content URI that represents the specific pet that was clicked on,
//        // by appending the "id" (passed as input to this method) onto the
//        // {@link PetEntry#CONTENT_URI}.
//        // For example, the URI would be "content://com.example.android.pets/pets/2"
//        // if the pet with ID 2 was clicked on.
//        Uri currentPetUri = ContentUris.withAppendedId(GroceryEntry.CONTENT_URI, id);
//        System.out.println("currentPetUri = " + currentPetUri);
//
//        // Set the URI on the data field of the intent
//        intent_edit.setData(currentPetUri);
//
//        // Launch the {@link AddItemActivity} to display the data for the current pet.
//        startActivity(intent_edit);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                GroceryEntry._ID,
                GroceryEntry.COLUMN_NAME_TITLE,
                GroceryEntry.COLUMN_NAME_PRICE,
                GroceryEntry.COLUMN_NAME_QUANTITY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                GroceryEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link GroceryCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}