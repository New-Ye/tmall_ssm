package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.Category;
import com.zxc.tmall.pojo.CategoryExample;
import java.util.List;

public interface CategoryMapper {

    //删除分类
    int deleteByPrimaryKey(Integer id);

    //新增分类
    int insert(Category record);

    //新增分类（动态SQL）
    int insertSelective(Category record);

    //显示所有分类
    List<Category> selectByExample(CategoryExample example);

    //用于编辑，指向地址admin_category_edit,并且会传递当前分类对象的id过去
    Category selectByPrimaryKey(Integer id);

    //修改分类(动态SQL)
    int updateByPrimaryKeySelective(Category record);

    //修改分类
    int updateByPrimaryKey(Category record);
}
