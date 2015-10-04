package com.monkeycoders.incentavo.incentivo.models.Dao;

import com.monkeycoders.incentavo.incentivo.models.ChildrenData;
import java.util.List;


public interface ChildDao {

    public ChildrenData getChildren(int id);
    public List<ChildrenData> getChildrens();
    public void saveChildrens(List<ChildrenData> childrens);
}
