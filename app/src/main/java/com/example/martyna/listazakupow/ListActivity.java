package com.example.martyna.listazakupow;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ListActivity extends parentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        DBHelper mDbHelper = new DBHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //reading from db
        //which column are we interested in
        String[] projection = {
                groceryContract.GroceryEntry._ID,
                groceryContract.GroceryEntry.COLUMN_NAME_TITLE,
                groceryContract.GroceryEntry.COLUMN_NAME_PRICE,
                groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY
        };

        //selection, stectionArgs - which row are we interested in

        Cursor cursor = db.query(
                groceryContract.GroceryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        //display the list items
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.checkDB);
            displayView.setText("Number of rows in grocery database table: " + cursor.getCount());

            int idColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_TITLE);
            int priceColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY);

            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                String currentPrice = cursor.getString(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);

                displayView.append("\n" + currentID + " - " + currentTitle + " - " + currentPrice + " - " + currentQuantity);
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    public void goToAddNew(View v){
        Intent intent_add = new Intent(this, AddItemActivity.class);
        startActivity(intent_add);
    }
}
