package cn.boyce.manager.controller;

import cn.boyce.manager.service.ItemParamItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 17:10 2019/4/30
 **/
@Controller
public class ItemParamItemController {

    @Reference
    private ItemParamItemService itemParamItemService;

//    @RequestMapping("/showParam/{itemId}")
//    public String showParam(@PathVariable Long itemId, Model model) {
//        String string = itemParamItemService.getItemParamById(itemId);
//        model.addAttribute("html", string);
//        return "item-param";
//    }
}
