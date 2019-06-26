package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.Review;
import com.zxc.tmall.pojo.ReviewExample;
import java.util.List;

public interface ReviewMapper {
    //删除评论
    int deleteByPrimaryKey(Integer id);
    //增加评论到数据库
    int insert(Review record);
    //增加评论到数据库
    int insertSelective(Review record);

    //展示所有评论
    List<Review> selectByExample(ReviewExample example);
    //通过产品获取评价
    Review selectByPrimaryKey(Integer id);

    //修改评论
    int updateByPrimaryKeySelective(Review record);
    //修改评论
    int updateByPrimaryKey(Review record);
}

