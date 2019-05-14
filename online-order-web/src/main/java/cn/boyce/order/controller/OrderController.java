package cn.boyce.order.controller;

import cn.boyce.cart.service.CartService;
import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.pojo.User;
import cn.boyce.order.pojo.OrderInfo;
import cn.boyce.order.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单管理 Controller
 *
 * @Author: Yuan Baiyu
 * @Date: Created in 19:05 2019/5/8
 **/
@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private CartService cartService;

    @Reference
    private OrderService orderService;

    /**
     * 跳转订单确认页面
     * @param request
     * @return
     */
    @RequestMapping("/order-cart.html")
    public String showOrderCart(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        List<Item> cartList = cartService.getCartList(5L);
        request.setAttribute("cartList", cartList);
        return "order-cart";
    }

    @PostMapping("/create.html")
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
        // 1、接收表单提交的数据 OrderInfo。
        // 2、补全用户信息。
        User user = (User) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        // 3、调用 Service 创建订单。
        R result = orderService.createOrder(orderInfo);
        if (result.getStatus() == 200) {
            // 清空购物车
            cartService.clearCartList(user.getId());
        }
        // 取订单号
        String orderId = result.getData().toString();
        // a) 需要 Service 返回订单号
        request.setAttribute("orderId", orderId);
        request.setAttribute("payment", orderInfo.getPayment());
        // b) 当前日期加三天。
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        request.setAttribute("date", dateTime.toString("yyyy-MM-dd"));
        // 4、返回逻辑视图展示成功页面
        return "success";
    }

}