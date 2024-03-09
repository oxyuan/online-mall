package cn.boyce.manager.controller.item;

import cn.boyce.common.format.EasyUIDataGridResult;
import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.service.ItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: oxyuan
 * @Date: Created in 0:58 2019/4/9
 **/
@RequestMapping("/item")
@RestController
public class ItemController {

    @Reference
    ItemService itemService;

    /**
     * 根据 Id 查找商品信息
     * @param itemId
     * @return
     */
    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }

    /**
     * 分页展示商品信息列表
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/list")
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }

    /**
     * 添加商品信息，包括商品信息、描述信息、规格参数
     * @param item
     * @param desc
     * @param itemParams
     * @return
     */
    @PostMapping("/save")
    public R saveItem(Item item, String desc, String itemParams) {
        return itemService.addItem(item, desc, itemParams);
    }

}