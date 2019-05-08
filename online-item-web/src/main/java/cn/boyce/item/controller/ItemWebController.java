package cn.boyce.item.controller;

import cn.boyce.item.pojo.ItemDto;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.pojo.ItemDesc;
import cn.boyce.manager.service.ItemDescService;
import cn.boyce.manager.service.ItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 14:25 2019/5/7
 **/
@Controller
public class ItemWebController {

    @Reference
    private ItemService itemService;

    @Reference
    private ItemDescService itemDescService;

    @RequestMapping("/item/{itemId}.html")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        // 跟据商品 id 查询商品信息
        Item item = itemService.getItemById(itemId);
        // 把 Item 转换成 ItemDto 对象
        ItemDto itemDto = new ItemDto(item);
        // 根据商品 id 查询商品描述
        ItemDesc itemDesc = itemDescService.getItemDescById(itemId);
        // 把数据传递给页面
        model.addAttribute("item", itemDto);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }
}
