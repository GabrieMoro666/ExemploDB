package com.example.exemplodb.banco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.exemplodb.banco.feedReader.FeedReaderPais;
import com.example.exemplodb.banco.dbHelper.FeedReaderDbHelper;
import com.example.exemplodb.banco.model.Pais;

public class PaisDao {
    private FeedReaderDbHelper dbHelper;

    public  PaisDao(Context context) {
        this.dbHelper = new FeedReaderDbHelper(context);
    }

    public long inserir(Pais pais) {
        long newRowId;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderPais.FeedEntry.COLUMN_NAME_PAIS, pais.getPais());

        newRowId = db.insert(FeedReaderPais.FeedEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public Pais buscar(String pais) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Pais p = new Pais();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderPais.FeedEntry.COLUMN_NAME_PAIS,
                FeedReaderPais.FeedEntry.COLUMN_NAME_ESTADOS
        };

        String selection = FeedReaderPais.FeedEntry.COLUMN_NAME_PAIS + " = ?";

        String[] selectionArgs = { pais };

        String sortOrder = FeedReaderPais.FeedEntry.COLUMN_NAME_PAIS; //"DESC"

        Cursor cursor = db.query(
                FeedReaderPais.FeedEntry.TABLE_NAME,   // The table to query
                projection,                            // The array of columns to return (pass null to get all)
                selection,                             // The columns for the WHERE clause
                selectionArgs,                         // The values for the WHERE clause
                null,                         // don't group the rows
                null,                          // don't filter by row groups
                 sortOrder                             // The sort order
        );

        if (cursor.moveToNext()) {
            p.setId(cursor.getLong(cursor.getColumnIndex(FeedReaderPais.FeedEntry._ID)));
            p.setPais(cursor.getString(cursor.getColumnIndex(FeedReaderPais.FeedEntry.COLUMN_NAME_PAIS)));
            p.setEstados(cursor.getInt(cursor.getColumnIndex(FeedReaderPais.FeedEntry.COLUMN_NAME_ESTADOS)));
        }
        cursor.close();

        return p;
    }

    public int atualizarEstados(String pais, int estados) {
        int rowsUpdate;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderPais.FeedEntry.COLUMN_NAME_ESTADOS, estados);


        String selection = FeedReaderPais.FeedEntry.COLUMN_NAME_PAIS + " = ?";
        String[] selectionArgs = { pais };

        rowsUpdate = db.update(
                FeedReaderPais.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return rowsUpdate;
    }

    public void limparTabela(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + FeedReaderPais.FeedEntry.TABLE_NAME);
    }

}
