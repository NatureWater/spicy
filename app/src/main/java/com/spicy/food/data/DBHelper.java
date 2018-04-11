package com.spicy.food.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import de.greenrobot.dao.query.QueryBuilder;

public class DBHelper {
    private static Context mContext;
    private static DBHelper instance;

    private static final String DEFAULT_DB_NAME = "Diary-db";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static DaoMaster.DevOpenHelper sHelper;

    public static DBHelper getInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        if (instance == null) {
            instance = new DBHelper();
            if (mContext == null) {
                mContext = context;
            }
            if (daoMaster == null) {
                sHelper = new DaoMaster.DevOpenHelper(mContext,
                        DEFAULT_DB_NAME, null);
                SQLiteDatabase db = sHelper.getWritableDatabase();
                daoMaster = new DaoMaster(db);
            }
            if (daoSession == null) {
                daoSession = daoMaster.newSession();
            }

            daoSession.getDiaryDao();
            enableQueryBuilderLog();

        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static void enableQueryBuilderLog() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        closeDaoSession();

        closeHelper();
        instance = null;
    }

    private void closeDaoSession() {
        if (null != daoSession) {
            daoSession.clear();
            daoSession = null;
            daoMaster = null;
        }
    }

    private void closeHelper() {
        if (sHelper != null) {
            sHelper.close();
            sHelper = null;
        }
    }

}
