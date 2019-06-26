package com.zxc.tmall.service;

import java.util.List;

import com.zxc.tmall.pojo.Order;
import com.zxc.tmall.pojo.OrderItem;

public interface OrderItemService {

    //新增订单
    void add(OrderItem c);
    //购物车删除订单
    void delete(int id);
    //购物车提交订单（有多个商品）
    void update(OrderItem c);
    //拿到订单编号
    OrderItem get(int id);
    //展示所有订单
    List list();
    //填充订单的所有要购买的商品
    void fill(List<Order> os);
    //填充订单的要购买的商品
    void fill(Order o);

    //根据产品获取销售量
    int getSaleCount(int  pid);

    //用户购买的产品
    List<OrderItem> listByUser(int uid);
}

