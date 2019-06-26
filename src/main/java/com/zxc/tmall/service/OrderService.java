package com.zxc.tmall.service;

import java.util.List;

import com.zxc.tmall.pojo.Order;
import com.zxc.tmall.pojo.OrderItem;

public interface OrderService {

    //提供订单状态的常量值
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";
    //新增订单
    void add(Order c);

    //提交订单（有多个商品）
    float add(Order c,List<OrderItem> ois);
    //删除订单
    void delete(int id);
    //确认收货成功，修改订单状态
    void update(Order c);
    //通过oid获取订单对象o
    Order get(int id);

    //展示所有订单
    List list();
    //我的订单页(订单的状态，待发货，待付款。。。)
    List list(int uid, String excludedStatus);
}

