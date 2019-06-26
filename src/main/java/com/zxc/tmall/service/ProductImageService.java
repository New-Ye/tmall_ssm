package com.zxc.tmall.service;
 
import java.util.List;

import com.zxc.tmall.pojo.ProductImage;

public interface ProductImageService {

    String type_single = "type_single";
    String type_detail = "type_detail";
    //增加产品图片
    void add(ProductImage pi);
    //删除产品图片
    void delete(int id);
    //修改产品图片
    void update(ProductImage pi);

    //面包屑导航栏
    ProductImage get(int id);
    //展示产品图片
    List list(int pid, String type);
}
