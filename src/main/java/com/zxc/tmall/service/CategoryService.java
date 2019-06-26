package com.zxc.tmall.service;

import com.zxc.tmall.pojo.Category;
import java.util.List;

public interface CategoryService{
    //显示所有分类
    List<Category> list();

    //新增分类
    void add(Category category);

    //删除分类
    void delete(int id);

    //用于编辑，指向地址admin_category_edit,并且会传递当前分类对象的id过去
    Category get(int id);

    //修改分类(动态SQL)
    void update(Category category);
}
