package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.PropertyValue;
import com.zxc.tmall.pojo.PropertyValueExample;
import java.util.List;

public interface PropertyValueMapper {
    ////删除属性
    int deleteByPrimaryKey(Integer id);

    //新增产品属性
    int insert(PropertyValue record);

    //新增产品属性（动态SQL）
    int insertSelective(PropertyValue record);

    //展示产品的属性
    List<PropertyValue> selectByExample(PropertyValueExample example);

    //面包屑导航
    PropertyValue selectByPrimaryKey(Integer id);

    //修改商品属性（动态SQL）
    int updateByPrimaryKeySelective(PropertyValue record);

    //修改商品属性
    int updateByPrimaryKey(PropertyValue record);
}

