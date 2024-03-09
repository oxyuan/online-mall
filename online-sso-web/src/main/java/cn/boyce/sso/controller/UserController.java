package cn.boyce.sso.controller;

import cn.boyce.common.format.R;
import cn.boyce.common.util.CookieUtils;
import cn.boyce.manager.pojo.User;
import cn.boyce.sso.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: oxyuan
 * @Date: Created in 21:15 2019/5/7
 **/
@RestController
@RequestMapping("/user")
public class UserController {


    @Reference
    private UserService userService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping("/check/{param}/{type}")
    public R checkData(@PathVariable String param, @PathVariable Integer type) {
        return userService.checkData(param, type);
    }

    @PostMapping("/register")
    public R register(User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public R login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        R result = userService.login(username, password);
        // Token 写入 cookie 浏览器关闭就过期
        if (result.getStatus() == 200) {
            String token = result.getData().toString();
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }
        return result;
    }

    /**
     * 根据 Token 获取用户信息
     *
     * @param token    token
     * @param callback jsonp 回掉方法名
     */
    @GetMapping("/token/{token}")
    public Object getUserInfo(@PathVariable String token, String callback) {
        R result = userService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            // 处理 ajax 跨域访问，jsonp
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        } else {
            return result;
        }
    }
}
