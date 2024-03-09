package cn.boyce.cart.controller;

import cn.boyce.cart.service.CartService;
import cn.boyce.common.format.R;
import cn.boyce.common.util.CookieUtils;
import cn.boyce.common.util.JsonUtils;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.pojo.User;
import cn.boyce.manager.service.ItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: oxyuan
 * @Date: Created in 18:27 2019/5/8
 **/
@Controller
@RequestMapping("/cart")
public class CartController {

    @Value("${CART}")
    private String CART;

    @Value("${CART_EXPIRE}")
    private Integer CART_EXPIRE;

    @Reference
    private ItemService itemService;

    @Reference
    private CartService cartService;

    /**
     * 添加购物车：登录时添加 redis，未登录添加 cookie
     *
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/add/{itemId}.html")
    public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response) {
        // 登录添加至 redis
        User user = (User) request.getAttribute("user");
        if (null != user) {
            cartService.addCart(user.getId(), itemId, num);
            return "cartSuccess";
        }
        // 未登录添加至 cookie
        // 1、从 cookie 中查询商品列表。
        List<Item> cartList = getCartListFromCookie(request);
        // 2、判断商品在商品列表中是否存在。
        boolean hasItem = false;
        for (Item item : cartList) {
            // 对象比较的是地址，应该是值的比较
            if (item.getId() == itemId.longValue()) {
                // 3、如果存在，商品数量相加。(num 在数据库的item表中代表库存数量，此处代表购物车中的数量)
                item.setNum(item.getNum() + num);
                hasItem = true;
                break;
            }
        }
        if (!hasItem) {
            // 4、不存在，根据商品 id 查询商品信息。
            Item item = itemService.getItemById(itemId);
            // 取一张图片
            String image = item.getImage();
            if (StringUtils.isNoneBlank(image)) {
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            // 设置购买商品数量
            item.setNum(num);
            // 5、把商品添加到购车列表。
            cartList.add(item);
        }
        // 6、把购车商品列表写入 cookie。
        CookieUtils.setCookie(request, response, CART, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
        return "cartSuccess";
    }

    /**
     * 从 cookie 中取购物车列表
     * <p>Title: getCartList</p>
     * <p>Description: </p>
     *
     * @param request
     * @return
     */
    private List<Item> getCartListFromCookie(HttpServletRequest request) {
        // 取购物车列表(需要转码、解码)
        String json = CookieUtils.getCookieValue(request, CART, true);
        // 判断 json 是否为 null
        if (StringUtils.isNotBlank(json)) {
            List<Item> list = JsonUtils.jsonToList(json, Item.class);
            // 把 json 转换成商品列表返回
            return list;
        }
        return new ArrayList<>();
    }

    @RequestMapping("/cart.html")
    public String showCartList(HttpServletRequest request, HttpServletResponse response) {
        // 取 Cookie 购物车商品列表
        List<Item> cartList = getCartListFromCookie(request);
        // 登录添加至 redis
        User user = (User) request.getAttribute("user");
        if (user != null) {
            // 合并购物车
            cartService.mergeCart(user.getId(), cartList);
            // 删除 Cookie 中购物车列表
            CookieUtils.deleteCookie(request, response, CART);
            cartList = cartService.getCartList(user.getId());
        }
        // 传递给页面
        request.setAttribute("cartList", cartList);
        return "cart";
    }

    /**
     * 更新购物车列表，返回json数据时不能接收.html请求
     *
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/update/num/{itemId}/{num}.action")
    @ResponseBody
    public R updateNum(@PathVariable Long itemId, @PathVariable Integer num,
                       HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getAttribute("user");
        if (user != null) {
            return cartService.updateCartNum(user.getId(), itemId, num);
        }
        // 未登录
        // 1、接收两个参数
        // 2、从 cookie 中取商品列表
        List<Item> cartList = getCartListFromCookie(request);
        // 3、遍历商品列表找到对应商品
        for (Item item : cartList) {
            if (item.getId() == itemId.longValue()) {
                // 4、更新商品数量 （原数量<=要修改的量如何？）
                item.setNum(num);
            }
        }
        // 5、把商品列表写入 cookie。
        CookieUtils.setCookie(request, response, CART, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
        // 6、响应 R。Json 数据。
        return R.ok();
    }

    @RequestMapping("/delete/{itemId}.html")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,
                                 HttpServletResponse response) {
        User user = (User) request.getAttribute("user");
        if (user != null) {
            cartService.deleteCartItem(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }
        // 未登录
        // 1、从 url 中取商品 id
        // 2、从 cookie 中取购物车商品列表
        List<Item> cartList = getCartListFromCookie(request);
        // 3、遍历列表找到对应的商品
        for (Item item : cartList) {
            if (item.getId() == itemId.longValue()) {
                // 4、删除商品。
                cartList.remove(item);
                break;
            }
        }
        // 5、把商品列表写入 cookie。
        CookieUtils.setCookie(request, response, CART, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
        // 6、返回逻辑视图：在逻辑视图中做 redirect 跳转。
        return "redirect:/cart/cart.html";
    }
}