package com.example.exemplodb.banco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.exemplodb.banco.feedReader.FeedReaderEstado;
import com.example.exemplodb.banco.dbHelper.FeedReaderDbHelper;
import com.example.exemplodb.banco.model.Estado;

import java.util.LinkedList;
import java.util.List;

public class EstadoDao {
    private FeedReaderDbHelper dbHelper;

    public EstadoDao(Context context) {
        this.dbHelper = new FeedReaderDbHelper(context);
    }

    public long inserir(Estado estado) {
        long newRowId;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS, estado.getPais());
        values.put(FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO, estado.getEstado());

        newRowId = db.insert(FeedReaderEstado.FeedEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public Estado buscar(String pais, String estado) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Estado e = new Estado();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS,
                FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO
        };

        String selection =  FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS   + " = ? AND " +
                            FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO + " = ?" ;

        String[] selectionArgs = { pais, estado };

        String sortOrder =  FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS + ", " +
                            FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO; //"DESC"

        Cursor cursor = db.query(
                FeedReaderEstado.FeedEntry.TABLE_NAME, // The table to query
                projection,                            // The array of columns to return (pass null to get all)
                selection,                             // The columns for the WHERE clause
                selectionArgs,                         // The values for the WHERE clause
                null,                         // don't group the rows
                null,                          // don't filter by row groups
                 sortOrder                             // The sort order
        );

        if (cursor.moveToNext()) {
            e.setId(cursor.getLong(cursor.getColumnIndex(FeedReaderEstado.FeedEntry._ID)));
            e.setPais(cursor.getString(cursor.getColumnIndex(FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS)));
            e.setEstado(cursor.getString(cursor.getColumnIndex(FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO)));
        }
        cursor.close();

        return e;
    }

    public int excluir(String pais, String estado) {
        int deletedRows;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS + " = ? AND " + FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO + " = ?";

        String[] selectionArgs = { pais, estado };

        deletedRows = db.delete(FeedReaderEstado.FeedEntry.TABLE_NAME, selection, selectionArgs);

        return deletedRows;
    }

    public List<Estado> buscarTudo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Estado> eAll = new LinkedList<>();
        Estado e = null;

        String[] projection = {
                BaseColumns._ID,
                FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS,
                FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO
        };

        String sortOrder =  FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS + ", " +
                            FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO; //"DESC"

        Cursor cursor = db.query(
                FeedReaderEstado.FeedEntry.TABLE_NAME, // The table to query
                projection,                            // The array of columns to return (pass null to get all)
                null,                         // The columns for the WHERE clause
                null,                      // The values for the WHERE clause
                null,                          // don't group the rows
                null,                           // don't filter by row groups
                sortOrder                               // The sort order
        );

        while (cursor.moveToNext()) {
            e = new Estado();

            e.setId(cursor.getLong(cursor.getColumnIndex(FeedReaderEstado.FeedEntry._ID)));
            e.setPais(cursor.getString(cursor.getColumnIndex(FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS)));
            e.setEstado(cursor.getString(cursor.getColumnIndex(FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO)));

            eAll.add(e);
        }
        cursor.close();

        return eAll;
    }

    public void limparTabela(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + FeedReaderEstado.FeedEntry.TABLE_NAME);
    }

}
