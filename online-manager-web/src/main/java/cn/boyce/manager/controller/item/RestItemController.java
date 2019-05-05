package cn.boyce.manager.controller.item;


import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.pojo.ItemDesc;
import cn.boyce.manager.pojo.ItemParamItem;
import cn.boyce.manager.service.ItemDescService;
import cn.boyce.manager.service.ItemParamItemService;
import cn.boyce.manager.service.ItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 23:28 2019/4/30
 **/
@RestController
@RequestMapping("/rest/item")
public class RestItemController {

    @Reference
    ItemService itemService;

    @Reference
    ItemDescService itemDescService;

    @Reference
    ItemParamItemService itemParamItemService;

    /**
     * 获取商品描述
     */
    @GetMapping("/query/item/desc/{itemId}")
    public R getItemDesc(@PathVariable long itemId) {
        ItemDesc itemDesc = itemDescService.getItemDescById(itemId);
        if (itemDesc == null) {
            return R.error(" 获取商品描述失败 ");
        } else {
            return R.ok(itemDesc);
        }
    }

    /**
     * 获取商品规格
     */
    @GetMapping("/param/item/query/{itemId}")
    public R getItemParam(@PathVariable long itemId) {
        ItemParamItem itemParamItem = itemParamItemService.getItemParamById(itemId);
        if (itemParamItem != null) {
            return R.ok(itemParamItem);
        } else {
            return R.error(null);
        }
    }

    @PostMapping("/update")
    public R updateItem(Item item, String desc) {

        itemService.updateItem(item);

        // 更新商品描述
        ItemDesc itemDesc = itemDescService.getItemDescById(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.updateItemDesc(itemDesc);

        return R.ok();
    }

    /**
     * 删除商品
     */
    @PostMapping("/delete")
    public R deleteItems(long[] ids) {
        for (long id : ids) {
            Item item = itemService.getItemById(id);
            // 商品状态，1 - 正常，2 - 下架，3 - 删除
            item.setStatus((byte) 3);
            itemService.updateItem(item);
        }
        return R.ok();
    }

    /**
     * 下架商品
     */
    @PostMapping("/instock")
    public R instockItems(long[] ids) {
        for (long id : ids) {
            Item item = itemService.getItemById(id);
            // 商品状态，1 - 正常，2 - 下架，3 - 删除
            item.setStatus((byte) 2);
            itemService.updateItem(item);
        }
        return R.ok();
    }

    /**
     * 上架商品
     */
    @PostMapping("/reshelf")
    public R reshelfItems(long[] ids) {
        for (long id : ids) {
            Item item = itemService.getItemById(id);
            // 商品状态，1 - 正常，2 - 下架，3 - 删除
            item.setStatus((byte) 1);
            itemService.updateItem(item);
        }
        return R.ok();
    }
}