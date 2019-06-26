package com.zxc.tmall.service;

import java.util.List;

import com.zxc.tmall.pojo.Category;
import com.zxc.tmall.pojo.Product;

public interface ProductService {
    //新增产品
    void add(Product p);
    //删除产品
    void delete(int id);
    //修改产品
    void update(Product p);
    //展示产品第一张图片
    Product get(int id);
    //展示所有产品
    List list(int cid);
    //产品管理里的页面截图
    void setFirstProductImage(Product p);

    //为多个分类填充产品集合
    void fill(List<Category> cs);

    //为分类填充产品集合
    void fill(Category c);

    //为多个分类填充推荐产品集合，
    // 即把分类下的产品集合，按照8个为一行，拆成多行，以利于后续页面上进行显示
    void fillByRow(List<Category> cs);

    //为产品设置销量和评价数量
    void setSaleAndReviewNumber(Product p);

    //为多个产品设置销量和评价数量
    void setSaleAndReviewNumber(List<Product> ps);
    //价格区间
    List<Product> search(String keyword);
}
