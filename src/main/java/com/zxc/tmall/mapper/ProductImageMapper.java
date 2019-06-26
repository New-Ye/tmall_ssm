package com.zxc.tmall.mapper;

import com.zxc.tmall.pojo.ProductImage;
import com.zxc.tmall.pojo.ProductImageExample;
import java.util.List;

public interface ProductImageMapper {
    //删除产品图片
    int deleteByPrimaryKey(Integer id);
    //新增产品图片
    int insert(ProductImage record);
    //新增产品图片
    int insertSelective(ProductImage record);

    //展示产品图片
    List<ProductImage> selectByExample(ProductImageExample example);

    //面包屑导航栏
    ProductImage selectByPrimaryKey(Integer id);
    //修改产品图片
    int updateByPrimaryKeySelective(ProductImage record);
    //修改产品图片
    int updateByPrimaryKey(ProductImage record);
}
