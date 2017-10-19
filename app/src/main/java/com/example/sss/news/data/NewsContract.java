package com.example.sss.news.data;

import android.provider.BaseColumns;

/**
 * Created by sss on 24/9/17.
 */

public final class NewsContract {

    private NewsContract(){}


    public static abstract class newsData implements BaseColumns   {

        public static final String TABLE_NAME = "news";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_PUBLISHER = "publisher";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL2IMAGE = "imageUrl";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_CATEGORY = "category";


    }


    public static abstract class sourceData implements BaseColumns   {

        public static final String TABLE_NAME = "sources";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NEWS_ID = "news_id";
        public static final String COLUMN_TITLE = "title";

    }
}
