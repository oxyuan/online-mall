package cn.boyce.controller;

import cn.boyce.format.EasyUIDataGridResult;
import cn.boyce.format.R;
import cn.boyce.pojo.Item;
import cn.boyce.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 0:58 2019/4/9
 **/
@RequestMapping("/item")
@RestController
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;

    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }

    @GetMapping("/list")
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }

    @PostMapping("/save")
    public R saveItem(Item item, String desc) {
        return itemService.addItem(item, desc);
    }

}