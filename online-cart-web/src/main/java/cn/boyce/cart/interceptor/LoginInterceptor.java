package cn.boyce.cart.interceptor;

import cn.boyce.common.format.R;
import cn.boyce.common.util.CookieUtils;
import cn.boyce.manager.pojo.User;
import cn.boyce.sso.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义登录拦截器
 *
 * @Author: Yuan Baiyu
 * @Date: Created in 18:18 2019/5/8
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Reference
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、从 Cookie 中取出 token
        String token = CookieUtils.getCookieValue(request, "E3_TOKEN");
        // 2、没有 token，直接放行
        if (StringUtils.isBlank(token)) {
            return true;
        }
        // 3、取到 token，调用 sso 服务取出 user 信息
        R result = userService.getUserByToken(token);
        // 4、没有用户信息直接放行
        if (result.getStatus() != 200) {
            return true;
        }
        // 5、存在用户信息，则保存至 request 中
        request.setAttribute("user", (User) result.getData());
        return true;
    }
}
