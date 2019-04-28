package cn.boyce.controller;

import cn.boyce.format.EasyUITreeNode;
import cn.boyce.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 12:43 2019/4/20
 **/
@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

//    @Reference
    @Autowired
    ItemCatService itemCatService;

    @RequestMapping("/list")
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id", defaultValue = "0") Long parantId) {
        return itemCatService.getItemCatList(parantId);
    }



}
