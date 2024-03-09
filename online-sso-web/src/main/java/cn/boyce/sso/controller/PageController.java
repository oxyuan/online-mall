package cn.boyce.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: oxyuan
 * @Date: Created in 19:41 2019/5/7
 **/
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/login")
    public String showLogin(String redirect, Model model) {
        // 重定向 URL
        model.addAttribute("redirect", redirect);
        return "login";
    }
}