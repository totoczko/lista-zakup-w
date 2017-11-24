package com.example.martyna.listazakupow;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Martyna on 2017-11-22.
 */

public class GroceryCursorAdapter extends CursorAdapter {
    public GroceryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView idTextView = (TextView) view.findViewById(R.id.item_id);
        TextView titleTextView = (TextView) view.findViewById(R.id.item_title);
        TextView priceTextView = (TextView) view.findViewById(R.id.item_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.item_quantity);

        // Find the columns of pet attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry._ID);
        int titleColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_TITLE);
        int priceColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(groceryContract.GroceryEntry.COLUMN_NAME_QUANTITY);

        // Read the pet attributes from the Cursor for the current pet
        int itemID = cursor.getInt(idColumnIndex);
        String itemTitle = cursor.getString(titleColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);

        // Update the TextViews with the attributes for the current pet
        idTextView.setText(Integer.toString(itemID));
        titleTextView.setText(itemTitle);
        priceTextView.setText(itemPrice);
        quantityTextView.setText(itemQuantity);
    }

}
