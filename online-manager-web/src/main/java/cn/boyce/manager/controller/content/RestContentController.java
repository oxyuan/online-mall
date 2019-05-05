package cn.boyce.manager.controller.content;

import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Content;
import cn.boyce.content.service.ContentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 17:27 2019/5/3
 **/
@RestController
@RequestMapping("/rest")
public class RestContentController {

    @Reference
    private ContentService contentService;

    @PostMapping("/content/edit")
    public R editCont(Content content){
        contentService.updateById(content);
        return R.ok();
    }
}
