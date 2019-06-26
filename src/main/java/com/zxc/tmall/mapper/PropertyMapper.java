package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.Property;
import com.zxc.tmall.pojo.PropertyExample;
import java.util.List;

public interface PropertyMapper {
    //删除属性
    int deleteByPrimaryKey(Integer id);

    //新增属性
    int insert(Property record);

    //新增属性(动态SQL)
    int insertSelective(Property record);

    //展示所有属性
    List<Property> selectByExample(PropertyExample example);

    //根据ID展示属性，用于面包屑导航
    Property selectByPrimaryKey(Integer id);

    //修改属性(动态SQL)
    int updateByPrimaryKeySelective(Property record);

    //修改属性
    int updateByPrimaryKey(Property record);
}

