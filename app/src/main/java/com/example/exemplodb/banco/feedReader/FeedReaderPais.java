package com.example.exemplodb.banco.feedReader;

import android.provider.BaseColumns;

public final class FeedReaderPais {

    private FeedReaderPais(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "pais";
        public static final String COLUMN_NAME_PAIS = "pais";
        public static final String COLUMN_NAME_ESTADOS = "estados";
    }

}
