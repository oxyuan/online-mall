package cn.boyce.portal.controller;

import cn.boyce.manager.pojo.Content;
import cn.boyce.content.service.ContentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 18:16 2019/5/3
 **/
@Controller
public class IndexController {

    @Value("${CONTENT_BANNER_ID}")
    private Long CONTENT_BANNER_ID;

    @Reference
    private ContentService contentService;

    @RequestMapping({"/index", "/", "index.html"})
    public String showIndex(Model model) {
        List<Content> add1List = contentService.listByCategoryId(CONTENT_BANNER_ID);
        model.addAttribute("ad1List", add1List);
        return "index";
    }
}
