package cn.boyce.order.interceptor;

import cn.boyce.cart.service.CartService;
import cn.boyce.common.format.R;
import cn.boyce.common.util.CookieUtils;
import cn.boyce.common.util.JsonUtils;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.pojo.User;
import cn.boyce.sso.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用购物车服务时，需要先对请求拦截，判断是否为登陆状态
 *
 * @Author: oxyuan
 * @Date: Created in 19:03 2019/5/8
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Reference
    private UserService userService;

    @Reference
    private CartService cartService;

    @Value("${SSO_SERVICE_URL}")
    private String SSO_SERVICE_URL;

    /**
     * 通过存于cookie中的token（E3_TOKEN）判断是否为登陆
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、从 Cookie 中取出 token
        String token = CookieUtils.getCookieValue(request, "E3_TOKEN");
        // 2、没有 token，直接跳转登录
        if (StringUtils.isBlank(token)) {
            response.sendRedirect(SSO_SERVICE_URL + "/page/login?redirect=" + request.getRequestURL());
            return false;
        }
        // 3、取到 token，调用 sso 服务取出 user 信息
        R result = userService.getUserByToken(token);
        // 4、没有用户信息跳转登录
        if (result.getStatus() != 200) {
            response.sendRedirect(SSO_SERVICE_URL + "/page/login?redirect=" + request.getRequestURL());
            return false;
        }
        // 5、存在用户信息，则保存至 request 中
        User user = (User) result.getData();
        request.setAttribute("user", user);
        // 6、判断 Cookie 中是否含有购物车商品，有则合并
        String jsonCartList = CookieUtils.getCookieValue(request, "E3_CART", true);
        if (StringUtils.isNotBlank(jsonCartList)) {
            // 合并
            cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCartList, Item.class));
        }
        return true;
    }
}
