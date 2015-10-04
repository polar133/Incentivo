package com.monkeycoders.incentavo.incentivo.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.monkeycoders.incentavo.incentivo.models.ChildrenData;


public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myincentivo.db";
    static private DBHelper instance;
    //RuntimeExceptionDao
    private RuntimeExceptionDao<ChildrenData, Integer> ChildrenDaoRuntime;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static public void init(Context ctx) {
        if (null == instance) {
            instance = new DBHelper(ctx);
        }
    }

    static public DBHelper getInstance() {
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ChildrenData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAllTables() {
        try {
            TableUtils.createTable(getConnectionSource(), ChildrenData.class);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDelete() {
        try {
            TableUtils.clearTable(getConnectionSource(), ChildrenData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        ChildrenDaoRuntime = null;
    }

    public RuntimeExceptionDao<ChildrenData, Integer> getChildrenDataDao() {
        if (ChildrenDaoRuntime == null) {
            ChildrenDaoRuntime = getRuntimeExceptionDao(ChildrenData.class);
        }
        return ChildrenDaoRuntime;
    }
}