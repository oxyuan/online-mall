package cn.boyce.manager.service;

import cn.boyce.common.format.EasyUITreeNode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: oxyuan
 * @Date: Created in 0:13 2019/4/18
 **/
public interface ItemCatService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
