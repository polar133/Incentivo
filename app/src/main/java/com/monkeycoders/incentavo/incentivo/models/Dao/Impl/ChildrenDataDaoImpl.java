package com.monkeycoders.incentavo.incentivo.models.Dao.Impl;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.monkeycoders.incentavo.incentivo.models.ChildrenData;
import com.monkeycoders.incentavo.incentivo.models.Dao.ChildDao;
import com.monkeycoders.incentavo.incentivo.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ChildrenDataDaoImpl implements ChildDao {

    public ChildrenData getChildren(int id){
        RuntimeExceptionDao<ChildrenData, Integer> childrenDao = DBHelper.getInstance().getChildrenDataDao();
        return childrenDao.queryForId(id);
    }

    public List<ChildrenData> getChildrens() {
        RuntimeExceptionDao<ChildrenData, Integer> childrenDao = DBHelper.getInstance().getChildrenDataDao();
        if (childrenDao.queryForAll().size() >= 1) return childrenDao.queryForAll();
        else return new ArrayList<>();
    }

    public void saveChildrens(List<ChildrenData> childrens) {

        RuntimeExceptionDao<ChildrenData, Integer> childrenDao = DBHelper.getInstance().getChildrenDataDao();
        if (!childrenDao.isTableExists()) {
            DBHelper.getInstance().createAllTables();
        }
        for (ChildrenData children : childrens){
            childrenDao.create(children);
        }

    }
}
