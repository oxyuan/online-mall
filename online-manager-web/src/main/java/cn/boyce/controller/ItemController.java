package cn.boyce.controller;

import cn.boyce.format.EasyUIDataGridResult;
import cn.boyce.format.R;
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

    @GetMapping("/{id}")
    public R getItem(@PathVariable("id") Long id) {
        try {
            return R.isOk().data(itemService.getItemById(id));
        } catch (Exception e) {
            return R.isFail(e);
        }
    }

    @GetMapping("/list")
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }
}