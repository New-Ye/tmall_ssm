package com.zxc.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxc.tmall.pojo.Category;
import com.zxc.tmall.pojo.Product;
import com.zxc.tmall.service.CategoryService;
import com.zxc.tmall.service.ProductService;
import com.zxc.tmall.util.Page;

//产品管理
@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    //新增产品
    @RequestMapping("admin_product_add")
    public String add(Model model, Product p) {
        productService.add(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    //删除产品
    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        Product p = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    //编辑产品
    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id) {
        Product p = productService.get(id);
        //根据product对象的cid产品获取Category对象，并把其设置在product对象的category产品上
        Category c = categoryService.get(p.getCid());
        //把product对象放在request的 "p" 产品中
        p.setCategory(c);
        model.addAttribute("p", p);
        return "admin/editProduct";
    }

    //前往编辑页面
    @RequestMapping("admin_product_update")
    public String update(Product p) {
        productService.update(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    //展示所有产品
    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page) {
        Category c = categoryService.get(cid);
        //过PageHelper设置分页参数
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //基于cid，获取当前分类下的产品集合
        List<Product> ps = productService.list(cid);
        //通过PageInfo获取产品总数
        int total = (int) new PageInfo<>(ps).getTotal();
        // 把总数设置给分页page对象
        page.setTotal(total);
        //拼接字符串"&cid="+c.getId()，设置给page对象的Param值。
        // 因为产品分页都是基于当前分类下的分页，所以分页的时候需要传递这个cid
        page.setParam("&cid="+c.getId());
        //把产品集合设置到 request的 "ps" 产品上
        model.addAttribute("ps", ps);
        //把分类对象设置到 request的 "c" 产品上。(面包屑导航)
        model.addAttribute("c", c);
        //把分页对象设置到 request的 "page" 对象上
        model.addAttribute("page", page);

        return "admin/listProduct";
    }
}


