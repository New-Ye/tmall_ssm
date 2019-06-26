package com.zxc.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.zxc.tmall.pojo.*;
import com.zxc.tmall.service.*;
import comparator.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    @RequestMapping("forehome")
    //前台首页
    public String home(Model model) {
        //查询所有分类
        List<Category> cs= categoryService.list();
        //为这些分类填充产品集合
        productService.fill(cs);
        // 为这些分类填充推荐产品集合
        productService.fillByRow(cs);
         model.addAttribute("cs", cs);
        return "fore/home";
    }

    //注册
    @RequestMapping("foreregister")
    public String register(Model model,User user) {
        String name =  user.getName();
//        通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        //判断用户名是否存在
        if(exist){
            //服务端跳转到reigster.jsp，并且带上错误提示信息
            String m ="用户名已经被使用,不能使用";
            model.addAttribute("msg", m);
            return "fore/register";
        }
        //不存在，则加入到数据库中，并服务端跳转到registerSuccess.jsp页面
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

    //登录
    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        //把账号通过HtmlUtils.htmlEscape进行转义
        name = HtmlUtils.htmlEscape(name);
        //根据账号和密码获取User对象
        User user = userService.get(name,password);

        if(null==user){
            //如果对象为空，则服务端跳转回login.jsp，也带上错误信息，并且使用 loginPage.jsp 中的办法显示错误信息
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        //如果对象存在，则把对象保存在session中，并客户端跳转到首页"forehome"
        session.setAttribute("user", user);
        return "redirect:forehome";
    }
    //退出
    @RequestMapping("forelogout")
    public String logout( HttpSession session) {
        //在session中去掉"user"
        session.removeAttribute("user");
        //客户端跳转到首页:
        return "redirect:forehome";
    }

    //产品页的显示
    @RequestMapping("foreproduct")
    public String product( int pid, Model model) {
        //根据pid获取Product 对象p
        Product p = productService.get(pid);
        //根据对象p，获取这个产品对应的单个图片集合
        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        //根据对象p，获取这个产品对应的详情图片集合
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        //获取产品的所有属性值
        List<PropertyValue> pvs = propertyValueService.list(p.getId());
        // 获取产品对应的所有的评价
        List<Review> reviews = reviewService.list(p.getId());
        //设置产品的销量和评价数量
        productService.setSaleAndReviewNumber(p);
        //把上述取值放在request属性上
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user)
            return "success";
        return "fail";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    //modal.jsp中，点击了登录按钮之后
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password,HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);

        if(null==user){
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    //按商品要求排序
    @RequestMapping("forecategory")
    public String category(int cid,String sort, Model model) {
        //根据cid获取分类Category对象 c
        Category c = categoryService.get(cid);
        //为c填充产品
        productService.fill(c);
        //为产品填充销量和评价数据
        productService.setSaleAndReviewNumber(c.getProducts());

        //默认综合排序
        //如果sort==null，即不排序
        if(null!=sort){
            switch(sort){
                case "review":
                    Collections.sort(c.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(c.getProducts(),new ProductDateComparator());
                    break;

                case "saleCount" :
                    Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(c.getProducts(),new ProductAllComparator());
                    break;
            }
        }

        model.addAttribute("c", c);
        return "fore/category";
    }

    //价格区间
    @RequestMapping("foresearch")
    public String search( String keyword,Model model){

        //根据keyword进行模糊查询，获取满足条件的前20个产品
        PageHelper.offsetPage(0,20);
        List<Product> ps= productService.search(keyword);
        //为这些产品设置销量和评价数量
        productService.setSaleAndReviewNumber(ps);
        //把产品结合设置在model的"ps"属性上
        model.addAttribute("ps",ps);
        return "fore/searchResult";
    }

    //产品页增加数量，入购物车或者立即购买
    @RequestMapping("forebuyone")
    //1. 获取参数pid
    //2. 获取参数num
    //3. 根据pid获取产品对象p
    //4. 从session中获取用户对象user
    public String buyone(int pid, int num, HttpSession session) {
        Product p = productService.get(pid);
        int oiid = 0;

        User user =(User)  session.getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            // 基于用户对象user，查询没有生成订单的订单项集合
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                //如果产品是一样的话，就进行数量追加
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                //获取这个订单项的 id
                oiid = oi.getId();
                break;
            }
        }

        if(!found){
            //如果不存在对应的OrderItem,那么就新增一个订单项OrderItem
            //生成新的订单项
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            //设置数量，用户和产品
            oi.setNumber(num);
            oi.setPid(pid);
            //插入到数据库
            orderItemService.add(oi);
            //获取这个订单项的 id
            oiid = oi.getId();
        }
        return "redirect:forebuy?oiid="+oiid;
    }

    //结算页面
    @RequestMapping("forebuy")
    //通过字符串数组获取参数oiid
    // (结算页面还需要显示在购物车中选中的多条OrderItem数据，所以为了兼容从购物车页面跳转过来的需求，要用字符串数组获取多个oiid)
    public String buy( Model model,String[] oiid,HttpSession session){
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;
        //根据前面步骤获取的oiids，从数据库中取出OrderItem对象，并放入ois集合中
        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi= orderItemService.get(id);
            // 累计这些ois的价格总数，赋值在total上
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
            ois.add(oi);
        }
        //把订单项集合放在session的属性 "ois" 上
        session.setAttribute("ois", ois);
        //把总价格放在 model的属性 "total" 上
        model.addAttribute("total", total);
        return "fore/buy";
    }

    //在产品页点击加入购物车
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model,HttpSession session) {
        Product p = productService.get(pid);
        User user =(User)  session.getAttribute("user");
        boolean found = false;

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            //如果已经存在这个产品对应的OrderItem，并且还没有生成订单，即还在购物车中。
            //那么就应该在对应的OrderItem基础上，调整数量
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                //如果产品是一样的话，就进行数量追加
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }
        //如果不存在对应的OrderItem,那么就新增一个订单项OrderItem
        if(!found){
            //生成新的订单项
            OrderItem oi = new OrderItem();
            // 设置数量，用户和产品
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            //插入到数据库
            orderItemService.add(oi);
        }
        return "success";
    }

    //购物车
    @RequestMapping("forecart")
    public String cart( Model model,HttpSession session) {
        User user =(User)  session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", ois);
        return "fore/cart";
    }

    //调整购物车订单数量
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem( Model model,HttpSession session, int pid, int number) {
        //判断用户是否登录
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return "fail";

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        //遍历出用户当前所有的未生成订单的OrderItem
        for (OrderItem oi : ois) {
            //根据pid找到匹配的OrderItem，并修改数量后更新到数据库
            if(oi.getProduct().getId().intValue()==pid){
                oi.setNumber(number);
                orderItemService.update(oi);
                break;
            }

        }
        return "success";
    }

    //购物车删除订单项
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem( Model model,HttpSession session,int oiid){
        //判断用户是否登录
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return "fail";
        // 删除oiid对应的OrderItem数据
        orderItemService.delete(oiid);
        return "success";
    }

    //提交到结算页面
    @RequestMapping("forecreateOrder")
    public String createOrder( Model model,Order order,HttpSession session){
        //1. 从session中获取user对象
        //2. 通过参数Order接受地址，邮编，收货人，用户留言等信息
        User user =(User)  session.getAttribute("user");
        //根据当前时间加上一个4位随机数生成订单号,根据上述参数，创建订单对象
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);

        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        // 把订单状态设置为等待支付
        order.setStatus(OrderService.waitPay);
        //从session中获取订单项集合 ( 在结算功能的ForeController.buy() 13行，订单项集合被放到了session中 )
        //把订单加入到数据库，并且遍历订单项集合，设置每个订单项的order，更新到数据库
        List<OrderItem> ois= (List<OrderItem>)  session.getAttribute("ois");
        // 统计本次订单的总金额
        float total =orderService.add(order,ois);
        //客户端跳转到确认支付页forealipay，并带上订单id和总金额
        return "redirect:forealipay?oid="+order.getId() +"&total="+total;
    }

    //确认支付页
    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);
        return "fore/payed";
    }

    //我的订单页
    @RequestMapping("forebought")
    public String bought( Model model,HttpSession session) {
        User user =(User)  session.getAttribute("user");
        //查询user所有的状态不是"delete" 的订单集合os
        List<Order> os= orderService.list(user.getId(),OrderService.delete);
        //.为这些订单填充订单项
        orderItemService.fill(os);
        //把os放在model的属性"os"上
        model.addAttribute("os", os);

        return "fore/bought";
    }

    //确认收货
    @RequestMapping("foreconfirmPay")
    public String confirmPay( Model model,int oid) {
        //通过oid获取订单对象o
        Order o = orderService.get(oid);
        //为订单对象填充订单项
        orderItemService.fill(o);
        // 把订单对象放在request的属性"o"上
        model.addAttribute("o", o);
        return "fore/confirmPay";
    }

    //确认收货成功
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed( Model model,int oid) {
        //根据参数oid获取Order对象o
        Order o = orderService.get(oid);
        // 修改对象o的状态为等待评价，修改其确认支付时间
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date());
        //更新到数据库
        orderService.update(o);
        return "fore/orderConfirmed";
    }

    //删除订单
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder( Model model,int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        return "success";
    }

    //评价
    @RequestMapping("forereview")
    public String review( Model model,int oid) {
        //获取第一个订单项对应的产品,因为在评价页面需要显示一个产品图片，那么就使用这第一个产品的图片了
        Order o = orderService.get(oid);
        //获取这个产品的评价集合
        orderItemService.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p.getId());
        //为产品设置评价数量和销量
        productService.setSaleAndReviewNumber(p);

        model.addAttribute("p", p);
        model.addAttribute("o", o);
        model.addAttribute("reviews", reviews);
        return "fore/review";
    }

    //提交评价
    @RequestMapping("foredoreview")
    public String doreview( Model model,HttpSession session,@RequestParam("oid") int oid,@RequestParam("pid") int pid,String content) {
        Order o = orderService.get(oid);
        //修改订单对象状态
        o.setStatus(OrderService.finish);
        // 更新订单对象到数据库
        orderService.update(o);
        //根据pid获取产品对象
        Product p = productService.get(pid);
        //获取参数content (评价信息)
        // 对评价信息进行转义
        content = HtmlUtils.htmlEscape(content);
        //从session中获取当前用户
        User user =(User)  session.getAttribute("user");
        //创建评价对象review
        Review review = new Review();
        //为评价对象review设置 评价信息，产品，时间，用户
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        //增加到数据库
        reviewService.add(review);

        return "redirect:forereview?oid="+oid+"&showonly=true";
    }

}

