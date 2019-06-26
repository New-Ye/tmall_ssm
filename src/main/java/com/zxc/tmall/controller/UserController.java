package com.zxc.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxc.tmall.pojo.User;
import com.zxc.tmall.service.UserService;
import com.zxc.tmall.util.Page;


@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;
 
    @RequestMapping("admin_user_list")
    public String list(Model model, Page page){
        //获取分页对象,设置分页信息
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //查询用户集合
        List<User> us= userService.list();
        //通过PageInfo获取总数，并设置在page对象上
        int total = (int) new PageInfo<>(us).getTotal();
        //把用户集合设置到model的"us"属性上
        page.setTotal(total);
        //把分页对象设置到model的"page"属性上
        //服务端跳转到admin/listUser.jsp页面
        model.addAttribute("us", us);
        model.addAttribute("page", page);

        return "admin/listUser";
    }



}
