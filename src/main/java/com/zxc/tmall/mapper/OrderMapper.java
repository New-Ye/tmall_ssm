package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.Order;
import com.zxc.tmall.pojo.OrderExample;
import java.util.List;

public interface OrderMapper {
    //删除订单
    int deleteByPrimaryKey(Integer id);
    //新增订单
    int insert(Order record);
    //新增订单(动态SQL)
    int insertSelective(Order record);

    //展示所有订单
    List<Order> selectByExample(OrderExample example);
    //通过oid获取订单对象o
    Order selectByPrimaryKey(Integer id);

    //发货(动态SQL)
    int updateByPrimaryKeySelective(Order record);
    //发货
    int updateByPrimaryKey(Order record);
}

