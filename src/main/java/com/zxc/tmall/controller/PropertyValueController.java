package com.zxc.tmall.controller;

import com.zxc.tmall.pojo.Product;
import com.zxc.tmall.pojo.PropertyValue;
import com.zxc.tmall.service.ProductService;
import com.zxc.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model,int pid) {
        //根据pid获取product对象，因为面包屑导航里需要显示产品的名称和分类的连接。
        Product p = productService.get(pid);
        //初始化属性值： propertyValueService.init(p)。
        // 因为在第一次访问的时候，这些属性值是不存在的，需要进行初始化。
        propertyValueService.init(p);
        //根据产品，获取其对应的属性值集合
        List<PropertyValue> pvs = propertyValueService.list(p.getId());

        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "admin/editPropertyValue";
    }
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue pv) {
        //修改功能采用的是使用post方式提交ajax的异步调用方式
        propertyValueService.update(pv);
        return "success";
    }
}

