package cn.boyce.service;

import cn.boyce.format.EasyUITreeNode;
import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 0:13 2019/4/18
 **/
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(Long parentId);
}
