package cn.boyce.content.service;

import cn.boyce.common.format.EasyUITreeNode;
import cn.boyce.common.format.R;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 17:59 2019/5/2
 **/
public interface ContentCategoryService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<EasyUITreeNode> getContentCategoryList(Long parantId);

    @Transactional(propagation = Propagation.REQUIRED)
    R addContentCategory(Long parentId, String name);

    @Transactional(propagation = Propagation.REQUIRED)
    R updateContentCategory(Long id, String name);

    @Transactional(propagation = Propagation.REQUIRED)
    R deleteContentCategory(Long id);
}
