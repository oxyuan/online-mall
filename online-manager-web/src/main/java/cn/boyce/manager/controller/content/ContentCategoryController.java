package cn.boyce.manager.controller.content;

import cn.boyce.common.format.EasyUITreeNode;
import cn.boyce.common.format.R;
import cn.boyce.content.service.ContentCategoryService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 18:23 2019/5/2
 **/
@RestController
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Reference
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    public List<EasyUITreeNode> getContentCatList(
            @RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCategoryList(parentId);
    }

    @PostMapping("/create")
    public R createCategory(Long parentId, String name) {
        return contentCategoryService.addContentCategory(parentId, name);
    }

    @PostMapping("/update")
    public R updateContentCategory(Long id, String name) {
        return contentCategoryService.updateContentCategory(id, name);
    }

    @PostMapping("/delete")
    public R deleteContentCatList(Long id) {
        return contentCategoryService.deleteContentCategory(id);
    }
}
