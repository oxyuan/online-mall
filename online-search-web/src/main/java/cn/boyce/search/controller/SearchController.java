package cn.boyce.search.controller;

import cn.boyce.common.format.SearchResult;
import cn.boyce.search.service.SearchService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: oxyuan
 * @Date: Created in 20:28 2019/5/5
 **/
@Controller
public class SearchController {

    @Reference
    private SearchService searchService;

    @Value("${PAGE_ROWS}")
    private Integer PAGE_ROWS;

    @GetMapping("/search.html")
    public String search(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        //调用Service查询商品信息
        SearchResult result = searchService.search(keyword, page, PAGE_ROWS);
        //把结果传递给页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("recourdCount", result.getRecourdCount());
        model.addAttribute("page", page);
        model.addAttribute("itemList", result.getItemList());
        //返回逻辑视图
        return "search";
    }
}
