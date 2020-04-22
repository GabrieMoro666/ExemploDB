package com.example.exemplodb.banco.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.exemplodb.banco.feedReader.FeedReaderEstado;
import com.example.exemplodb.banco.feedReader.FeedReaderPais;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    Context context;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_ENTRIES_PAIS = "CREATE TABLE " +     FeedReaderPais.FeedEntry.TABLE_NAME         + " ("                    +
                                                                                FeedReaderPais.FeedEntry._ID                + " INTEGER PRIMARY KEY," +
                                                                                FeedReaderPais.FeedEntry.COLUMN_NAME_PAIS   + " TEXT NOT NULL,"       +
                                                                                FeedReaderPais.FeedEntry.COLUMN_NAME_ESTADOS+ " INTEGER"              +
                                                                            ")";

    private static final String SQL_DELETE_ENTRIES_PAIS = "DROP TABLE IF EXISTS " + FeedReaderPais.FeedEntry.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_ESTADO = "CREATE TABLE " +   FeedReaderEstado.FeedEntry.TABLE_NAME         + " ("                     +
                                                                                FeedReaderEstado.FeedEntry._ID                + " INTEGER  PRIMARY KEY," +
                                                                                FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS   + " TEXT NOT NULL,"        +
                                                                                FeedReaderEstado.FeedEntry.COLUMN_NAME_ESTADO + " TEXT NOT NULL,"        +
                                                                                "FOREIGN KEY(" +
                                                                                    FeedReaderEstado.FeedEntry.COLUMN_NAME_PAIS +
                                                                                ")" +
                                                                                " REFERENCES " + FeedReaderPais.FeedEntry.TABLE_NAME + "(" +
                                                                                    FeedReaderPais.FeedEntry.COLUMN_NAME_PAIS +
                                                                                ")" +
                                                                            ")";

    private static final String SQL_DELETE_ENTRIES_ESTADO = "DROP TABLE IF EXISTS " + FeedReaderEstado.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_PAIS);
        db.execSQL(SQL_CREATE_ENTRIES_ESTADO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_PAIS);
        db.execSQL(SQL_DELETE_ENTRIES_ESTADO);
    }

}
