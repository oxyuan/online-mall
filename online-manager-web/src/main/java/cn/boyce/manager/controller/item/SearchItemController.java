package cn.boyce.manager.controller.item;

import cn.boyce.common.format.R;
import cn.boyce.search.service.SearchItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导入索引库
 *
 * @Author: oxyuan
 * @Date: Created in 20:58 2019/5/5
 **/
@RestController
public class SearchItemController {

    @Reference(timeout = 300000)
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    public R impotItemIndex() {
        R result = searchItemService.importItems();
        return result;
    }
}