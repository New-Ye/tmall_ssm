package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.User;
import com.zxc.tmall.pojo.UserExample;
import java.util.List;

public interface UserMapper {

    //用户注销
    int deleteByPrimaryKey(Integer id);
    //用户注册
    int insert(User record);
    //用户注册（动态SQL）
    int insertSelective(User record);

    //后台展示所有用户
    List<User> selectByExample(UserExample example);

    //用户个人信息查询
    User selectByPrimaryKey(Integer id);
    //用户修改个人信息(动态SQL)
    int updateByPrimaryKeySelective(User record);
    //用户修改个人信息
    int updateByPrimaryKey(User record);
}

