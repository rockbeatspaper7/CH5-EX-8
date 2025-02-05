package com.example.supermarket;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class SupermarketDataSource {
    private SQLiteDatabase database;
    private SupermarketDBHelper dbHelper;

    public SupermarketDataSource(Context context) {
        dbHelper = new SupermarketDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertContact(SupermarketInfo s) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("contactname", s.getMarketName());
            initialValues.put("streetaddress", s.getAddress());
            initialValues.put("city", s.getCity());
            initialValues.put("state", s.getState());
            initialValues.put("zipcode", s.getZipcode());

            long result = database.insert("market", null, initialValues);

            if (result > 0) {
                didSucceed = true;
            } else {
                Log.e("SupermarketDataSource", "Error inserting supermarket into database");
            }
        } catch (Exception e) {
            Log.e("SupermarketDataSource", "Error during supermarket insertion", e);
        }
        return didSucceed;
    }
    public boolean updateContact(SupermarketInfo s) {
        boolean didSucceed = false;
        try {
            Long rowId = s.getId();
            ContentValues updateValues = new ContentValues();
            updateValues.put("contactname", s.getMarketName());
            updateValues.put("streetaddress", s.getAddress());
            updateValues.put("city", s.getCity());
            updateValues.put("state", s.getState());
            updateValues.put("zipcode", s.getZipcode());

            int rowsUpdated = database.update("market", updateValues, "_id=" + rowId, null);

            if (rowsUpdated > 0) {
                didSucceed = true;
                Log.d("SupermarketDataSource", "Supermarket updated successfully, ID: " + rowId);
            } else {
                Log.e("SupermarketDataSource", "Error updating supermarket with ID: " + rowId);
            }
        } catch (Exception e) {
            Log.e("SupermarketDataSource", "Error during supermarket update", e);
        }
        return didSucceed;
    }

    public int getLastContactID() {
        int lastId;
        try {
            String query = "Select MAX (_id) from market";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }
}
