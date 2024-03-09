package cn.boyce.manager.controller.item;

import cn.boyce.common.format.EasyUITreeNode;
import cn.boyce.manager.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: oxyuan
 * @Date: Created in 12:43 2019/4/20
 **/
@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

    @Reference
    ItemCatService itemCatService;

    @RequestMapping("/list")
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id", defaultValue = "0") Long parantId) {
        return itemCatService.getItemCatList(parantId);
    }


}
