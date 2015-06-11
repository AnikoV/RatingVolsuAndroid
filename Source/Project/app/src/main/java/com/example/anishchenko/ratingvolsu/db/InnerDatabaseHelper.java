package com.example.anishchenko.ratingvolsu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.anishchenko.ratingvolsu.beans.BasePredmetBean;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.beans.MarkBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class InnerDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABSE_NAME = "rating";

    // все классы, добавляемые в базу
    private static final Class<?>[] CLASSES = new Class[]{BasePredmetBean.class, BaseStudentBean.class, MarkBean.class};

    public InnerDatabaseHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connection) {
        try {
            for (Class<?> clazz : CLASSES) {
                // создаем таблицу для класса
                TableUtils.createTable(connection, clazz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connection, int oldVersion, int newVersion) {
        try {
            for (Class<?> clazz : CLASSES) {
                // удаляем таблицу класса
                TableUtils.dropTable(connection, clazz, true);
            }
            onCreate(db, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
