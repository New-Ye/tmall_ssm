package com.zxc.tmall.service;
 
import java.util.List;

import com.zxc.tmall.pojo.Review;

public interface ReviewService {
     
    //增加评论到数据库
    void add(Review c);

    //删除评论
    void delete(int id);

    //修改评论
    void update(Review c);

    //通过产品获取评价
    Review get(int id);

    //展示所有评论
    List list(int pid);

    //展示评论总数
    int getCount(int pid);
}
