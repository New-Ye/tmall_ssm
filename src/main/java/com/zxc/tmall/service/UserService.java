package com.zxc.tmall.service;
 
import java.util.List;

import com.zxc.tmall.pojo.User;

public interface UserService {
    //用户注册
    void add(User c);
    //用户注销
    void delete(int id);
    //用户修改个人信息
    void update(User c);
    //用户个人信息查询
    User get(int id);
    //后台展示所有用户
    List list();
    //注册时，判断某个名称是否已经被使用过了
    boolean isExist(String name);
    //登录
    User get(String name, String password);
}
