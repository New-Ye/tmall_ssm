package com.zxc.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxc.tmall.pojo.Category;
import com.zxc.tmall.service.CategoryService;
import com.zxc.tmall.util.ImageUtil;
import com.zxc.tmall.util.Page;
import com.zxc.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
 
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //显示所有分类
    @RequestMapping("admin_category_list")
    public String list(Model model,Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //为方法list增加参数Page用于获取浏览器传递过来的分页信息
        List<Category> cs= categoryService.list();
        //获取分页总数
        int total = (int) new PageInfo<>(cs).getTotal();
        //为分页对象设置总数
        page.setTotal(total);
        //把分类集合放在"cs"中
        model.addAttribute("cs", cs);
        //把分页对象放在 "page“ 中
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    //新增分类
    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
//        1 参数 Category c接受页面提交的分类名称
//        2 参数 session 用于在后续获取当前应用的路径
//        3 UploadedImageFile 用于接受上传的图片
        //通过categoryService保存c对象
        categoryService.add(c);
        //通过session获取ControllerContext,再通过getRealPath定位存放分类图片的路径
        //图片就会存放在:E:\project\tmall_ssm\target\tmall_ssm\img\category 这里
        File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
        //根据分类id创建文件名
        File file = new File(imageFolder,c.getId()+".jpg");
        //如果/img/category目录不存在，则创建该目录，否则后续保存浏览器传过来图片，会提示无法保存
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        //通过UploadedImageFile 把浏览器传递过来的图片保存在上述指定的位置
        uploadedImageFile.getImage().transferTo(file);
        //通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg.
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return "redirect:/admin_category_list";
    }

    //删除分类
    @RequestMapping("admin_category_delete")
    public String delete(int id,HttpSession session) throws IOException {
        //通过categoryService删除数据
        categoryService.delete(id);
        //通过session获取ControllerContext然后获取分类图片位置，接着删除分类图片
        File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();

        return "redirect:/admin_category_list";
    }

    //跳转到editCategory
    @RequestMapping("admin_category_edit")
    public String edit(int id,Model model) throws IOException {
        //通过categoryService.get获取Category对象
        Category c= categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
    }

    //修改分类
    @RequestMapping("admin_category_update")
    public String update(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(c);
        //通过UploadedImageFile 把浏览器传递过来的图片保存在上述指定的位置
        MultipartFile image = uploadedImageFile.getImage();
        //首先判断是否有上传图片，如果有上传，那么通过session获取ControllerContext,
        // 再通过getRealPath定位存放分类图片的路径。
        if(null!=image &&!image.isEmpty()){
            File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder,c.getId()+".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }

}
