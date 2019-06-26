package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.Product;
import com.zxc.tmall.pojo.ProductExample;
import java.util.List;

public interface ProductMapper {
    //删除产品
    int deleteByPrimaryKey(Integer id);

    //新增产品
    int insert(Product record);

    //新增产品(动态SQL)
    int insertSelective(Product record);

    //展示所有产品
    List<Product> selectByExample(ProductExample example);

    //展示产品第一张图片
    Product selectByPrimaryKey(Integer id);

    //修改产品
    int updateByPrimaryKeySelective(Product record);

    //修改产品（动态SQL）
    int updateByPrimaryKey(Product record);
}
