package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.OrderItem;
import com.zxc.tmall.pojo.OrderItemExample;
import java.util.List;

public interface  OrderItemMapper {
    int deleteByPrimaryKey(Integer id);
    //新增订单
    int insert(OrderItem record);
    //新增订单
    int insertSelective(OrderItem record);

    //根据产品获取销售量
    List<OrderItem> selectByExample(OrderItemExample example);

    //拿到订单编号
    OrderItem selectByPrimaryKey(Integer id);

    //提交订单（有多个商品）
    int updateByPrimaryKeySelective(OrderItem record);
    //提交订单（有多个商品）
    int updateByPrimaryKey(OrderItem record);
}

