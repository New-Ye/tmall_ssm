package com.zxc.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxc.tmall.pojo.Product;
import com.zxc.tmall.pojo.ProductImage;
import com.zxc.tmall.service.ProductImageService;
import com.zxc.tmall.service.ProductService;
import com.zxc.tmall.util.ImageUtil;
import com.zxc.tmall.util.UploadedImageFile;


@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    //新增产品图片
    @RequestMapping("admin_productImage_add")
    public String add(ProductImage  pi, HttpSession session, UploadedImageFile uploadedImageFile) {
        productImageService.add(pi);
        //文件命名以保存到数据库的产品图片对象的id+".jpg"的格式命名
        String fileName = pi.getId()+ ".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;
        if(ProductImageService.type_single.equals(pi.getType())){
            //定位到存放单个产品图片的目录
            imageFolder= session.getServletContext().getRealPath("img/productSingle");
            //每上传一张图片，都会有对应的正常，中等和小的三种大小图片，并且放在3个不同的目录下(小，中)
            imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
        }
        else{
            //正常大小图片
            imageFolder= session.getServletContext().getRealPath("img/productDetail");
        }

        File f = new File(imageFolder, fileName);
        f.getParentFile().mkdirs();
        try {
            //通过uploadedImageFile保存文件
            uploadedImageFile.getImage().transferTo(f);
            //借助ImageUtil.change2jpg()方法把格式真正转化为jpg，而不仅仅是后缀名为.jpg
            BufferedImage img = ImageUtil.change2jpg(f);
            ImageIO.write(img, "jpg", f);

            if(ProductImageService.type_single.equals(pi.getType())) {
                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);
                //再借助ImageUtil.resizeImage把正常大小的图片，改变大小之后，
                // 分别复制到productSingle_middle和productSingle_small目录下。
                ImageUtil.resizeImage(f, 56, 56, f_small);
                ImageUtil.resizeImage(f, 217, 190, f_middle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid="+pi.getPid();
    }

    //删除产品图片
    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session) {
        //面包屑导航栏
        ProductImage pi = productImageService.get(id);

        String fileName = pi.getId()+ ".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;

        //如果是单个图片，那么删除3张正常，中等，小号图片
        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder= session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
            File imageFile = new File(imageFolder,fileName);
            File f_small = new File(imageFolder_small,fileName);
            File f_middle = new File(imageFolder_middle,fileName);
            imageFile.delete();
            f_small.delete();
            f_middle.delete();

        }
        //如果是详情图片，那么删除一张图片
        else{
            imageFolder= session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder,fileName);
            imageFile.delete();
        }
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid="+pi.getPid();
    }

    //展示产品图片
    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        // 根据pid获取Product对象
        Product p =productService.get(pid);
        // 根据pid对象获取单个图片的集合pisSingle
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        // 根据pid对象获取详情图片的集合pisDetail
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);
        //把product 对象，pisSingle ，pisDetail放在model上
        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);

        return "admin/listProductImage";
    }
}

