package cn.boyce.manager.controller.content;

import cn.boyce.common.format.EasyUIDataGridResult;
import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Content;
import cn.boyce.content.service.ContentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: oxyuan
 * @Date: Created in 14:43 2019/5/3
 **/
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @GetMapping("/query/list")
    public EasyUIDataGridResult getContentListByCategoryId(Long categoryId, Integer page, Integer rows) {
        return contentService.getContentListByCategoryId(categoryId, page, rows);
    }

    @PostMapping("/delete")
    public R deleteContents(Long[] ids) {
        for (Long id : ids) {
            contentService.deleteById(id);
        }
        return R.ok();
    }

    @PostMapping("/save")
    public R saveContent(Content content) {
        if (null == content.getCategoryId()) {
            return R.error(null);
        }
        return contentService.addContent(content);
    }
}
