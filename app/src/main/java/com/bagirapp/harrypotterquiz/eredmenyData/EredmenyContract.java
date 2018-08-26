package com.bagirapp.harrypotterquiz.eredmenyData;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class EredmenyContract {
    public static final String CONTENT_AUTHORITY = "com.bagirapp.harrypotterquiz";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "eredmenyek";


    private EredmenyContract() {
    }

    public static final class EredmenyEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        // Table name
        public static final String TABLE_NAME = "eredmenyek";

        //Column headers
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_DATE = "date";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_POINTS = "points";

    }
}
