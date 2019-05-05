package cn.boyce.manager.controller.item;

import cn.boyce.manager.service.ItemParamService;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.boyce.common.format.EasyUIDataGridResult;
import cn.boyce.common.format.R;
import org.springframework.web.bind.annotation.*;

/**
 * 商品规格管理模板 controller
 *
 * @Author: Yuan Baiyu
 * @Date: Created in 22:28 2019/4/28
 **/
@RestController
@RequestMapping("/item/param")
public class ItemParamController {

    @Reference
    ItemParamService itemParamService;

    @RequestMapping("/list")
    public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
        return itemParamService.getItemParamList(page, rows);
    }

    @RequestMapping("/query/itemcatid/{cid}")
    public R getItemParamByCid(@PathVariable Long cid) {
        return itemParamService.getItemParamByCid(cid);
    }

    @RequestMapping("/save/{cid}")
    public R insertItemParam(@PathVariable Long cid, String paramData) {
        return itemParamService.addItemParam(cid, paramData);
    }

    @PostMapping("/delete")
    public R deleteParam(String ids) {
        return itemParamService.deleteParam(ids);
    }
}
