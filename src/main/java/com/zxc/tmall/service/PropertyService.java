package com.zxc.tmall.service;
 
import com.zxc.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {
    //新增属性
    void add(Property c);
    //删除属性
    void delete(int id);
    //编辑属性
    void update(Property c);

    //面包屑导航
    Property get(int id);
    //展示属性列表
    List list(int cid);
}
