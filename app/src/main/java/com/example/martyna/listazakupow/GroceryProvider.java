package com.example.martyna.listazakupow;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.martyna.listazakupow.groceryContract.GroceryEntry;
import static com.example.martyna.listazakupow.groceryContract.GroceryEntry.CONTENT_AUTHORITY;
import static com.example.martyna.listazakupow.groceryContract.GroceryEntry.PATH_GROCERIES;

/**
 * Created by Martyna on 2017-11-21.
 */

public class GroceryProvider extends ContentProvider{
    //tag for log messages
    public static final String LOG_TAG = GroceryProvider.class.getSimpleName();

    //add Uri matcher
    private static final int GROCERIES = 100;
    private static final int GROCERY_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_GROCERIES, GROCERIES);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_GROCERIES + "/#", GROCERY_ID);
    }

    private DBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        
        switch (match){
            case GROCERIES:
                cursor = database.query(GroceryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GROCERY_ID:
                selection = GroceryEntry._ID + "=?";
                selectionArgs = new String[] {
                        String.valueOf(ContentUris.parseId(uri))
                };
                
                cursor = database.query(GroceryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case GROCERIES:
                return GroceryEntry.CONTENT_LIST_TYPE;
            case GROCERY_ID:
                return GroceryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case GROCERIES:
                return insertGrocery(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertGrocery(Uri uri, ContentValues values){
        String title = values.getAsString(GroceryEntry.COLUMN_NAME_TITLE);
        if (title.equals(null) || title.equals("")) {
            throw new IllegalArgumentException("Wpisz nazwę!");
        }

        String price = values.getAsString(GroceryEntry.COLUMN_NAME_PRICE);
        if (price.equals(null) || price.equals("")) {
            throw new IllegalArgumentException("Wpisz cenę!");
        }

        String quantity = values.getAsString(GroceryEntry.COLUMN_NAME_QUANTITY);
        if (quantity.equals(null) || quantity.equals("")) {
            throw new IllegalArgumentException("Wpisz ilość!");
        }

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        long id = database.insert(GroceryEntry.TABLE_NAME, null, values);
        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match){
            case GROCERIES:
                return database.delete(GroceryEntry.TABLE_NAME, selection, selectionArgs);
            case GROCERY_ID:
                selection = GroceryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return database.delete(GroceryEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case GROCERIES:
                return updateGrocery(uri, contentValues, selection, selectionArgs);
            case GROCERY_ID:
                selection = GroceryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                return updateGrocery(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateGrocery(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if(values.containsKey(GroceryEntry.COLUMN_NAME_TITLE)){
            String title = values.getAsString(GroceryEntry.COLUMN_NAME_TITLE);
            if (title.equals(null) || title.equals("")) {
                throw new IllegalArgumentException("Wpisz nazwę!");
            }
        }

        if(values.containsKey(GroceryEntry.COLUMN_NAME_PRICE)){
            String price = values.getAsString(GroceryEntry.COLUMN_NAME_PRICE);
            if (price.equals(null) || price.equals("")) {
                throw new IllegalArgumentException("Wpisz poprawną cenę!");
            }
        }

        if(values.containsKey(GroceryEntry.COLUMN_NAME_QUANTITY)){
            String quantity = values.getAsString(GroceryEntry.COLUMN_NAME_QUANTITY);
            if (quantity.equals(null) || quantity.equals("")) {
                throw new IllegalArgumentException("Wpisz poprawną ilość!");
            }
        }

        if(values.size() == 0){
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        return database.update(GroceryEntry.TABLE_NAME, values, selection, selectionArgs);

    }

}
