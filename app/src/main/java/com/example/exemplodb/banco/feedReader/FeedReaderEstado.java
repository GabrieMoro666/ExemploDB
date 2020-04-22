package com.example.exemplodb.banco.feedReader;

import android.provider.BaseColumns;

public final class FeedReaderEstado {

    private FeedReaderEstado(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "estado";
        public static final String COLUMN_NAME_PAIS = "pais";
        public static final String COLUMN_NAME_ESTADO = "estado";
    }

}
