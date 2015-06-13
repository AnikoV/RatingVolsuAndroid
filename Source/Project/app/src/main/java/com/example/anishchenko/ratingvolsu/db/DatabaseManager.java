package com.example.anishchenko.ratingvolsu.db;

import android.content.Context;

import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.beans.MarkBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public enum DatabaseManager {

    INSTANCE;

    private InnerDatabaseHelper mInnerDBHelper;

    public void init(Context context) {
        mInnerDBHelper = new InnerDatabaseHelper(context);
    }

    public void release() {
        if (mInnerDBHelper != null) mInnerDBHelper.close();
    }

    // ФУНКЦИИ ДОБАВЛЕНИЯ

    public <T> void addObject(final T dataObject, Class<T> clazz) {
        if (dataObject == null) return;

        try {
            Dao<T, Integer> dao = mInnerDBHelper.getDao(clazz);
            dao.createOrUpdate(dataObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public <T> void AddList(final ArrayList<T> dataList, Class<T> clazz) {
        if (dataList == null || dataList.size() == 0) return;

        try {
            final Dao<T, Integer> dao = mInnerDBHelper.getDao(clazz);
            dao.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {

                    int size = dataList.size();
                    for (int i = 0; i < size; i++)
                        dao.createOrUpdate(dataList.get(i));

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ФУНКЦИИ ИЗВЛЕЧЕНИЯ

    public <T> T getObject(String id, Class<T> clazz) {
        try {
            Dao<T, String> dao = mInnerDBHelper.getDao(clazz);
            return dao.queryForId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> ArrayList<T> getList(Class<T> clazz) {
        try {
            Dao<T, Integer> dao = mInnerDBHelper.getDao(clazz);
            return (ArrayList<T>) dao.queryForAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<BaseStudentBean> getStudentList(String mark_set) {
        try {
            Dao<BaseStudentBean, Integer> dao = mInnerDBHelper.getDao(BaseStudentBean.class);
            QueryBuilder<BaseStudentBean, Integer> builer = dao.queryBuilder();
            builer.where().eq("markId", mark_set);
            return (ArrayList<BaseStudentBean>) builer.query();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<MarkBean> getMarkList(boolean isFav) {
        try {
            Dao<MarkBean, Integer> dao = mInnerDBHelper.getDao(MarkBean.class);
            QueryBuilder<MarkBean, Integer> builer = dao.queryBuilder();
            builer.where().eq("isFavorite", isFav);
            return (ArrayList<MarkBean>) builer.query();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ФУНКЦИИ ОЧИСТКИ

    public <T> void deleteById(int id, Class<T> clazz) {
        try {
            Dao<T, Integer> dao = mInnerDBHelper.getDao(clazz);
            dao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
