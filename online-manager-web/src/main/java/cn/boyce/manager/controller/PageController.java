package cn.boyce.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: oxyuan
 * @Date: Created in 13:52 2019/4/20
 **/
@Controller
public class PageController {

    /**
     *
     * @param page 页面名字作参
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    /**
     * 编辑商品 页面转向
     * @return
     */
    @RequestMapping("/rest/page/item-edit")
    public String showItemEdit() {
        return "item-edit";
    }
}
